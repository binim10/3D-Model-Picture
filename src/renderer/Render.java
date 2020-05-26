package renderer;

import elements.Camera;
import elements.LightSource;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * The type Render
 * render the image.
 */
public class Render {
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private ImageWriter _imageWriter;
    private Scene _scene;


    /**
     * Instantiates a new Render.
     *
     * @param imageWriter the image writer
     * @param scene       the scene
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        this._imageWriter = imageWriter;
        this._scene = scene;

    }

    /**
     * Render image by using writePixel method.
     * check each pixel in view plane if intersect some geometries and paint the pixel according that,
     * if not, paint in background's color.
     */
    public void renderImage() {
        Camera camera = _scene.getCamera();
        Intersectable geometries = _scene.getGeometries();
        java.awt.Color background = _scene.getBackground().getColor();
        //number of pixels
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        //size of the view plane
        double imageWidth = _imageWriter.getWidth();
        double imageHeight = _imageWriter.getHeight();
        //the distance
        double distance = _scene.getDistance();
        for (int row = 0; row < nY; row++) {
            for (int col = 0; col < nX; col++) {
                Ray ray = camera.constructRayThroughPixel(nX, nY, col, row, distance, imageWidth, imageHeight);
                GeoPoint closestPoint = findClosestIntersection(ray);
                //no intersections - paint the background color
                if (closestPoint == null) {
                    _imageWriter.writePixel(col, row, background);
                } else {//paint in the color of the geometry in the closest point
                    _imageWriter.writePixel(col, row, calcColor(closestPoint, ray).getColor());
                }
            }
        }
    }

    /**
     * Calc color of given geo point.
     *
     * @param gp    the Geo point class
     * @param ray   the ray
     * @return the Color
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, 1).add(_scene.getAmbientLight().getIntensity());
    }

    /**
     * Calc color of given geo point.
     *
     * @param p     the Geo point class
     * @param inRay the ray
     * @param level the level
     * @param k     the k
     * @return the Color
     */
    public Color calcColor(GeoPoint p, Ray inRay, int level, double k) {
        Color color = p.geometry.getEmmission();
        Vector v = inRay.getDirection();
        Vector n = p.geometry.getNormal(p.point);
        Material material = p.geometry.getMaterial();
        int nShininess = material.getNShininess();
        double kd = material.getKD();
        double ks = material.getKS();
        double nv = v.dotProduct(n);
        for (LightSource lightSource : _scene.getLights()) {
            Vector l = lightSource.getL(p.point);
            double nl = l.dotProduct(n);
            if ((nl > 0d && nv > 0d) || (nl <= 0d && nv <= 0d)) {
                double ktr = transparency(lightSource, l, n, p);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(p.point).scale(ktr);
                    color = color.add(calcDiffusive(kd, nl, lightIntensity),
                            calcSpecular(ks, nl, l, n, v, nShininess, lightIntensity));
                }
            }
        }
        if (level == 1)
            return Color.BLACK;
        double kr = p.geometry.getMaterial().getKR(), kkr = k * kr;
        if (kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(n, p.point, inRay);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null)
                color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
        }
        double kt = p.geometry.getMaterial().getKT(), kkt = k * kt;
        if (kkt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(n, p.point, inRay);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null)
                color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
        }
        return color;
    }


    /**
     * Calculate Specular component of light reflection.
     *
     * @param ks             specular component  mekadem
     * @param nl             dot product between l and n
     * @param l              direction from light to point
     * @param n              normal to surface at the point
     * @param v              direction from point of view to point
     * @param nShininess     shininess level
     * @param lightIntensity light intensity at the point
     * @return specular component light effect at the point
     */
    private Color calcSpecular(double ks, double nl, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.add(n.scale(-2 * nl));
        double vr = v.dotProduct(r);
        if (alignZero(vr) >= 0)
            return Color.BLACK;
        return lightIntensity.scale(ks * Math.pow(-vr, nShininess));
    }

    /**
     * Calculate Diffusive component of light reflection.
     *
     * @param kd             diffusive component mekadem
     * @param nl             dot product between l and n
     * @param lightIntensity light intensity at the point
     * @return diffusive component of light reflection
     */
    private Color calcDiffusive(double kd, double nl, Color lightIntensity) {
        double d = nl;
        if (d < 0)
            d = -d;
        return lightIntensity.scale(kd * d);
    }


    /**
     * Unshaded boolean.
     * @param ls the light source
     * @param l  the vector from lithe source to the point
     * @param n  the normal
     * @param gp the geo point
     * @return the boolean
     */
    private double transparency(LightSource ls, Vector l, Vector n, GeoPoint gp) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null) return 1.0;
        double distance = ls.getDistance(gp.point);
        double ktr = 1.0;
        for (GeoPoint g : intersections) {
            if (alignZero(g.point.distance(gp.point) - distance) <= 0) {
                if (ktr < MIN_CALC_COLOR_K) return 0.0;
                ktr *= g.geometry.getMaterial().getKT();
            }
        }
        return ktr;
    }


    /**
     * Construct reflected ray .
     *
     * @param n     the vector n
     * @param point the point
     * @param inRay the ray
     * @return the ray after moov delta
     */
    private Ray constructReflectedRay(Vector n, Point3D point, Ray inRay) {
        Vector v = inRay.getDirection();
        Vector r = v.add(n.scale(-2 * (v.dotProduct(n))));
        return new Ray(point, r, n);
    }

    /**
     * Construct refracted ray .
     *
     * @param n     the vector n
     * @param point the point
     * @param inRay the ray
     * @return the ray after moov delta
     */
    private Ray constructRefractedRay(Vector n, Point3D point, Ray inRay) {
        return new Ray(point, inRay.getDirection(), n);
    }

    /**
     * Find closest intersection geo point.
     *
     * @param ray the ray
     * @return the geo point closest
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersectionsPoints = _scene.getGeometries().findIntersections(ray);
        if (intersectionsPoints == null)
            return null;
        Point3D rayStart = ray.getPOO();
        double min = Double.MAX_VALUE;
        GeoPoint closest = null;
        for (GeoPoint p : intersectionsPoints) {
            double check = rayStart.distance(p.point);
            if (check < min) {
                min = check;
                closest = p;
            }
        }
        return closest;
    }

    /**
     * Print grid on the image.
     *
     * @param interval the interval
     * @param color    the color
     */
    public void printGrid(int interval, java.awt.Color color) {
        double rows = this._imageWriter.getNy();
        double cols = _imageWriter.getNx();
        //Writing the lines.
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (col % interval == 0 || row % interval == 0) {
                    _imageWriter.writePixel(col, row, color);
                }
            }
        }

    }

    /**
     * Write to image.
     * use the imageWriter method to write and save the image.
     */
    public void writeToImage() {
        _imageWriter.writeToImage();
    }

}

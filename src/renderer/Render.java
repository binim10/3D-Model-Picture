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
                List<GeoPoint> intersectionPoints = geometries.findIntersections(ray);
                //no intersections - paint the background color
                if (intersectionPoints == null) {
                    _imageWriter.writePixel(col, row, background);
                } else {//paint in the color of the geometry in the closest point
                    GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                    _imageWriter.writePixel(col, row, calcColor(closestPoint).getColor());
                }

            }
        }


    }

    /**
     * Calc color of given point.
     *
     * @param p the point
     * @return the Color
     */
    public primitives.Color calcColor(GeoPoint p) {
        Color color = _scene.getAmbientLight().getIntensity();
        color = color.add(p.geometry.getEmmission());
        Vector v = p.point.subtract(_scene.getCamera().getP0()).normalize();
        Vector n = p.geometry.getNormal(p.point);
        Material material = p.geometry.getMaterial();
        int nShininess = material.getNShininess();
        double kd = material.getKD();
        double ks = material.getKS();
        for (LightSource lightSource : _scene.getLights()) {
            Vector l = lightSource.getL(p.point);
            double nl = n.dotProduct(l);
            double nv = n.dotProduct(v);
            if ((nl > 0 && nv > 0) || (nl < 0 && nv < 0)) {
                Color lightIntensity = lightSource.getIntensity(p.point);
                color = color.add(calcDiffusive(kd, nl, lightIntensity),
                        calcSpecular(ks, nl, l, n, v, nShininess, lightIntensity));
            }
        }
        return color;
    }

    /**
     * Calculate Specular component of light reflection.
     *
     * @param ks             specular component  mekadem
     * @param l              direction from light to point
     * @param n              normal to surface at the point
     * @param v              direction from point of view to point
     * @param nShininess     shininess level
     * @param lightIntensity light intensity at the point
     * @return specular component light effect at the point
     */
    private Color calcSpecular(double ks, double nl, Vector n, Vector l, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.add(n.scale(-2 * nl));
        double vr = alignZero(v.dotProduct(r));
        if (alignZero(vr) >= 0)
            return Color.BLACK;
        return lightIntensity.scale(ks * Math.pow(-vr, nShininess));
    }

    /**
     * Calculate Diffusive component of light reflection.
     *
     * @param kd             diffusive component mekadem
     * @param nl             n.dotproduct(l)
     * @param lightIntensity light intensity at the point
     * @return diffusive component of light reflection
     */
    private Color calcDiffusive(double kd, double nl, Color lightIntensity) {
        if (alignZero(nl) < 0)
            nl = -nl;
        return lightIntensity.scale(kd * nl);
    }

    /**
     * Gets closest point to the beginning of ray by given list of points.
     *
     * @param points a list of points
     * @return the closest point
     */
    public GeoPoint getClosestPoint(List<GeoPoint> points) {
        GeoPoint closest;
        Point3D rayStart = _scene.getCamera().getP0();
        closest = new GeoPoint(points.get(0).geometry, points.get(0).point);
        double min = rayStart.distance(closest.point);
        for (GeoPoint p : points) {
            double check = rayStart.distance(p.point);
            if (check < min) {
                min = check;
                closest = new GeoPoint(p.geometry, p.point);
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

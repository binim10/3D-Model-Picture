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
    private final ImageWriter _imageWriter;
    private final Scene _scene;
    private int _superSampling;
    private final int SPARE_THREADS = 2;
    private int _threads = 1;
    private boolean _print = false;
    private boolean _BVHImprove;


    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object
     */
    public void renderImage() {
        final int nX = _imageWriter.getNx();
        final int nY = _imageWriter.getNy();
        final double dist = _scene.getDistance();
        final double width = _imageWriter.getWidth();
        final double height = _imageWriter.getHeight();
        final Camera camera = _scene.getCamera();
        final java.awt.Color background = _scene.getBackground().getColor();

        final Pixel thePixel = new Pixel(nY, nX);

        // Generate threads
        Thread[] threads = new Thread[_threads];
        for (int i = _threads - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel)) {
                    Ray ray = camera.constructRayThroughPixel(nX, nY, pixel.col, pixel.row, //
                            dist, width, height);
                    GeoPoint closestPoint = findClosestIntersection(ray);
                    //no intersections - paint the background color
                    _imageWriter.writePixel(pixel.col, pixel.row,
                            closestPoint == null ? background : calcColor(closestPoint, ray).getColor());
                }
            });
        }

        // Start threads
        for (
                Thread thread : threads)
            thread.start();

        // Wait for all threads to finish
        for (
                Thread thread : threads)
            try {
                thread.join();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        if (_print) System.out.print("\r100%%\n");
    }


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
     * Gets super sampling.
     *
     * @return the number of rays
     */
    public int getSuperSampling() {
        return _superSampling;
    }

    /**
     * Sets super sampling.
     * number of Rays from the point, effected the image quality.
     *
     * @param superSampling the super sampling
     * @return the number of rays
     */
    public Render setSuperSampling(int superSampling) {
        _superSampling = superSampling;
        return this;
    }

    /**
     * Render image by using writePixel method.
     * check each pixel in view plane if intersect some geometries and paint the pixel according that,
     * if not, paint in background's color.
     */
    public void renderImageOld() {
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
     * Set multithreading <br>
     * - if the parameter is 0 - number of coress less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading patameter must be 0 or higher");
        if (threads != 0)
            _threads = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            if (cores <= 2)
                _threads = 1;
            else
                _threads = cores;
        }
        return this;
    }

    /**
     * turn on/off the improvement of BVH
     *
     * @param bvhImprove true if improving is set
     * @return Render
     */
    public Render setBVHImprove(boolean bvhImprove) {
        this._BVHImprove = bvhImprove;
        if (bvhImprove)
            _scene.getGeometries().buildHierarchyTree();
        return this;
    }

    /**
     * Calc color of given geo point.
     *
     * @param gp  the Geo point class
     * @param ray the ray
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
        double nv = alignZero(v.dotProduct(n));
        for (LightSource lightSource : _scene.getLights()) {
            Vector l = lightSource.getL(p.point);
            double nl = alignZero(l.dotProduct(n));
            if ((nl > 0d && nv > 0d) || (nl < 0d && nv < 0d)) {
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
     * calculate the color according transparency.
     *
     * @param ls the light source
     * @param l  the vector from lithe source to the point
     * @param n  the normal
     * @param gp the geo point
     * @return the boolean
     */
    private double transparency(LightSource ls, Vector l, Vector n, GeoPoint gp) {
        Vector lightDirection = l.scale(-1); // from point to light source- BASIC SHADOW RAY
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        double ktr, sumKtr = 0;
        double distance = ls.getDistance(gp.point);
        //create the beam of rays for soft shadow
        List<Ray> beamRays = lightRay.createRaysBeam(ls, gp.point, n, getSuperSampling());
        for (Ray r : beamRays) {
            List<GeoPoint> intersections;
            if (_BVHImprove)
                intersections = _scene.getGeometries().findIntersectionsBB(r);
            else
                intersections = _scene.getGeometries().findIntersections(r);
            if (intersections == null) {
                sumKtr += 1.0;
                continue;
            }
            ktr = 1.0;
            for (GeoPoint g : intersections) {
                if (alignZero(g.point.distance(gp.point) - distance) <= 0) {
                    if (ktr < MIN_CALC_COLOR_K) {
                        ktr = 0.0;
                        break;
                    }
                    ktr *= g.geometry.getMaterial().getKT();
                }
            }
            sumKtr += ktr;
        }
        return sumKtr / beamRays.size();
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
        List<GeoPoint> intersectionsPoints;
        if (_BVHImprove) {
            intersectionsPoints = _scene.getGeometries().findIntersectionsBB(ray);
        } else
            intersectionsPoints = _scene.getGeometries().findIntersections(ray);
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

    /**
     * Set debug printing on
     *
     * @return the Render object itself
     */
    public Render setDebugPrint() {
        _print = true;
        return this;
    }

    /**
     * Pixel is an internal helper class whose objects are associated with a Render object that
     * they are generated in scope of. It is used for multithreading in the Renderer and for follow up
     * its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each thread.
     *
     * @author Dan
     */
    private class Pixel {
        public volatile int row = 0;
        public volatile int col = -1;
        private long _maxRows = 0;
        private long _maxCols = 0;
        private long _pixels = 0;
        private long _counter = 0;
        private int _percents = 0;
        private long _nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         *
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            _maxRows = maxRows;
            _maxCols = maxCols;
            _pixels = (long) maxRows * maxCols;
            _nextCounter = _pixels / 100;
            if (Render.this._print) System.out.printf("\r %02d%%", _percents);
        }

        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
         * critical section for all the threads, and main Pixel object data is the shared data of this critical
         * section.<br/>
         * The function provides next pixel number each call.
         *
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
         * finished, any other value - the progress percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++_counter;
            if (col < _maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            ++row;
            if (row < _maxRows) {
                col = 0;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         *
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percents = nextP(target);
            if (percents > 0)
                if (Render.this._print) {
                    System.out.printf("\r %02d%%%n", percents);
                }
            if (percents >= 0)
                return true;
            if (Render.this._print) {
                System.out.printf("\r %02d%%%n", 100);
            }
            return false;
        }
    }
}
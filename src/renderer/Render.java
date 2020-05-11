package renderer;

import elements.Camera;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * The type Render.
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
        return p.geometry.getEmmission().add(_scene.getAmbientLight().getIntensity());
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

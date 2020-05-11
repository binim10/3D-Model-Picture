package renderer;

import elements.AmbientLight;
import elements.Camera;
import geometries.Intersectable;
import geometries.Sphere;
import org.junit.Test;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.List;

import static org.junit.Assert.*;

/**
 * The type Render test.
 */
public class RenderTest {

    /**
     * test for Gets closest point.
     */
    @Test
    public void getClosestPointtest() {
        //================================EP============================
        Scene scene = new Scene("test closest");
        scene.setCamera(new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(100);
        scene.setBackground(new Color(75, 127, 90));
        scene.setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1));
        scene.addGeometries(new Sphere(50, new Point3D(0, 0, 100)));
        Ray ray = scene.getCamera().constructRayThroughPixel(500, 500, 250, 250, 100, 500, 500);
        ImageWriter imageWriter = new ImageWriter("base render test", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);
        List<Intersectable.GeoPoint> list = scene.getGeometries().findIntersections(ray);
        Intersectable.GeoPoint closest = render.getClosestPoint(list);
        //=========TC01: 2 points in list
        assertEquals("not the closest", new Point3D(0.25000625039065816, 0.25000625039065816, 50.00125007813163), closest);
    }
}
package elements;

import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * The Camera integration test check the num of intersection between rays and geometries by going through the viewPlane.
 */
public class CameraIntegrationTest {

    Camera cam1 = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
    Camera cam2 = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
    List<Point3D> results;
    int count = 0;
    int nX = 3;
    int nY = 3;

    /**
     * Tests for sphere integration with find intersections method
     */
    @Test
    public void testConstructRayThroughPixelWithSphere() {
        //TC01: 2 intersections
        Sphere sph = new Sphere(1, new Point3D(0, 0, 3));
        for (int i = 0; i < nY; ++i) {
            for (int j = 0; j < nX; ++j) {
                results = sph.findIntersections(new Ray(cam1.constructRayThroughPixel(3, 3, j, i, 1, 3, 3)));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("SphTC01:bad ray", 2, count);
         

        //TC02: 18 intersections.
        Sphere sph2 = new Sphere(2.5, new Point3D(0, 0, 2.5));

        results = null;
        count = 0;
        for (int i = 0; i < nY; ++i) {
            for (int j = 0; j < nX; ++j) {
                results = sph2.findIntersections(new Ray(cam2.constructRayThroughPixel(nX, nY, j, i, 1, 3, 3)));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("SphTC02:bad Ray", 18, count);
         

        //TC03:10 intersection points
        Sphere sph3 = new Sphere(2, new Point3D(0, 0, 2));

        results = null;
        count = 0;
        for (int i = 0; i < nY; ++i) {
            for (int j = 0; j < nX; ++j) {
                results = sph3.findIntersections(new Ray(cam2.constructRayThroughPixel(nX, nY, j, i, 1, 3, 3)));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("SphTC03:bad Ray", 10, count);
         

        //TC04:9 intersection points
        Sphere sph4 = new Sphere(4, new Point3D(0, 0, 2));

        results = null;
        count = 0;
        for (int i = 0; i < nY; ++i) {
            for (int j = 0; j < nX; ++j) {
                results = sph4.findIntersections(new Ray(cam1.constructRayThroughPixel(nX, nY, j, i, 1, 3, 3)));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("SphTC03:bad Ray", 9, count);
         

        //TC05:Zero intersection points
        Sphere sph5 = new Sphere(0.5, new Point3D(0, 0, -1));

        results = null;
        count = 0;
        for (int i = 0; i < nY; ++i) {
            for (int j = 0; j < nX; ++j) {
                results = sph5.findIntersections(new Ray(cam1.constructRayThroughPixel(nX, nY, j, i, 1, 3, 3)));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("SphTC05:bad Ray", 0, count);
         
        //END
    }

    /**
     * test for plane integration with find intersections method
     */
    @Test
    public void testConstructRayThroughPixelWithPlane() {
        //TC01:9 intersection points
        Plane pla1 = new Plane(new Point3D(0, 0, 3), new Point3D(0, 1, 3), new Point3D(1, 0, 3));
        results = null;
        count = 0;
        for (int i = 0; i < nY; ++i) {
            for (int j = 0; j < nX; ++j) {
                results = pla1.findIntersections(new Ray(cam1.constructRayThroughPixel(nX, nY, j, i, 1, 3, 3)));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("PlaTC01:bad Ray", 9, count);
         

        //TC02: 9 intersection points
        Plane pla2 = new Plane(new Point3D(0, 0, 2), new Point3D(0, -4, 1), new Point3D(1, 4, 3));
        results = null;
        count = 0;
        for (int i = 0; i < nY; ++i) {
            for (int j = 0; j < nX; ++j) {
                results = pla2.findIntersections(new Ray(cam1.constructRayThroughPixel(nX, nY, j, i, 1, 3, 3)));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("PlaTC02:bad Ray", 9, count);
         

        //TC03:6 intersection points
        Plane pla3 = new Plane(new Point3D(0, 0, 2), new Point3D(0, -1, 1), new Point3D(1, 1, 3));
        results = null;
        count = 0;
        for (int i = 0; i < nY; ++i) {
            for (int j = 0; j < nX; ++j) {
                results = pla3.findIntersections(new Ray(cam1.constructRayThroughPixel(nX, nY, j, i, 1, 3, 3)));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("PlaTC03:bad Ray", 6, count);
         

        //END
    }

    /**
     * Test ConstructRayThroughPixel with triangle integration with find intersections method
     */
    @Test
    public void testConstructRayThroughPixelWithTriangle() {
        //TC01: One intersection point
        Triangle triangle = new Triangle(new Point3D(0, -1, 2), new Point3D(1, 1, 2), new Point3D(-1, 1, 2));
        results = null;
        count = 0;
        for (int i = 0; i < nY; ++i) {
            for (int j = 0; j < nX; ++j) {
                results = triangle.findIntersections(new Ray(cam2.constructRayThroughPixel(nX, nY, j, i, 1, 3, 3)));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("TriTC01:bad Ray", 1, count);
         

        //TC02: Two intersection points
        Triangle triangle2 = new Triangle(new Point3D(0, -20, 2), new Point3D(1, 1, 2), new Point3D(-1, 1, 2));
        results = null;
        count = 0;
        for (int i = 0; i < nY; ++i) {
            for (int j = 0; j < nX; ++j) {
                results = triangle2.findIntersections(new Ray(cam1.constructRayThroughPixel(nX, nY, j, i, 1, 3, 3)));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("TriTC02:bad Ray", 2, count);
         
        //END
    }
}

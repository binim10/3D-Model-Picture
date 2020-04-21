package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;

public class GeometriesTest {

    @Test
    public void findIntersections() {
        Triangle _triangle = new Triangle(new Point3D(0, 1.5, 3), new Point3D(0, 1, 0), new Point3D(0, 3, 0));
        Sphere _sphere = new Sphere(1d, new Point3D(1, 0, 0));
        Plane _plane = new Plane(new Point3D(0, 1.5, 0), new Point3D(0, 1, 0.2), new Point3D(0, 1, 1));
        Geometries _geometries = new Geometries(_triangle, _sphere, _plane);
        Geometries _empty = new Geometries();

        //=================Equivalence Partitions===================
        //TC01: the Ray intersect almost the all geometries
        List<Point3D> result1 = _geometries.findIntersections(new Ray(new Point3D(2.6491820366597, -2.1421103028843, 0),
                new Vector(-2.6491820366597, 3.7252316384971, 2.2729097142749)));
        assertEquals("The Ray supposed to going through Triangle and Plane", 2, result1.size());

        //=================BVA======================================
        //TC11: The Ray intersect All Geometries
        List<Point3D> result11 = _geometries.findIntersections(new Ray(new Point3D(2.6491820366597, -2.1421103028843, 0),
                new Vector(-2.6491820366597, 3.5766128120969, 0.6518916880567)));
        assertEquals("The Ray supposed to going through all geometries", 4, result11.size());
        //TC12: The Ray intersect only one
        List<Point3D> result12 = _geometries.findIntersections(new Ray(new Point3D(2.6491820366597, -2.1421103028843, 0),
                new Vector(-2.6491820366597, 3.1421103028843, 3.8004674121363)));
        assertEquals("The Ray supposed to going through the Plane", 1, result12.size());
        //TC13: No intersections.
        List<Point3D> result13 = _geometries.findIntersections(new Ray(new Point3D(3, -3, 0),
                new Vector(1, 0, 0)));
        assertNull("No intersections at all", result13);
        //TC14: no geometries in the collection
        assertNull("No Geometries to going through", _empty.findIntersections(new Ray(new Point3D(2.6491820366597, -2.1421103028843, 0),
                new Vector(-2.6491820366597, 3.7252316384971, 2.2729097142749))));


    }
}
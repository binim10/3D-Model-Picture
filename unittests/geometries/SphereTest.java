package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.awt.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.junit.Assert.*;

public class SphereTest {

    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Sphere sp = new Sphere(2, new Point3D(1, 1, 1));
        Vector v = new Vector((-1d / Math.sqrt(2)), Math.sqrt(1d / 2), 0);
        assertEquals("Bad normal to sphere", v, sp.getNormal(new Point3D(0, 2, 1)));
    }

    @Test
    public void findIntersections() {
        Sphere sphere = new Sphere(1d, new Point3D(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertEquals("TC01: fail has instruction", null,
                sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<Point3D> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals("TC02 Wrong number of points", 2, result.size());
        if (result.get(0).get_x().get() > result.get(1).get_x().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("no match between the intersections", List.of(p1, p2), result);

        // TC03: Ray starts inside the sphere (1 point)
        Point3D p3 = new Point3D(1.5831513611335566, 0.8123635208501675, 0.0);
        List<Point3D> result2 = sphere.findIntersections(new Ray(new Point3D(0.5, 0, 0), new Vector(2, 1.5, 0)));
        assertEquals("TC03: no intersection's at all or more than one", 1, result2.size());
        assertEquals("TC03: Ray not cross the sphere in the excepted points", p3, result2.get(0));
        // TC04: Ray starts after the sphere (0 points)
        assertEquals("TC04: the Ray not start after the sphere", null,
                sphere.findIntersections(new Ray(new Point3D(3, 0, 0), new Vector(1, 0, 0))));

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)


        // TC11: Ray starts at sphere and goes inside (1 points)
        Point3D p11 = new Point3D(1.820706605082462, 0.5712796438230884, 0.008957506761619063);
        List<Point3D> result11 = sphere.findIntersections(new Ray(new Point3D(0.19, 0.58, 0.07),
                new Vector(1.87, -0.01, -0.07)));
        assertEquals("TC11: no match between the number of intersection", 1, result11.size());
        assertEquals("no match between the points of the intersection", p11, result11.get(0));


        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull("TC12: there is an intersection", sphere.findIntersections(new Ray(new Point3D(1, 0, 1),
                new Vector(0, 0, 1))));


        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        Point3D p13 = new Point3D(0, 0, 0);
        Point3D p13_1 = new Point3D(2, 0, 0);
        List<Point3D> result13 = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(3, 0, 0)));
        assertEquals("TC13: No match between Number of intersection", 2, result13.size());
        if (result13.get(0).get_x().get() > result13.get(1).get_x().get())
            result13 = List.of(result13.get(1), result13.get(0));
        assertEquals("TC13: no match between points of intersection", List.of(p13, p13_1), result13);

        // TC14: Ray starts at sphere and goes inside (1 points)
        Point3D p14 = new Point3D(2, 0, 0);
        List<Point3D> result14 = sphere.findIntersections(new Ray(new Point3D(0, 0, 0),
                new Vector(3, 0, 0)));
        assertEquals("TC11: no match between the number of intersection", 1, result14.size());
        assertEquals("no match between the points of the intersection", p14, result14.get(0));

        // TC15: Ray starts inside (1 points)
        Point3D p15 = new Point3D(2, 0, 0);
        List<Point3D> result15 = sphere.findIntersections(new Ray(new Point3D(1.5, 0, 0),
                new Vector(3, 0, 0)));
        assertEquals("TC11: no match between the number of intersection", 1, result15.size());
        assertEquals("no match between the points of the intersection", p15, result15.get(0));

        // TC16: Ray starts at the center (1 points)
        Point3D p16 = new Point3D(1.5773502691896257, 0.5773502691896258, 0.5773502691896258);
        List<Point3D> result16 = sphere.findIntersections(new Ray(new Point3D(1, 0, 0),
                new Vector(2, 2, 2)));
        assertEquals("TC11: no match between the number of intersection", 1, result16.size());
        assertEquals("no match between the points of the intersection", p16, result16.get(0));

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull("TC17: there is an intersection", sphere.findIntersections(new Ray(new Point3D(2, 0, 0),
                new Vector(3, 0, 0))));

        // TC18: Ray starts after sphere (0 points)
        assertNull("TC18: there is an intersection", sphere.findIntersections(new Ray(new Point3D(3, 0, 0),
                new Vector(3, 0, 0))));


        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull("TC19: there is an intersection", sphere.findIntersections(new Ray(new Point3D(0.5, 1, 0),
                new Vector(3, 0, 0))));

        // TC20: Ray starts at the tangent point
        assertNull("TC20: there is an intersection", sphere.findIntersections(new Ray(new Point3D(1, 1, 0),
                new Vector(3, 0, 0))));

        // TC21: Ray starts after the tangent point
        assertNull("TC21: there is an intersection", sphere.findIntersections(new Ray(new Point3D(1.5, 1, 0),
                new Vector(3, 0, 0))));


        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull("TC22: there is an intersection", sphere.findIntersections(new Ray(new Point3D(-0.5, 0, 0),
                new Vector(0, 1, 0))));
    }
}
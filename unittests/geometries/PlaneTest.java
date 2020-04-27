package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;


import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * The type Plane test.
 */
public class PlaneTest {

    /**
     * Gets normal.
     */
    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Plane pl = new Plane(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals("Bad normal to plain", new Vector(sqrt3, sqrt3, sqrt3), pl.get_normal());
    }

    /**
     * Test find intersections.
     */
    @Test
    public void testFindIntersections() {

        Plane pl = new Plane(new Point3D(0, 1.5, 0), new Point3D(0, 1, 0.2), new Point3D(0, 1, 1));

        // ============ Equivalence Partitions Tests ==============
        // TC02: Ray intersects the plane(1 intersection)
        Point3D p1 = new Point3D(0, 1, 1.25);
        List<Point3D> result = pl.findIntersections(new Ray(new Point3D(-0.5, 1, 1), new Vector(2, 0, 1)));
        assertEquals("no moutch intersection", 1, result.size());
        assertEquals("no the point i want", p1, result.get(0));

        // TC03: Ray does not intersect the plane(0 intersection)
        assertNull("", pl.findIntersections(new Ray(new Point3D(0.5, 0, 0), new Vector(2, 1, 0))));


        // ============ BVA Tests ==============
        // TC04: Ray parallel and not include in the plane(0 intersection)
        assertNull("", pl.findIntersections(new Ray(new Point3D(0.5, 0, 0), new Vector(0, 0, 1))));

        // TC05: Ray parallel and include in the plane(0 intersection)
        assertNull("", pl.findIntersections(new Ray(new Point3D(0, 0, 0), new Vector(0, 0, 1))));

        // TC06: Ray is orthogonal and start before to the plane (1 intersection)
        Point3D p6 = new Point3D(0, 0, 0);
        List<Point3D> result06 = pl.findIntersections(new Ray(new Point3D(0.5, 0, 0), new Vector(-2, 0, 0)));
        assertEquals("no match intersection", 1, result06.size());
        assertEquals("no the point i want", p6, result06.get(0));

        // TC07: Ray is orthogonal and start in the plane (0 intersection)
        assertNull("", pl.findIntersections(new Ray(new Point3D(0, 0, 0), new Vector(-2, 0, 1))));

        // TC08:  Ray is orthogonal and start in the plane (0 intersection)
        assertNull("", pl.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(-2, 0, 1))));

        // TC09: Ray is neither orthogonal nor parallel to and begins at the plane(0 intersection)
        assertNull("", pl.findIntersections(new Ray(new Point3D(0, 2, 2), new Vector(-2, 5, 2))));
        ;

        // TC10: Ray intersects the plane
        assertNull("", pl.findIntersections(new Ray(new Point3D(0, 0, 0), new Vector(-2, 5, 2))));
        ;

    }
}
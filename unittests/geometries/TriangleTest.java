package geometries;

import org.junit.After;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

public class TriangleTest {

    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle tr = new Triangle(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals("Bad normal to plain", new Vector(sqrt3, sqrt3, sqrt3), tr._plane.get_normal());
    }

    @Test
    public final void findIntersections() {
        Triangle t = new Triangle(new Point3D(0, 1, 0), new Point3D(0, 4, 0), new Point3D(0, 3, 3));
        // ============ Equivalence Partitions Tests ==============
        // TC02: Inside polygon/triangle(1 intersection)
        Point3D p2 = new Point3D(0, 2, 1);
        assertEquals("no mutch betwwen num of intersection", 1, t.findIntersections(new Ray(new Point3D(1, 2, 1), new Vector(-2, 0, 0))).size());
        assertEquals("no mutch betwwen num of intersection", p2, t.findIntersections(new Ray(new Point3D(1, 2, 1), new Vector(-2, 0, 0))));

        // TC03: Outside against edge(0 intersection)
        assertNull("there is intersection with the poligon", t.findIntersections(new Ray(new Point3D(1, 0.5, 1), new Vector(-2, 0, 0))));

        // TC04: Outside against vertex(0 intersection)
        assertNull("there is intersection with the poligon", t.findIntersections(new Ray(new Point3D(0, 0.5, -0.5), new Vector(-2, 0, 0))));

        // ============ BVA Tests ==============
        // TC01: There is a simple single test here
        // TC01: There is a simple single test here
        // TC01: There is a simple single test here
        // TC01: There is a simple single test here
        // TC01: There is a simple single test here
        // TC01: There is a simple single test here
        // TC01: There is a simple single test here
        // TC01: There is a simple single test here
        // TC01: There is a simple single test here
    }
}
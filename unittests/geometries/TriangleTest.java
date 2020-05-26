package geometries;

import org.junit.After;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static org.junit.Assert.*;

/**
 * The type Triangle test.
 */
public class TriangleTest {

    /**
     * test getNormal
     */
    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle tr = new Triangle(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals("Bad normal to plain", new Vector(sqrt3, sqrt3, sqrt3), tr._plane.get_normal());
    }

    /**
     * test findIntersections.
     */
    @Test
    public final void findIntersections() {
        Triangle t = new Triangle(new Point3D(0, 1.5, 3), new Point3D(0, 1, 0), new Point3D(0, 3, 0));

        // ============ Equivalence Partitions Tests ==============
        // TC02: Inside polygon/triangle(1 intersection)
        GeoPoint p2 = new GeoPoint(t, new Point3D(0, 2, 1));
        List<GeoPoint> result2 = t.findIntersections(new Ray(new Point3D(0.5, 2, 1), new Vector(-2, 0, 0)));
        assertEquals("no match between num of intersection", p2, result2.get(0));

        // TC03: Outside against edge(0 intersection)
        assertNull("there is intersection with the Triangle", t.findIntersections(new Ray(new Point3D(0.5, 3, 1), new Vector(-2, 0, 0))));

        // TC04: Outside against vertex(0 intersection)
        assertNull("there is intersection with the Triangle", t.findIntersections(new Ray(new Point3D(0.5, 0.9, -0.1), new Vector(-2, 0, 0))));

        // ============ BVA Tests ==============
        // TC11: in vertex
        assertNull("TC11: no match number of intersection", t.findIntersections(new Ray(new Point3D(0.5, 1, 0), new Vector(-2, 0, 0))));
        // TC12: On edge
        assertNull("", t.findIntersections(new Ray(new Point3D(0.5, 2.27, 1.46), new Vector(-2, 0, 0))));
        // TC13: On edge's continuation
        assertNull("", t.findIntersections(new Ray(new Point3D(1, 0, -6), new Vector(-2, 0, 0))));

    }
}
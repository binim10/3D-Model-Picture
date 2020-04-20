package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;


import java.util.List;

import static org.junit.Assert.*;

public class PlaneTest {

    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Plane pl =new Plane(new Point3D(0,0,1),new Point3D(1,0,0),new Point3D(0,1,0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals("Bad normal to plain", new Vector(sqrt3, sqrt3, sqrt3),pl.get_normal());
    }

    @Test
    public void testFindIntersections() {

    }
}
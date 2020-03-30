package geometries;

import org.junit.After;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

public class TriangleTest {

    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle tr =new Triangle(new Point3D(0,0,1),new Point3D(1,0,0),new Point3D(0,1,0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals("Bad normal to plain", new Vector(sqrt3, sqrt3, sqrt3),tr._plane.get_normal());
    }
}
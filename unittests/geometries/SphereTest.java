package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

public class SphereTest {

    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Sphere sp = new Sphere(2, new Point3D(1, 1, 1));
        Vector v = new Vector((-1d /Math.sqrt(2)), Math.sqrt(1d / 2), 0);
        assertEquals("Bad normal to sphere", v, sp.getNormal(new Point3D(0,2,1)));
    }
}
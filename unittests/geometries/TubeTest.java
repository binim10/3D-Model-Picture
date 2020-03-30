package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

public class TubeTest {

    // ============ Equivalence Partitions Tests ==============
    /**
     *test case for getNormal method in tube.
     *EP
     */
    @Test
    public void getNormal() {
        Tube t = new Tube(2d, new Ray(new Point3D(0, 1, 2), new Vector(2, 2, 2)));
        Vector v = new Vector(-11 /( 11 * Math.sqrt(3)), -11 / (11 * Math.sqrt(3)), -11 / (11 * Math.sqrt(3)));
        assertEquals("Bad normal to tube", v, t.getNormal(new Point3D(1, 2, 3)));
    }
}
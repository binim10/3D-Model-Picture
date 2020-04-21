package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

public class TubeTest {

    // ============ Equivalence Partitions Tests ==============

    /**
     * test case for getNormal method in tube.
     * EP in case that the t (the dotProduct between the direction and the  )
     */
    @Test
    public void getNormal() {
        Tube t = new Tube(2d, new Ray(new Point3D(0, 1, 0), new Vector(0, 0, 1)));
        Vector v = new Vector(0, 1, 0);
        assertEquals("Bad normal to tube", v, t.getNormal(new Point3D(0, 3, 0)));
    }
}

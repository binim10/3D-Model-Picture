package primitives;

import org.junit.Test;

import static java.lang.System.out;
import static org.junit.Assert.*;
import static primitives.Util.isZero;

public class VectorTest {


    @Test
    public void add() {
        Point3D p1 = new Point3D(1, 2, 3);
        assertTrue("ERROR: Point + Vector does not work correctly",Point3D.ZERO.equals(p1.add(new Vector(-1, -2, -3))));
    }

    @Test
    public void subtract() {
        Point3D p1 = new Point3D(1, 2, 3);
        assertEquals("ERROR: Point - Point does not work correctly",new Vector(1, 1, 1),new Point3D(2, 3, 4).subtract(p1));
    }

    @Test
    public void scale() {
    }

    @Test
    public void dotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);
        // test Dot-Product
        assertTrue("ERROR: dotProduct() for orthogonal vectors is not zero", isZero(v1.dotProduct(v3)));
        assertTrue("ERROR: dotProduct() wrong value", isZero(v1.dotProduct(v2) + 28));
    }

    @Test
    public void crossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals("crossProduct() wrong result length", v1.length() * v3.length(), vr.length(), 0.00001);

        // Test cross-product result orthogonality to its operands
        assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
        assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v3)));

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-product of co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {}

    }

    @Test
    public void lengthSquared() {
    }

    @Test
    public void length() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);
        assertTrue("ERROR: lengthSquared() wrong value",isZero(v1.lengthSquared() - 14));
        assertTrue("ERROR: length() wrong value",isZero(new Vector(0, 3, 4).length() - 5));
    }

    @Test
    public void normalize() {
        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v);
        Vector vCopyNormalize = vCopy.normalize();
        assertTrue("ERROR: normalize() function creates a new vector",vCopy == vCopyNormalize);
        assertTrue("ERROR: normalize() result is not a unit vector",isZero(vCopyNormalize.length() - 1));
    }

    @Test
    public void normalized() {
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalized();
        assertFalse("ERROR: normalized() function does not create a new vector", u == v);
        assertTrue("ERROR: normalize() result is not a unit vector",isZero(u.length() - 1));
    }
}
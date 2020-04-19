package geometries;

import primitives.Point3D;

/**
 * The type Triangle.
 */
public class Triangle extends Polygon {

    /**
     * Instantiates a new Triangle.
     *
     * @param a the a
     * @param b the b
     * @param c the c
     */
    public Triangle(Point3D a,Point3D b,Point3D c) {
        super(a,b,c);
    }

    @Override
    public String toString() {
        return "Triangle:" + super.toString();
    }

}

package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * The type Cylinder.
 */
public class Cylinder extends Tube {
    private final double _height;

    /**
     * Instantiates a new Cylinder.
     *
     * @param radius the radius
     * @param r      the r
     * @param height the height
     */
    public Cylinder(double radius, Ray r, double height) {
        super(radius, r);
        this._height = height;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public double get_height() {
        return _height;
    }

    @Override
    public String toString() {
        return "Cylinder:" +
                "_height=" + _height +
                ".\n" + super.toString();
    }

    @Override
    public Vector getNormal(Point3D p) {
        return null;
    }
}

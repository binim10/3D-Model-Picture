package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * The type Radial geometry.
 */
public abstract class RadialGeometry implements Geometry {

    double _radius;

    /**
     * Instantiates a new Radial geometry.
     *
     * @param radius the radius
     */
    public RadialGeometry(double radius) {
        this._radius = radius;
    }

    /**
     * Instantiates a new Radial geometry.
     *
     * @param radialGeometry the radial geometry
     */
    public RadialGeometry(RadialGeometry radialGeometry) {
        this._radius=radialGeometry._radius;
    }

    /**
     * Gets radius.
     *
     * @return the radius
     */
    public double get_radius() {
        return this._radius;
    }

    @Override
    public String toString() {
        return "_radius= " + _radius +
                ". ";
    }

    @Override
    public Vector getNormal(Point3D p) {
        return null;
    }
}

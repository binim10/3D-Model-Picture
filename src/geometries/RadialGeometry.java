package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * The type Radial geometry.
 */
public abstract class RadialGeometry extends Geometry {

    /**
     * The Radius.
     */
    protected double _radius;

    /**
     * Instantiates a new Radial geometry.
     *
     * @param radius the radius
     */
    public RadialGeometry(double radius) {
        this(Color.BLACK, radius);
    }

    /**
     * Instantiates a new Radial geometry.
     *
     * @param color  the color
     * @param radius the radius
     */
    public RadialGeometry(Color color, double radius) {
        this(color, new Material(0, 0, 0), radius);
    }


    /**
     * Instantiates a new Radial geometry.
     *
     * @param color    the color
     * @param material the material
     * @param radius   the radius
     */
    public RadialGeometry(Color color, Material material, double radius) {
        super(color, material);
        _radius = radius;
    }

    /**
     * Gets radius.
     *
     * @return the radius
     */
    public double getRadius() {
        return this._radius;
    }

    @Override
    public String toString() {
        return "_radius= " + _radius +
                ". ";
    }
}

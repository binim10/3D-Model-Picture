package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * The interface Geometry.
 */
public abstract class Geometry implements Intersectable {
    protected Color _emmission;
    protected Material _material;

    /**
     * Instantiates a new Geometry.
     */
    public Geometry() {
        this(Color.BLACK, new Material(0, 0, 0));
    }

    /**
     * Instantiates a new Geometry.
     *
     * @param emmission the emmission
     */
    public Geometry(Color emmission) {
        this(emmission, new Material(0, 0, 0));
    }

    /**
     * Instantiates a new Geometry.
     *
     * @param emmission the emmission
     * @param material  the material
     */
    public Geometry(Color emmission, Material material) {
        _emmission = emmission;
        _material = material;
    }

    /**
     * Gets emmission.
     *
     * @return the emmission
     */
    public Color getEmmission() {
        return _emmission;
    }

    /**
     * Gets material.
     *
     * @return the material
     */
    public Material getMaterial() {
        return _material;
    }

    /**
     * Gets normal.
     *
     * @param p the Point3D
     * @return the normal
     */
    public abstract Vector getNormal(Point3D p);


}

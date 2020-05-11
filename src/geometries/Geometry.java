package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * The interface Geometry.
 */
public abstract class Geometry implements Intersectable {
    protected Color _emmission;

    public Geometry() {
        this._emmission = Color.BLACK;
    }

    public Geometry(Color emmission) {
        this._emmission = emmission;
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
     * Gets normal.
     *
     * @param p the Point3D
     * @return the normal
     */
    public abstract Vector getNormal(Point3D p);


}

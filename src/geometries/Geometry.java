package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * The interface Geometry.
 */
public interface Geometry {

    /**
     * Gets normal.
     *
     * @param p the Point3D
     * @return the normal
     */
    Vector getNormal(Point3D p);
}

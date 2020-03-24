package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Sphere extends RadialGeometry {
    Point3D _center;

    /**
     * Instantiates a new Sphere.
     *
     * @param radius the radius
     * @param center the center
     */
    public Sphere(double radius, Point3D center) {
        super(radius);
        this._center = new Point3D(center);
    }

    /**
     * Gets center.
     *
     * @return the center
     */
    public Point3D get_center() {
        return _center;
    }

    @Override
    public Vector getNormal(Point3D p) {
        return null;
    }
}

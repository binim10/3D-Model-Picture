package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * The type Sphere.
 */
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
        Vector v = p.subtract(get_center());
        return v.normalize();
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        Point3D p0 = Ray.getPoint();
        Vector v = Ray.getDirection();
        Vector u;
        try {
            u = _center.subtract(p0);   // p0 == _center
        } catch (IllegalArgumentException e) {
            return List.of(Ray.getTargetPoint(_radius));
        }
        double tm = alignZero(v.dotProduct(u));
        double dSquared = (tm == 0) ? u.lengthSquared() : u.lengthSquared() - tm * tm;
        double thSquared = alignZero(_radius * _radius - dSquared);

        if (thSquared <= 0) return null;

        double th = alignZero(Math.sqrt(thSquared));
        if (th == 0) return null;

        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);
        if (t1 <= 0 && t2 <= 0) return null;
        if (t1 > 0 && t2 > 0) return List.of(Ray.getTargetPoint(t1), Ray.getTargetPoint(t2)); //P1 , P2
        if (t1 > 0)
            return List.of(Ray.getTargetPoint(t1));
        else
            return List.of(Ray.getTargetPoint(t2));
    }
}

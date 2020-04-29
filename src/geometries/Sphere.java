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
    private Point3D _center;

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
        // Ray start at the center of the sphere
        if (ray.getPOO().equals(get_center())) {
            Point3D p = new Point3D(ray.getPoint(_radius));
            return List.of(p);
        }
        Vector u = _center.subtract(ray.getPOO());
        double tm = alignZero(u.dotProduct(ray.getDirection()));
        double d = alignZero(Math.sqrt(u.lengthSquared() - (tm * tm)));
        //TODO if alignzero(d-radius)>=0
        if (alignZero(d-  _radius)>=0)
            return null;
        double th = alignZero(Math.sqrt((_radius * _radius) - (d * d)));
        double t1 = alignZero(tm + th);
        double t2 = alignZero(tm - th);
        if (t1 > 0 && t2 > 0) {
            Point3D p1 = new Point3D(ray.getPoint(t1));
            Point3D p2 = new Point3D(ray.getPoint(t2));
            return List.of(p1, p2);
        }
        if (t1 <= 0 && t2 <= 0) {
            return null;
        }
        if (t1 > 0 && t2 <= 0) {
            return List.of(new Point3D(ray.getPoint(t1)));
        }
        if (t1 <= 0 && t2 > 0) {
            return List.of(new Point3D(ray.getPoint(t2)));
        }
        return null;
    }
}

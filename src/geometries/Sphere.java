package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * The type Sphere.
 */
public class Sphere extends RadialGeometry {
    private final Point3D _center;

    /**
     * Instantiates a new Sphere.
     *
     * @param radius the radius
     * @param center the center
     */
    public Sphere(double radius, Point3D center) {
        this(Color.BLACK, radius, center);
    }

    /**
     * Instantiates a new Sphere withe color.
     *
     * @param color  the color
     * @param radius the radius
     * @param center the center
     */
    public Sphere(Color color, double radius, Point3D center) {
        this(color, new Material(0, 0, 0), radius, center);
    }

    /**
     * Instantiates a new Sphere withe material.
     *
     * @param color    the color
     * @param material the material
     * @param radius   the radius
     * @param center   the center
     */
    public Sphere(Color color, Material material, double radius, Point3D center) {
        super(color, material, radius);
        _center = new Point3D(center);
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
    public List<GeoPoint> findIntersections(Ray ray) {
        // Ray start at the center of the sphere
        if (ray.getPOO().equals(get_center())) {
            GeoPoint p = new GeoPoint(this, ray.getPoint(_radius));
            return List.of(p);
        }
        Vector u = _center.subtract(ray.getPOO());
        double tm = alignZero(u.dotProduct(ray.getDirection()));
        double d = alignZero(Math.sqrt(u.lengthSquared() - (tm * tm)));
        if (alignZero(d - _radius) >= 0)
            return null;
        double th = alignZero(Math.sqrt((_radius * _radius) - (d * d)));
        double t1 = alignZero(tm + th);
        double t2 = alignZero(tm - th);
        if (t1 > 0 && t2 > 0) {
            GeoPoint p1 = new GeoPoint(this, ray.getPoint(t1));
            GeoPoint p2 = new GeoPoint(this, ray.getPoint(t2));
            return List.of(p1, p2);
        }
        if (t1 <= 0 && t2 <= 0) {
            return null;
        }
        if (t1 > 0 && t2 <= 0) {
            return List.of(new GeoPoint(this, ray.getPoint(t1)));
        }
        if (t1 <= 0 && t2 > 0) {
            return List.of(new GeoPoint(this, ray.getPoint(t2)));
        }
        return null;
    }

    @Override
    void createBox() {
        double x = _center.getX().get(),
                y = _center.getY().get(),
                z = _center.getZ().get();
        minX = x - _radius;
        maxX = x + _radius;
        minY = y - _radius;
        maxY = y + _radius;
        minZ = z - _radius;
        maxZ = z + _radius;
    }
}

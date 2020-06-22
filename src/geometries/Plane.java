package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The type Plane represent a plane.
 */
public class Plane extends Geometry {
    /**
     * The P.
     */
    protected Point3D _p;
    /**
     * The Normal.
     */
    protected Vector _normal;

    /**
     * Instantiates a new Plane.
     *
     * @param x the x
     * @param y the y
     * @param z the z
     */
    public Plane(Point3D x, Point3D y, Point3D z) {
        this(x, (y.subtract(x).crossProduct(z.subtract(x))).normalized());
    }

    /**
     * Instantiates a new Plane.
     *
     * @param p      the p
     * @param normal the normal
     */
    public Plane(Point3D p, Vector normal) {
        this(new Material(0, 0, 0), Color.BLACK, p, normal);
    }

    /**
     * Instantiates a new Plane with color.
     *
     * @param color  the color
     * @param p      the p
     * @param normal the normal
     */
    public Plane(Color color, Point3D p, Vector normal) {
        this(new Material(0, 0, 0), color, p, normal);
    }

    /**
     * Instantiates a new Plane with material.
     *
     * @param material the material
     * @param color    the color
     * @param p        the p
     * @param normal   the normal
     */
    public Plane(Material material, Color color, Point3D p, Vector normal) {
        super(color, material);
        _p = p;
        _normal = normal.normalized();

    }


    /**
     * Gets p.
     *
     * @return the p
     */
    public Point3D get_p() {
        return _p;
    }

    /**
     * Gets normal.
     *
     * @return the normal
     */
    public Vector get_normal() {
        return _normal.normalized();
    }

    @Override
    public Vector getNormal(Point3D p) {
        return get_normal();
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        if (ray.getPOO().equals(_p)) // Ray start at the point that present the plane
            return null;
        double nv = ray.getDirection().dotProduct(_normal);
        if (isZero(nv)) // Ray is parallel to the plane
            return null;

        double nQMinusP0 = _normal.dotProduct(_p.subtract(ray.getPOO()));
        double t = alignZero(nQMinusP0 / nv);

        if (alignZero(t) > 0) {
            GeoPoint p = new GeoPoint(this, ray.getPoint(t));
            return List.of(p);
        }
        return null;

    }

    @Override
    public void createBox() {
        _minX = Double.NEGATIVE_INFINITY;
        _maxX = Double.POSITIVE_INFINITY;
        _minY = Double.NEGATIVE_INFINITY;
        _maxY = Double.POSITIVE_INFINITY;
        _minZ = Double.NEGATIVE_INFINITY;
        _maxZ = Double.POSITIVE_INFINITY;
    }
}

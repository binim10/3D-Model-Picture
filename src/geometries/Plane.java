package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * The type Plane represent a plane.
 */
public class Plane implements Geometry {
    protected Point3D _p;
    protected Vector _normal;

    /**
     * Instantiates a new Plane.
     *
     * @param p      the p
     * @param normal the normal
     */
    public Plane(Point3D p, Vector normal) {
        this._p = p;
        this._normal = normal;
    }

    /**
     * Instantiates a new Plane.
     *
     * @param x the x
     * @param y the y
     * @param z the z
     */
    public Plane(Point3D x,Point3D y, Point3D z) {
        this._p=new Point3D(x);
        Vector v1= y.subtract(x);
        Vector v2=z.subtract(x);
        this._normal=(v1.crossProduct(v2)).normalized();
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
        return _normal;
    }

    @Override
    public Vector getNormal(Point3D p) {
        return get_normal();
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}

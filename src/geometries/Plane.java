package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Plane implements Geometry {
    protected Point3D _p;
    protected Vector _normal;

    public Plane(Point3D p, Vector normal) {
        this._p = p;
        this._normal = normal;
    }

    public Plane(Point3D x,Point3D y, Point3D z) {
        this._p=new Point3D(x);
        this._normal=null;
    }

    public Point3D get_p() {
        return _p;
    }

    public Vector get_normal() {
        return _normal;
    }

    @Override
    public Vector getNormal(Point3D p) {
        return null;
    }
}

package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube {
    double _height;

    public Cylinder(double radius, Ray r, double height) {
        super(radius, r);
        this._height = height;
    }

    public double get_height() {
        return _height;
    }

    @Override
    public String toString() {
        return "Cylinder:" +
                "_height=" + _height +
                 ".\n"+super.toString();
    }

    @Override
    public Vector getNormal(Point3D p) {
        return null;
    }
}

package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Tube extends  RadialGeometry {
    Ray r;

    public Tube(double radius, Ray r) {
        super(radius);
        this.r =new Ray(r);
    }

    public Ray getR() {
        return r;
    }

    @Override
    public String toString() {
        return "Tube:" +
                "r=" + r +
                super.toString();
    }

    @Override
    public Vector getNormal(Point3D p) {
        return null;
    }
}

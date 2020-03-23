package geometries;

public abstract class RadialGeometry implements Geometry {
    double _radius;

    public RadialGeometry(double radius) {
        this._radius = radius;
    }

    public RadialGeometry(RadialGeometry radialGeometry) {
        this._radius=radialGeometry._radius;
    }

    public double get_radius() {
        return this._radius;
    }

    @Override
    public String toString() {
        return "_radius= " + _radius +
                ":\n";
    }
}

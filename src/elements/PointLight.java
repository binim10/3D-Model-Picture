package elements;

import primitives.Color;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Vector;

import java.util.List;

/**
 * The type Point light.
 */
public class PointLight extends Light implements LightSource {

    protected Point3D _position;
    protected double _kC, _kL, _kQ;
    protected double _radius;

    /**
     * Instantiates a new Point light.
     *
     * @param intensity the intensity
     * @param position  the position
     * @param c         the c
     * @param l         the l
     * @param q         the q
     */
    public PointLight(Color intensity, Point3D position, double c, double l, double q) {
        super(intensity);
        _position = position;
        _kC = c;
        _kL = l;
        _kQ = q;
    }

    @Override
    public Point3D getPosition() {
        return _position;
    }

    @Override
    public LightSource setRadius(double radius) {
        _radius = radius;
        return this;
    }

    @Override
    public double getDistance(Point3D point3D) {
        return point3D.distance(_position);
    }

    @Override
    public List<Point3D> getPoints() {
        return null;
    }

    @Override
    public double getRadius() {
        return _radius;
    }

    @Override
    public Color getIntensity(Point3D point3D) {
        double disSq = point3D.distanceSquared(_position);
        double d = point3D.distance(_position);
        return _intensity.reduce(_kC + _kL * d + _kQ * disSq);
    }

    @Override
    public Vector getDirection() {
        return null;
    }

    @Override
    public void setPoints(List<Point3D> randPo) {
    }

    @Override
    public Vector getL(Point3D point3D) {
        return point3D.subtract(_position).normalize();
    }
}

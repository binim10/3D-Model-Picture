package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import java.util.List;

public class DirectionalLight extends Light implements LightSource {

    private Vector _direction;

    /**
     * Instantiates a new Light.
     *
     * @param _intensity the intensity
     */
    public DirectionalLight(Color _intensity, Vector direction) {
        super(_intensity);
        _direction = direction.normalized();
    }

    @Override
    public Color getIntensity(Point3D point3D) {
        return _intensity;
    }

    @Override
    public double getRadius() {
        return 0;
    }

    @Override
    public LightSource setRadius(double radius) {
        return this;
    }

    @Override
    public LightSource setPoints(List<Point3D> randPo) {
        return this;
    }

    @Override
    public Point3D getPosition() {
        return null;
    }

    @Override
    public Vector getL(Point3D point3D) {
        return _direction.normalize();
    }

    @Override
    public double getDistance(Point3D point3D) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public List<Point3D> getPoints() {
        return null;
    }
}

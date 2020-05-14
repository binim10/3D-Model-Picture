package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * The type Point light.
 */
public class PointLight extends Light implements LightSource {

    protected Point3D _position;
    protected double _kC, _kL, _kQ;


    /**
     * Instantiates a new Light.
     *
     * @param _intensity the intensity
     * @param position   the position
     * @param c          the c
     * @param l          the l
     * @param q          the q
     */
    public PointLight(Color _intensity, Point3D position, double c, double l, double q) {
        super(_intensity);
        _position = position;
        _kC = c;
        _kL = l;
        _kQ = q;
    }

    @Override
    public Color getIntensity(Point3D point3D) {
        double disSq = point3D.distanceSquared(_position);
        double d = point3D.distance(_position);
        return _intensity.reduce(_kC + _kL * d + _kQ * disSq);
    }

    @Override
    public Vector getL(Point3D point3D) {
        return point3D.subtract(_position).normalize();
    }
}

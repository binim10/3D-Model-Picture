package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import java.util.List;

import static java.lang.Math.max;
import static primitives.Util.alignZero;

/**
 * The type Spot light.
 */
public class SpotLight extends PointLight {
    private Vector _direction;
    private double _sharpsBeam;
    protected List<Point3D> _randomPoints = null;

    /**
     * Instantiates a new Light.
     *
     * @param intensity the intensity
     * @param position  the position
     * @param c         the c
     * @param l         the l
     * @param q         the q
     * @param direction the direction
     */
    public SpotLight(Color intensity, Point3D position, double c, double l, double q, Vector direction) {
        this(intensity, position, c, l, q, direction, 1.0);
    }

    public SpotLight(Color intensity, Point3D position, double c, double l, double q, Vector direction, double sharps) {
        this(intensity, position, c, l, q, direction, sharps, 0);
    }

    public SpotLight(Color intensity, Point3D position, double c, double l, double q, Vector direction, double sharps, double radius) {
        super(intensity, position, c, l, q);
        _direction = direction.normalized();
        _sharpsBeam = sharps;
        _radius = radius;
    }

    @Override
    public LightSource setPoints(List<Point3D> randPo) {
        _randomPoints = randPo;
        return this;
    }

    @Override
    public List<Point3D> getPoints() {
        return _randomPoints;
    }

    @Override
    public Color getIntensity(Point3D point3D) {
        double dirL = point3D.subtract(_position).normalized().dotProduct(_direction);
        if (alignZero(dirL) <= 0) {
            return Color.BLACK;
        }
        if (_sharpsBeam > 1) {
            dirL = Math.pow(dirL, _sharpsBeam);
        }
        return super.getIntensity(point3D).scale(dirL);
    }
}

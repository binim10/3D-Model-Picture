package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import static java.lang.Math.max;

/**
 * The type Spot light.
 */
public class SpotLight extends PointLight {
    private Vector _direction;

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
        super(intensity, position, c, l, q);
        _direction = direction;
    }

    @Override
    public Color getIntensity(Point3D point3D) {
        Vector l = point3D.subtract(_position).normalized();
        //max method is used for ensure the intensity doesnt scale with negative.
        return super.getIntensity(point3D).scale(max(0, _direction.dotProduct(l)));
    }
}

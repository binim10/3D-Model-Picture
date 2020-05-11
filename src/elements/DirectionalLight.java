package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {

    private Vector _direction;

    /**
     * Instantiates a new Light.
     *
     * @param _intensity the intensity
     */
    public DirectionalLight(Color _intensity, Vector direction) {
        super(_intensity);
        _direction = direction;
    }

    @Override
    public Color getIntensity(Point3D point3D) {
        return _intensity;
    }

    @Override
    public Vector getL(Point3D point3D) {
        return _direction;
    }
}

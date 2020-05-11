package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * The interface Light source.
 */
public interface LightSource {
    /**
     * Gets intensity.
     *
     * @param point3D the point 3 d
     * @return the intensity
     */
    public Color getIntensity(Point3D point3D);

    /**
     * Gets l.
     *
     * @param point3D the point 3D
     * @return the vector l
     */
    public Vector getL(Point3D point3D);

}

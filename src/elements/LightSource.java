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
    Color getIntensity(Point3D point3D);

    /**
     * Gets the vector from light source to the given point.
     *
     * @param point3D the point in the scene
     * @return the vector L normalized
     */
    Vector getL(Point3D point3D);

}

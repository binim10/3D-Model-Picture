package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import java.util.List;

/**
 * The interface Light source.
 */
public interface LightSource {
    /**
     * Gets position if exist.
     *
     * @return the position (directional return null)
     */

    Point3D getPosition();

    /**
     * Gets radius if exist.
     *
     * @return the radius
     */
    double getRadius();

    /**
     * Sets radius if its possible.
     *
     * @param radius the radius
     * @return the radius
     */
    LightSource setRadius(double radius);

    /**
     * Gets intensity in a given point.
     * each light source calculated difference
     *
     * @param point3D the point
     * @return the intensity
     */
    Color getIntensity(Point3D point3D);

    /**
     * Gets direction from spot Light, others return null
     *
     * @return the direction
     */
    Vector getDirection();

    /**
     * Gets distance from a given point to position.
     *
     * @param point3D the point 3 d
     * @return the distance
     */
    double getDistance(Point3D point3D);

    /**
     * Gets the vector from light source to the given point.
     *
     * @param point3D the point in the scene
     * @return the vector L normalized
     */
    Vector getL(Point3D point3D);

    /**
     * Gets points list
     *
     * @return the points
     */
    List<Point3D> getPoints();

    /**
     * Sets points ONLY for spotLight.
     *
     * @param randPo the random points
     * @return the interface (directional and point lights will not store nothing)
     */
    void setPoints(List<Point3D> randPo);
}

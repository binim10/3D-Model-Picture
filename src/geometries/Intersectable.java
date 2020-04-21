package geometries;

import primitives.*;


import java.util.List;

/**
 * The interface Intersectable.
 */
public interface Intersectable {
    /**
     * Find intersections list between giving ray and geometries shapes.
     * the returned list can be null if there is no intersections or there is no geometries shape in Geometries.
     *
     * @param ray the ray that intersect the geometry
     * @return List -  a list with all the the intersections points
     */
    List<Point3D> findIntersections(Ray ray);
}

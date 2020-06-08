package geometries;

import primitives.*;


import java.util.List;
import java.util.Objects;

/**
 * The interface Intersectable.
 */
public interface Intersectable {

    /**
     * The util class Get point.
     */
    class GeoPoint {

        public Geometry geometry;
        public Point3D point;

        /**
         * Instantiates a new Get point.
         *
         * @param geometry the geometry
         * @param point    the point
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geoPoint.geometry == this.geometry &&
                    geoPoint.point.equals(this.point);
        }


    }

    /**
     * Find intersections list between giving ray and geometries shapes.
     * the returned list can be null if there is no intersections or there is no geometries shape in Geometries.
     *
     * @param ray the ray that intersect the geometry
     * @return List -  a list with all the the intersections points
     */
    List<GeoPoint> findIntersections(Ray ray);

}

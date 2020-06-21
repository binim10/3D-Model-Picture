package geometries;

import primitives.*;


import java.util.List;
import java.util.Objects;

/**
 * The interface Intersectable.
 */
public abstract class Intersectable {

    double minX = Double.POSITIVE_INFINITY,
            minY = Double.POSITIVE_INFINITY,
            minZ = Double.POSITIVE_INFINITY,
            maxX = Double.NEGATIVE_INFINITY,
            maxY = Double.NEGATIVE_INFINITY,
            maxZ = Double.NEGATIVE_INFINITY;

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
    List<GeoPoint> findIntersections(Ray ray) {
        return null;
    }

    List<GeoPoint> findBox(Ray ray) {
        double rX = ray.getDirection().getHead().getX().get(),
                rY = ray.getDirection().getHead().getY().get(),
                rZ = ray.getDirection().getHead().getZ().get();

        double tMin = (this.minX - ray.getPOO().getX().get()) / rX;
        double tMax = (this.maxX - ray.getPOO().getX().get()) / rX;

        double txMin = Math.min(tMin, tMax);
        double txMax = Math.max(tMin, tMax);

        tMin = (this.minY - ray.getPOO().getY().get()) / rY;
        tMin = (this.maxY - ray.getPOO().getY().get()) / rY;

        double tyMin = Math.min(tMin, tMax);
        double tyMax = Math.max(tMin, tMax);

        tMin = (this.minZ - ray.getPOO().getZ().get()) / rZ;
        tMin = (this.maxZ - ray.getPOO().getZ().get()) / rZ;

        double tzMin = Math.min(tmin, Math.min(tz1, tz2));
        double tzMax = Math.max(tmax, Math.max(tz1, tz2));

        if (tmax >= tmin)
            return this.findIntersections(ray);
        else
            return null;
    }

    void createBox() {

    }

}

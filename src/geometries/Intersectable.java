package geometries;

import primitives.*;


import java.util.List;
import java.util.Objects;

import static primitives.Util.alignZero;

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


    public Boolean checkIntersectionWithBox(Ray ray) {
        Point3D originRay = ray.getPOO();
        Point3D headV = ray.getDirection().getHead();
        double rayX = alignZero(headV.getX().get());
        double rayY = alignZero(headV.getY().get());
        double rayZ = alignZero(headV.getZ().get());
        double rayPX = alignZero(originRay.getX().get());
        double rayPY = alignZero(originRay.getY().get());
        double rayPZ = alignZero(originRay.getZ().get());

        if (rayX == 0 && (rayPX > this.maxX || rayPX < this.minX))
            return false;
        if (rayX > 0 && this.maxX < rayPX)
            return false;
        if (rayX < 0 && this.minX > rayPX)
            return false;

        if (rayY == 0 && (rayPY > this.maxY || rayPY < this.minY))
            return false;
        if (rayY > 0 && this.maxY < rayPY)
            return false;
        if (rayY < 0 && this.minY > rayPY)
            return false;

        if (rayZ == 0 && (rayPZ > this.maxZ || rayPZ < this.minZ))
            return false;
        if (rayZ > 0 && this.maxZ < rayPZ)
            return false;
        if (rayZ < 0 && this.minZ > rayPZ)
            return false;
        return true;
    }


    void createBox() {

    }

}

package geometries;

import primitives.*;


import java.util.List;

import static primitives.Util.alignZero;

/**
 * The abstract Intersectable.
 */
public abstract class Intersectable {

    protected double _minX = Double.POSITIVE_INFINITY,
            _minY = Double.POSITIVE_INFINITY,
            _minZ = Double.POSITIVE_INFINITY,
            _maxX = Double.NEGATIVE_INFINITY,
            _maxY = Double.NEGATIVE_INFINITY,
            _maxZ = Double.NEGATIVE_INFINITY;

    public Boolean checkIntersectionWithBox(Ray ray) {
        Point3D originRay = ray.getPOO();
        Point3D headV = ray.getDirection().getHead();
        double rayVX = alignZero(headV.getX().get());
        double rayVY = alignZero(headV.getY().get());
        double rayVZ = alignZero(headV.getZ().get());
        double rayPX = alignZero(originRay.getX().get());
        double rayPY = alignZero(originRay.getY().get());
        double rayPZ = alignZero(originRay.getZ().get());

        if (rayVX == 0 && (rayPX > this._maxX || rayPX < this._minX))
            return false;
        if (rayVX > 0 && this._maxX < rayPX)
            return false;
        if (rayVX < 0 && this._minX > rayPX)
            return false;

        if (rayVY == 0 && (rayPY > this._maxY || rayPY < this._minY))
            return false;
        if (rayVY > 0 && this._maxY < rayPY)
            return false;
        if (rayVY < 0 && this._minY > rayPY)
            return false;

        if (rayVZ == 0 && (rayPZ > this._maxZ || rayPZ < this._minZ))
            return false;
        if (rayVZ > 0 && this._maxZ < rayPZ)
            return false;
        return !(rayVZ < 0) || !(this._minZ > rayPZ);
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

    /**
     * The util class Get point.
     */
    public static class GeoPoint {

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

    void createBox() {

    }

}

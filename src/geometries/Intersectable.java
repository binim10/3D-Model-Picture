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

    /**
     * boolean func to check if the ray intersect the ray
     *
     * @param ray
     * @return true or false
     */
    public Boolean checkIntersectionWithBox(Ray ray) {

        Point3D maxPoi = new Point3D(_maxX, _maxY, _maxZ);
        Point3D minPoi = new Point3D(_minX, _minY, _minZ);
        double pCX = ray.getPOO().getX().get();
        double pCY = ray.getPOO().getY().get();
        double pCZ = ray.getPOO().getZ().get();

        double pVX = ray.getDirection().getHead().getX().get();
        double pVY = ray.getDirection().getHead().getY().get();
        double pVZ = ray.getDirection().getHead().getZ().get();

        Point3D invDir = new Point3D(1d / pVX, 1d / pVY, 1d / pVZ);

        boolean signDirX = invDir.getX().get() < 0;
        boolean signDirY = invDir.getY().get() < 0;
        boolean signDirZ = invDir.getZ().get() < 0;

        Point3D bbox = signDirX ? maxPoi : minPoi;
        double tXmin = (bbox.getX().get() - pCX) * invDir.getX().get();
        bbox = signDirX ? minPoi : maxPoi;
        double tXmax = (bbox.getX().get() - pCX) * invDir.getX().get();
        bbox = signDirY ? maxPoi : minPoi;
        double tYmin = (bbox.getY().get() - pCY) * invDir.getY().get();
        bbox = signDirY ? minPoi : maxPoi;
        double tYmax = (bbox.getY().get() - pCY) * invDir.getY().get();

        if ((tXmin > tYmax) || (tYmin > tXmax)) {
            return null;
        }
        if (tYmin > tXmin) {
            tXmin = tYmin;
        }
        if (tYmax < tXmax) {
            tXmax = tYmax;
        }

        bbox = signDirZ ? maxPoi : minPoi;
        double tzmin = (bbox.getZ().get() - pCZ) * invDir.getZ().get();
        bbox = signDirZ ? minPoi : maxPoi;
        double tzmax = (bbox.getZ().get() - pCZ) * invDir.getZ().get();

        if ((tXmin > tzmax) || (tzmin > tXmax)) {
            return false;
        }
        if (tzmin > tXmin) {
            tXmin = tzmin;
        }
        if (tzmax < tXmax) {
            tXmax = tzmax;
        }
        return true;
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

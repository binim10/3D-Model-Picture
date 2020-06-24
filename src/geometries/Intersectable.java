package geometries;

import primitives.*;


import java.util.List;

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
        Point3D pV = ray.getDirection().getHead();
        Point3D pC = ray.getPOO();
        double pCX = pC.getX().get();
        double pCY = pC.getY().get();
        double pCZ = pC.getZ().get();

        double pVX = pV.getX().get();
        double pVY = pV.getY().get();
        double pVZ = pV.getZ().get();


        double tXmin = (_minX - pCX) / pVX;
        double tXmax = (_maxX - pCX) / pVX;
        if (tXmin > tXmax) {
            double temp = tXmin;
            tXmin = tXmax;
            tXmax = temp;
        }

        double tYmin = (_minY - pCY) / pVY;
        double tYmax = (_maxY - pCY) / pVY;
        if (tYmin > tYmax) {
            double temp = tYmin;
            tYmin = tYmax;
            tYmax = temp;
        }
        if ((tXmin > tYmax) || (tYmin > tXmax))
            return false;

        if (tYmin > tXmin)
            tXmin = tYmin;

        if (tYmax < tXmax)
            tXmax = tYmax;


        double tZmin = (_minZ - pCZ) / pVZ;
        double tZmax = (_maxZ - pCZ) / pVZ;

        if (tZmin > tZmax) {
            double temp = tZmin;
            tZmin = tZmax;
            tZmax = temp;
        }
        return ((tXmin <= tZmax) && (tZmin <= tXmax));
    }


    /**
     * Find intersections list between giving ray and geometries shapes.
     * the returned list can be null if there is no intersections or there is no geometries shape in Geometries.
     *
     * @param ray the ray that intersect the geometry
     * @return List -  a list with all the the intersections points
     */
    public abstract List<GeoPoint> findIntersections(Ray ray);

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

    protected abstract void createBox();

    public List<GeoPoint> findIntersectionsBB(Ray ray) {
        if (checkIntersectionWithBox(ray))
            return findIntersections(ray);
        return null;
    }
}

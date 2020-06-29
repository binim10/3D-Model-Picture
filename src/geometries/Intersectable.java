package geometries;

import primitives.*;
import java.util.List;

/**
 * The abstract Intersectable.
 */
public abstract class Intersectable {

    /**
     * The Min x.
     */
    protected double _minX = Double.POSITIVE_INFINITY,
    /**
     * The Min y.
     */
    _minY = Double.POSITIVE_INFINITY,
    /**
     * The Min z.
     */
    _minZ = Double.POSITIVE_INFINITY,
    /**
     * The Max x.
     */
    _maxX = Double.NEGATIVE_INFINITY,
    /**
     * The Max y.
     */
    _maxY = Double.NEGATIVE_INFINITY,
    /**
     * The Max z.
     */
    _maxZ = Double.NEGATIVE_INFINITY;

    /**
     * Calculate the distance between two geometries
     * according the middle of they boxes
     *
     * @param geoI the geo i
     * @param geoJ the geo j
     * @return The distance between geoI to geoJ
     */
    public static double distance(Intersectable geoI, Intersectable geoJ) {
        double midX, midY, midZ;
        midX = (geoI._minX + geoI._maxX) / 2d;
        midY = (geoI._minY + geoI._maxY) / 2d;
        midZ = (geoI._minZ + geoI._maxZ) / 2d;
        Point3D pGeoI = new Point3D(midX, midY, midZ);

        midX = (geoJ._minX + geoJ._maxX) / 2d;
        midY = (geoJ._minY + geoJ._maxY) / 2d;
        midZ = (geoJ._minZ + geoJ._maxZ) / 2d;
        Point3D pGeoJ = new Point3D(midX, midY, midZ);

        return pGeoI.distance(pGeoJ);
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
     * boolean func to check if the ray intersect the ray
     *
     * @param ray the ray
     * @return true or false
     */
    public Boolean checkIntersectionWithBox(Ray ray) {
        Point3D rayVectorHead = ray.getDirection().getHead();
        Point3D rayPoint = ray.getPOO();
        double pCX = rayPoint.getX().get();
        double pCY = rayPoint.getY().get();
        double pCZ = rayPoint.getZ().get();

        double pVX = rayVectorHead.getX().get();
        double pVY = rayVectorHead.getY().get();
        double pVZ = rayVectorHead.getZ().get();

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
     * Create box for geometry
     * use different method for each geometry
     */
    protected abstract void createBox();

    /**
     * Find intersections with BVH.
     *
     * @param ray the ray
     * @return the list
     */
    public List<GeoPoint> findIntersectionsBB(Ray ray) {
        if (checkIntersectionWithBox(ray))
            return findIntersections(ray);
        return null;
    }

    /**
     * The util class Geopoint.
     */
    public static class GeoPoint {

        /**
         * The Geometry.
         */
        public Geometry geometry;
        /**
         * The Point.
         */
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
}

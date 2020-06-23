package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Geometries to hold collection of intersectable's
 */
public class Geometries extends Intersectable {
    private final List<Intersectable> _geometries;

    /**
     * Instantiates a new Geometries.
     */
    public Geometries() {
        _geometries = new LinkedList<Intersectable>();
    }

    /**
     * Instantiates a new Geometries.
     *
     * @param geometries the geometries
     */
    public Geometries(Intersectable... geometries) {
        this._geometries = Arrays.asList(geometries);
    }

    /**
     * Add Intersectables to the list
     *
     * @param geometries the geometries
     */
    public void add(Intersectable... geometries) {
        for (Intersectable geo : geometries) {
            geo.createBox();
            this.createBox(geo);
            _geometries.add(geo);
        }
    }


    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        List<GeoPoint> intersections = null;//if there isn't any intersections the list will be null.
        for (Intersectable geo : _geometries) {
            List<GeoPoint> tempIntersections = geo.findIntersections(ray);
            if (tempIntersections != null) {
                if (intersections == null)
                    intersections = new LinkedList<GeoPoint>();
                intersections.addAll(tempIntersections);
            }
        }
        return intersections;
    }

    @Override
    public void createBox() {
        _minX = Double.POSITIVE_INFINITY;
        _minY = Double.POSITIVE_INFINITY;
        _minZ = Double.POSITIVE_INFINITY;
        _maxX = Double.NEGATIVE_INFINITY;
        _maxY = Double.NEGATIVE_INFINITY;
        _maxZ = Double.NEGATIVE_INFINITY;

    }

    @Override
    public List<GeoPoint> findIntersectionsBB(Ray ray) {
        List<GeoPoint> intersections = null;
        List<GeoPoint> tempIntersection = null;
        for (Intersectable geo : _geometries) {
            if (geo.checkIntersectionWithBox(ray)) {
                tempIntersection = new LinkedList<GeoPoint>();
                tempIntersection = geo.findIntersectionsBB(ray);
            }
            if (tempIntersection != null) {
                intersections = new LinkedList<GeoPoint>(tempIntersection);
            }
        }
        return intersections;
    }


    void createBox(Intersectable inter) {
        _minX = Math.min(inter._minX, _minX);
        _maxX = Math.max(inter._maxX, _maxX);
        _minY = Math.min(inter._minY, _minY);
        _maxY = Math.max(inter._maxY, _maxY);
        _minZ = Math.min(inter._minZ, _minZ);
        _maxZ = Math.max(inter._maxZ, _maxZ);
    }
}

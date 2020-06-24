package geometries;

import primitives.Ray;

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
        _geometries = new LinkedList<>();
    }

    /**
     * Instantiates a new Geometries.
     *
     * @param geometries the geometries
     */
    public Geometries(Intersectable... geometries) {
        _geometries = new LinkedList<>();
        this.add(geometries);

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
                    intersections = new LinkedList<>();
                intersections.addAll(tempIntersections);
            }
        }
        return intersections;
    }

    @Override
    public void createBox() {
    }

    @Override
    public List<GeoPoint> findIntersectionsBB(Ray ray) {
        List<GeoPoint> intersections = null;
        List<GeoPoint> tempIntersection = null;
        for (Intersectable geo : _geometries) {
            if (geo.checkIntersectionWithBox(ray)) {
                tempIntersection = geo.findIntersectionsBB(ray);
            }
            if (tempIntersection != null) {
                if (intersections == null)
                    intersections = new LinkedList<>();
                intersections.addAll(tempIntersection);
            }
        }
        return intersections;
    }


    void createBox(Intersectable inter) {
        this._minX = Math.min(inter._minX, this._minX);
        this._maxX = Math.max(inter._maxX, this._maxX);
        this._minY = Math.min(inter._minY, this._minY);
        this._maxY = Math.max(inter._maxY, this._maxY);
        this._minZ = Math.min(inter._minZ, this._minZ);
        this._maxZ = Math.max(inter._maxZ, this._maxZ);
    }
}

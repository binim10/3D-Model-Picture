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
public class Geometries implements Intersectable {
    private List<Intersectable> _geometries;

    /**
     * Instantiates a new Geometries.
     */
    public Geometries() {
        _geometries = new ArrayList<Intersectable>();
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
        _geometries.addAll(Arrays.asList(geometries));
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
}

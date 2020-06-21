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
        List<GeoPoint> intersections = new LinkedList<GeoPoint>();
        boolean a = false;
        List<GeoPoint> tempIntersection = null;
        for (Intersectable geo : _geometries) {
            if (geo.checkIntersectionWithBox(ray))
                tempIntersection = geo.findIntersections(ray);
            if (tempIntersection != null)
                intersections.addAll(tempIntersection);
            tempIntersection = null;
        }
        return intersections.isEmpty() ? null : intersections;
    }


    void createBox(Intersectable inter) {
        minX = inter.minX < minX ? inter.minX : minX;
        maxX = inter.maxX > maxX ? inter.maxX : maxX;
        minY = inter.minY < minY ? inter.minY : minY;
        maxY = inter.maxY > maxY ? inter.maxY : maxY;
        minZ = inter.minZ < minZ ? inter.minZ : minZ;
        maxZ = inter.maxZ > maxZ ? inter.maxZ : maxZ;
    }
}

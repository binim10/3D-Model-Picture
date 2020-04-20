package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Arrays;
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
        _geometries=new ArrayList<Intersectable>();
    }

    /**
     * Instantiates a new Geometries.
     *
     * @param geometries the geometries
     */
    public Geometries(Intersectable... geometries) {
        this._geometries =Arrays.asList(geometries);
    }

    /**
     * Add Intersectable's to the list
     *
     * @param geometries the geometries
     */
    public void add(Intersectable... geometries){
        _geometries.addAll(Arrays.asList(geometries));
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}

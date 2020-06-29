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


    /**
     * Create box.
     *
     * @param inter the inter
     */
    void createBox(Intersectable inter) {
        this._minX = Math.min(inter._minX, this._minX);
        this._maxX = Math.max(inter._maxX, this._maxX);
        this._minY = Math.min(inter._minY, this._minY);
        this._maxY = Math.max(inter._maxY, this._maxY);
        this._minZ = Math.min(inter._minZ, this._minZ);
        this._maxZ = Math.max(inter._maxZ, this._maxZ);
    }

    /**
     * Create a BVH hierarchy tree from collection of geometries
     * Note : the tree contained only the finite geometries (and the infinite geometries
     * will be separately within the collection)
     */
    public void buildHierarchyTreeN3() {
        List<Intersectable> infiniteGeometries = new LinkedList<>();//temp list for the plane
        for(Intersectable geo: _geometries){
            if(geo instanceof Plane)
                infiniteGeometries.add(geo);
        }
        _geometries.removeAll(infiniteGeometries);
        double distance=0;
        Intersectable left = null;
        Intersectable right = null;
        while (_geometries.size() > 1) {//stop when has only one geometries in the list
            double best = Double.POSITIVE_INFINITY;
            for (Intersectable geoI : _geometries)
                for (Intersectable geoJ : _geometries) {
                    if (geoI != geoJ && (distance = distance(geoI, geoJ)) < best) {
                        best = distance;
                        left = geoI;
                        right = geoJ;
                    }
                }
            Geometries tempGeometries = new Geometries(left, right);
            _geometries.remove(left);
            _geometries.remove(right);
            _geometries.add(tempGeometries);
            System.out.println(_geometries.size());
        }
        _geometries.addAll(infiniteGeometries);//after i have only one geometry in the list, i add the infinite geometries to the list
    }

    /**
     * Create a BVH hierarchy tree from collection of geometries
     * Note : the tree contained only the finite geometries (and the infinite geometries
     * will be separately within the collection)
     */
    public void buildHierarchyTreeN2() {
        List<Intersectable> infiniteGeometries = new LinkedList<>();//temp list for the plane
        for(Intersectable geo: _geometries){
            if(geo instanceof Plane)
                infiniteGeometries.add(geo);
        }
        _geometries.removeAll(infiniteGeometries);
        double distance=0;
        Intersectable left = null;
        Intersectable right = null;
        while (_geometries.size() > 1) {//stop when has only one geometries in the list
            double best = Double.POSITIVE_INFINITY;
            Intersectable geoI = _geometries.get(0);
                for (Intersectable geoJ : _geometries) {
                    if (geoI != geoJ && (distance = distance(geoI, geoJ)) < best) {
                        best = distance;
                        left = geoI;
                        right = geoJ;
                    }
                }
            Geometries tempGeometries = new Geometries(left, right);
            _geometries.remove(left);
            _geometries.remove(right);
            _geometries.add(tempGeometries);
            System.out.println(_geometries.size());
        }
        _geometries.addAll(infiniteGeometries);//after i have only one geometry in the list, i add the infinite geometries to the list
    }

   public void buildHierarchyTree(){
        if (_geometries.size()>1000)
            buildHierarchyTreeN2();
        else
            buildHierarchyTreeN3();
   }
}



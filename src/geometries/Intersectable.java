package geometries;

import primitives.*;
//import primitives.Ray;

import java.util.List;

public interface Intersectable {
    List<Point3D> findIntersections(Ray ray);
}

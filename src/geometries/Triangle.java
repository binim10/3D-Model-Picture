package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The type Triangle.
 */
public class Triangle extends Polygon {

    /**
     * Instantiates a new Triangle.
     *
     * @param a the a
     * @param b the b
     * @param c the c
     */
    public Triangle(Point3D a, Point3D b, Point3D c) {
        super(a, b, c);
    }

    @Override
    public String toString() {
        return "Triangle:" + super.toString();
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        if (_plane.findIntersections(ray) == null) // Ray doesn't intersect with the triangle
            return null;

        List<Vector> vectors = new ArrayList<>(3);
        for (int i = 0; i < 3; ++i) {
            vectors.add(new Vector(ray.getPOO()).subtract(new Vector(_vertices.get(i))));
        }

        List<Vector> normals = new ArrayList<>(3);
        for (int i = 0; i < 2; ++i) {
            normals.add(vectors.get(i).crossProduct(vectors.get(i + 1)));
        }
        normals.add(vectors.get(2).crossProduct(vectors.get(0)));

        int plus = 0, minus = 0;
        for (int i = 0; i < 3; ++i) {
            double t = alignZero(normals.get(i).dotProduct(ray.getDirection()));
            if (isZero(t))
                return null;
            if (t > 0)
                plus++;
            else
                minus++;
        }
        if (plus != 3 && minus != 3)
            return null;
        return _plane.findIntersections(ray);
    }
}

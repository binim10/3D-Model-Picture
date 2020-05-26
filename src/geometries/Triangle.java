package geometries;

import primitives.*;

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
        this(Color.BLACK, a, b, c);
    }

    /**
     * Instantiates a new Triangle withe color.
     *
     * @param color the color of the triangle.
     * @param a     the a
     * @param b     the b
     * @param c     the c
     */
    public Triangle(Color color, Point3D a, Point3D b, Point3D c) {
        this(Color.BLACK, new Material(0, 0, 0), a, b, c);
    }

    /**
     * Instantiates a new Triangle with material.
     *
     * @param color    the color
     * @param material the material
     * @param point3D  the point 3 d
     * @param point3D1 the point 3 d 1
     * @param point3D2 the point 3 d 2
     */
    public Triangle(Color color, Material material, Point3D point3D, Point3D point3D1, Point3D point3D2) {
        super(color, material, point3D, point3D1, point3D2);
    }

    @Override
    public String toString() {
        return "Triangle:" + super.toString();
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        List<GeoPoint> plaInter = _plane.findIntersections(ray);
        if (plaInter == null) // Ray doesn't intersect with the triangle
            return null;

        for (GeoPoint g : plaInter) {
            g.geometry = this;
        }

        List<Vector> vectors = new ArrayList<>(3);
        for (int i = 0; i < 3; ++i) {
            vectors.add(new Vector(_vertices.get(i).subtract(ray.getPOO())).normalize());
        }

        List<Vector> normals = new ArrayList<>(3);
        for (int i = 0; i < 2; ++i) {
            normals.add(vectors.get(i).crossProduct(vectors.get(i + 1)).normalize());
        }
        normals.add(vectors.get(2).crossProduct(vectors.get(0)).normalize());

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
        return plaInter;

    }
}

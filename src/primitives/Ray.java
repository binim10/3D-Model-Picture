package primitives;

import elements.LightSource;
import elements.SpotLight;

import java.util.LinkedList;
import java.util.List;

/**
 * The type Ray.
 */
public class Ray {

    /**
     * @param DELTA help me to move the point that help to true shadoe
     */
    private static final double DELTA = 0.1;

    // Point of origin
    private Point3D _POO;

    // Ray direction
    private Vector _direction;

    /**
     * Instantiates a new Ray.
     *
     * @param poo       the poo
     * @param direction the direction
     */
    public Ray(Point3D poo, Vector direction) {
        //check if direction is normalized
        this._POO = new Point3D(poo);
        this._direction = direction.normalized();
    }

    public Ray(Point3D poo, Vector direction, Vector n) {
        Vector delta = n.scale(n.dotProduct(direction) > 0 ? DELTA : -DELTA);
        _POO = new Point3D(poo.add(delta));
        _direction = direction.normalized();
    }

    /**
     * Instantiates a new Ray.
     *
     * @param ray the ray
     */
    public Ray(Ray ray) {
        this._POO = new Point3D(ray.getPOO());
        this._direction = new Vector(ray.getDirection());
    }

    /**
     * Gets poo.
     *
     * @return the poo
     */
    public Point3D getPOO() {
        return this._POO;
    }

    /**
     * Gets direction.
     *
     * @return the direction
     */
    public Vector getDirection() {
        return this._direction.normalize();
    }

    /**
     * Calculate to find point on Ray with a given t.
     *
     * @param t the t
     * @return the point 3D
     */
    public Point3D getPoint(double t) {
        return getPOO().add(getDirection().scale(t));
    }

    public List<Ray> createBeamRays(LightSource ls, Point3D point, Vector normal, int numRays) {
        List<Ray> rayList = new LinkedList<Ray>();
        rayList.add(this);
        List<Point3D> pointList = ls.getPoints();
        if (pointList == null && ls.getRadius() > 0) {//it is not directional light and it has a radius
            pointList = ls.getPosition().createRandomPoints(_direction.normalize(), ls.getRadius(), numRays);
            ls.setPoints(pointList);//it will store the points if is spot, other it will do nothing
        }
        if (pointList != null) {
            for (Point3D p : pointList) {
                Ray r = new Ray(point, p.subtract(point).normalize(), normal);
                rayList.add(r);
            }
        }
        return rayList;
    }

    @Override
    public String toString() {
        return "Ray:" +
                "_POO=" + _POO +
                ", _direction=" + _direction +
                '\n';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Ray)) return false;
        Ray temp = (Ray) obj;
        return _POO.equals(temp._POO) && _direction.equals(temp._direction);
    }

}

package primitives;

import elements.LightSource;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.getRandom;

/**
 * The type Ray.
 */
public class Ray {

    private static final double DELTA = 0.1;
    private final Point3D _POO;
    private final Vector _direction;

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

    /**
     * Instantiates a new Ray.
     *
     * @param poo       the poo
     * @param direction the direction
     * @param n         the n
     */
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
     * Gets point of start
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

    /**
     * Create beam rays list from given point to the light source.
     * we decided to put this method in ray class because its create Ray's.
     *
     * @param ls      the LightSource
     * @param point   the point i want to calculate its color
     * @param normal  the normal to the point
     * @param numRays the number of rays in the beam
     * @return the list
     */
    public List<Ray> createRaysBeam(LightSource ls, Point3D point, Vector normal, int numRays) {
        List<Ray> rayList = new LinkedList<>();
        rayList.add(this);
        List<Point3D> pointList = ls.getPoints();//in spot light i have already random points
        if (pointList == null && ls.getRadius() > 0) {//it is not directional light and it has a radius
            Vector lightDirection = ls.getDirection();//only spot light return vector, other return null
            if (lightDirection != null) {//case spot light in first time
                pointList = createRandomPoints(ls.getPosition(), lightDirection, ls.getRadius(), numRays);
                ls.setPoints(pointList);
            } else//case point light
                pointList = createRandomPoints(ls.getPosition(), _direction, ls.getRadius(), numRays);
        }
        if (pointList != null) {
            for (Point3D p : pointList) {
                Ray r = new Ray(point, p.subtract(point).normalize(), normal);
                rayList.add(r);
            }
        }
        return rayList;
    }

    /**
     * Create random points surround the point by given normal and radius.
     * help method to createRaysBeam. (RDD)
     * @param centerPoint the center point
     * @param direction   the direction
     * @param radius      the radius
     * @param numRays     the num rays
     * @return the list
     */
    private List<Point3D> createRandomPoints(Point3D centerPoint, Vector direction, double radius, int numRays) {
        List<Point3D> randomPoints = new LinkedList<>();
        Vector vX = direction.createNormal();
        Vector vY = vX.crossProduct(direction);
        double x, y;
        for (int i = 0; i < numRays; i++) {
            x = getRandom(-1, 1);
            y = Math.sqrt(1 - x * x);
            Point3D p = centerPoint;
            x = alignZero(x * (getRandom(-radius, radius)));
            y = alignZero(y * (getRandom(-radius, radius)));
            if (x != 0)
                p = p.add(vX.scale(x));
            if (y != 0)
                p = p.add(vY.scale(y));
            randomPoints.add(p);
        }
        return randomPoints;
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

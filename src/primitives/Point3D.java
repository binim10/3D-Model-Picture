package primitives;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static primitives.Util.alignZero;
import static primitives.Util.getRandom;

/**
 * The type Point 3 d.
 */
public class Point3D {
    public final static Point3D ZERO = new Point3D(0, 0, 0);

    Coordinate _x;
    Coordinate _y;
    Coordinate _z;

    /****************** Constructor *******************/
    /**
     * Constructor for creating a point
     *
     * @param _x coordinate on the x axis
     * @param _y coordinate on the y axis
     * @param _z coordinate on the z axis
     */
    public Point3D(Coordinate _x, Coordinate _y, Coordinate _z) {
        this._x = new Coordinate(_x);
        this._y = new Coordinate(_y);
        this._z = new Coordinate(_z);
    }

    /**
     * Constructor get doubles
     *
     * @param _x the x
     * @param _y the y
     * @param _z the z
     */
    public Point3D(double _x, double _y, double _z) {
        this(new Coordinate(_x), new Coordinate(_y), new Coordinate(_z));
    }

    /**
     * copy Constructor
     *
     * @param _point the point
     */
    public Point3D(Point3D _point) {
        this._x = _point._x;
        this._y = _point._y;
        this._z = _point._z;
    }

    /******************* getters ****************/
    /**
     * Gets x.
     *
     * @return new Coordinate with _x value
     */
    public Coordinate getX() {
        return new Coordinate(_x);
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public Coordinate getY() {
        return new Coordinate(_y);
    }

    /**
     * Gets z.
     *
     * @return the z
     */
    public Coordinate getZ() {
        return new Coordinate(_z);
    }

    /*******************functions****************/

    /**
     * Add vector to the point
     *
     * @param _vector the vector
     * @return Point3D point 3 d
     */
    public Point3D add(Vector _vector) {
        return new Point3D(
                this.getX().get() + _vector._head.getX().get(),
                this.getY().get() + _vector._head.getY().get(),
                this.getZ().get() + _vector._head.getZ().get());
    }

    /**
     * calculate vector between two points
     *
     * @param vertex the vertex
     * @return vector vector
     */
    public Vector subtract(Point3D vertex) {
        return new Vector(
                this.getX().get() - vertex.getX().get(),
                this.getY().get() - vertex.getY().get(),
                this.getZ().get() - vertex.getZ().get()
        );
    }


    /**
     * calculate the distance Squared
     *
     * @param point the point
     * @return double double
     */
    public double distanceSquared(Point3D point) {
        return ((point.getX().get() - this.getX().get()) * (point.getX().get() - this.getX().get()) +
                (point.getY().get() - this.getY().get()) * (point.getY().get() - this.getY().get()) +
                (point.getZ().get() - this.getZ().get()) * (point.getZ().get() - this.getZ().get()));
    }

    /**
     * calculate the distance between points
     * by using the root of distanceSquared
     *
     * @param point the point
     * @return double double
     */
    public double distance(Point3D point) {
        return Math.sqrt(distanceSquared(point));
    }

    /**
     * Create random points surround the point by given normal and radius.
     *
     * @param direction the direction
     * @param radius    the radius
     * @return the list
     */
    public List<Point3D> createRandomPoints(Vector direction, double radius, int numRays) {
        if (radius == 0)
            return null;
        List<Point3D> randomPoints = new LinkedList<Point3D>();
        Vector vX = direction.normalize().createNormal();
        Vector vY = vX.crossProduct(direction.normalize());
        double x, y;
        for (int i = 0; i < numRays; i++) {
            x = getRandom(-1, 1);
            y = Math.sqrt(1 - x * x);
            Point3D p = this;
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

    /************override functions*************/
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point3D)) return false;
        Point3D temp = (Point3D) obj;
        return _x.equals(temp._x) && _y.equals(temp._y) && _z.equals(temp._z);
    }

    @Override
    public String toString() {
        return "(" +
                getX().toString() +
                ", " + getY().toString() +
                ", " + getZ().toString() +
                ')';
    }


}
package primitives;

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
    public Coordinate get_x() {
        return new Coordinate(_x);
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public Coordinate get_y() {
        return new Coordinate(_y);
    }

    /**
     * Gets z.
     *
     * @return the z
     */
    public Coordinate get_z() {
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
                this.get_x().get() + _vector._head.get_x().get(),
                this.get_y().get() + _vector._head.get_y().get(),
                this.get_z().get() + _vector._head.get_z().get());
    }/**/

    /**
     * calculate vector between two points
     *
     * @param vertex the vertex
     * @return vector vector
     */
    public Vector subtract(Point3D vertex) {
        return new Vector(
                this.get_x().get() - vertex.get_x().get(),
                this.get_y().get() - vertex.get_y().get(),
                this.get_z().get() - vertex.get_z().get()
        );
    }


    /**
     * calculate the distance Squared
     *
     * @param point the point
     * @return double double
     */
    public double distanceSquared(Point3D point) {
        return ((point.get_x().get() - this.get_x().get()) * (point.get_x().get() - this.get_x().get()) +
                (point.get_y().get() - this.get_y().get()) * (point.get_y().get() - this.get_y().get()) +
                (point.get_z().get() - this.get_z().get()) * (point.get_z().get() - this.get_z().get()));
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
                get_x().toString() +
                ", " + get_y().toString() +
                ", " + get_z().toString() +
                ')';
    }


}
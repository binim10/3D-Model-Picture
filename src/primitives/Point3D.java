package primitives;

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
     * @param _x
     * @param _y
     * @param _z
     */
    public Point3D(double _x, double _y, double _z) {
        this(new Coordinate(_x), new Coordinate(_y), new Coordinate(_z));
    }

    /**
     * copy Constructor
     *
     * @param _point
     */
    public Point3D(Point3D _point) {
        this._x = _point._x;
        this._y = _point._y;
        this._z = _point._z;
    }

    /******************* getters ****************/
    /**
     * @return new Coordinate with _x value
     */
    public Coordinate get_x() {
        return new Coordinate(_x);
    }

    public Coordinate get_y() {
        return new Coordinate(_y);
    }

    public Coordinate get_z() {
        return new Coordinate(_z);
    }

    /*******************functions****************/

    /**
     * Add vector to the point
     *
     * @param _vector
     * @return Point3D
     */
    public Point3D add(Vector _vector){
        return new Point3D(
                this.get_x()._coord+_vector._head.get_x()._coord,
                this.get_y()._coord+_vector._head.get_y()._coord,
                this.get_z()._coord+_vector._head.get_z()._coord);
    }
    /**
     * calculate vector between two points
     *
     * @param vertex
     * @return vector
     */
    public Vector subtract(Point3D vertex) {
        return new Vector(
                this.get_x()._coord-vertex.get_x()._coord,
                this.get_y()._coord - vertex.get_y()._coord,
                this.get_z()._coord - vertex.get_z()._coord
        );
    }


    /**
     * calculate the distance Squared
     *
     * @param _point
     * @return double
     */
    public double distanceSquared (Point3D _point){
        return ((_point.get_x()._coord-this.get_x()._coord)*(_point.get_x()._coord-this.get_x()._coord)+
                (_point.get_y()._coord-this.get_y()._coord)*(_point.get_y()._coord-this.get_y()._coord)+
                (_point.get_z()._coord-this.get_z()._coord)*(_point.get_z()._coord-this.get_z()._coord));
    }

    /**
     * calculate the distance between points
     * by using the root of distanceSquared
     *
     * @param _point
     * @return double
     */
    public double distance(Point3D _point){
        return Math.sqrt(distanceSquared(_point));
    }
    /************override functions*************/
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point3D)) return false;
        Point3D temp = (Point3D) obj;
        return _x.equals(temp._x) && _y.equals(temp._y)&&_z.equals(temp._z);
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
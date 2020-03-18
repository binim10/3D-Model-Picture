package primitives;

public class Vector {
    Point3D _head;

    /**
     * Constructor who get 3 coordinates
     *
     * @param _x
     * @param _y
     * @param _z
     */
    public Vector(Coordinate _x, Coordinate _y, Coordinate _z) {
        if (Point3D.ZERO.equals(new Point3D(_x, _y, _z)))
            throw new IllegalArgumentException("Vector cannot be ZERO");
        this._head = new Point3D(_x, _y, _z);

    }

    public Vector(double _x, double _y, double _z) {
        if (Point3D.ZERO.equals(new Point3D(_x, _y, _z)))
            throw new IllegalArgumentException("Vector cannot be ZERO");
        this._head = new Point3D(_x, _y, _z);
    }

    /**
     * @param _head
     */
    public Vector(Point3D point3D) {
        if (Point3D.ZERO.equals(point3D))
            throw new IllegalArgumentException("Vector cannot be ZERO");
        this._head = new Point3D(point3D);
    }

    public Vector(Vector other) {
        this._head = new Point3D(other._head);
    }

    public Point3D get_head() {
        return _head;
    }

    /**
     * add vector to another using add method in Point3D class
     *
     * @param other
     * @return Vector
     */
    public Vector add(Vector other){
        return new Vector(this._head.add(other));//go to ctor of point3d
    }

    /**
     * subtract vector from another using subtract method in Point3D class
     *
     * @param _vector
     * @return
     */
    public Vector subtract(Vector _vector){
        return new Vector(this._head.subtract(_vector._head));//go to ctor of vector
    }

    /**
     * Scale
     * @param _scalar
     * @return Vector
     */
    public Vector scale(double _scalar){
        return new Vector(this._head._x._coord*_scalar,
                this._head._y._coord*_scalar,
                this._head._z._coord*_scalar);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _head.equals(vector._head);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "_head=" + _head +
                '}';
    }

    /**
     * @param other Vector
     * @return dotproduct (double)
     */
    public double dotProduct(Vector other) {
        return _head._x.get() * other._head._x.get() +
                _head._y.get() * other._head._y.get() +
                _head._z.get() * other._head._z.get();
    }

    /**
     * @param other Vector
     * @return Vector for cross product using right thumb rule
     */
    public Vector crossProduct(Vector other) {
        return Vector.ZERO;
    }
}
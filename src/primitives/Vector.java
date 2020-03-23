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
     * constructor
     *
     * @param point3D
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
    public Vector add(Vector other) {
        return new Vector(this._head.add(other));//go to ctor of point3d
    }

    /**
     * subtract vector from another using subtract method in Point3D class
     *
     * @param _vector
     * @return
     */
    public Vector subtract(Vector _vector) {
        return new Vector(this._head.subtract(_vector._head));//go to ctor of vector
    }

    /**
     * Scale
     *
     * @param _scalar
     * @return Vector
     */
    public Vector scale(double _scalar) {
        return new Vector(this._head.get_x()._coord * _scalar,
                this._head.get_y()._coord * _scalar,
                this._head.get_z()._coord * _scalar);
    }

    /**
     * dotProduct
     *
     * @param _vec
     * @return
     */
    public double dotProduct(Vector _vec) {
        return (this._head.get_x()._coord * _vec._head.get_x()._coord) +
                (this._head.get_y()._coord * _vec._head.get_y()._coord) +
                (this._head.get_z()._coord * _vec._head.get_z()._coord);

    }

    /**
     *
     * @param _vector
     * @return
     */
    public Vector crossProduct(Vector _vector) {
        return new Vector((new Point3D(
                this._head.get_y()._coord * _vector._head.get_z()._coord - this._head.get_z()._coord * _vector._head.get_y()._coord,
                this._head.get_z()._coord * _vector._head.get_x()._coord - this._head.get_x()._coord * _vector._head.get_z()._coord,
                this._head.get_x()._coord * _vector._head.get_y()._coord - this._head.get_y()._coord * _vector._head.get_x()._coord)));
    }

    public double lengthSquared(){
        return Point3D.ZERO.distanceSquared(this._head);
    }

    public double length(){
        return Point3D.ZERO.distance(this._head);
    }

    public Vector normalize(){
         this._head=new Point3D(this._head.get_x()._coord/this.length(),
                 this._head.get_y()._coord/this.length(),
                 this._head.get_z()._coord/length());
         return this;
    }

    public Vector normalized(){
        Vector temp=new Vector(this._head);
        return temp.normalize();
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


}
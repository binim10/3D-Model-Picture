package primitives;

public class Vector {
    protected Point3D _head;

    /****************** Constructor *******************/
    /**
     * Constructor who get 3 coordinates
     *
     * @param _x the x
     * @param _y the y
     * @param _z the z
     * @throws IllegalArgumentException if the argument is zero
     */
    public Vector(Coordinate _x, Coordinate _y, Coordinate _z) {
        if (Point3D.ZERO.equals(new Point3D(_x, _y, _z)))
            throw new IllegalArgumentException("Vector cannot be ZERO");
        this._head = new Point3D(_x, _y, _z);

    }

    /**
     * Instantiates a new Vector.
     *
     * @param _x the x
     * @param _y the y
     * @param _z the z
     * @throws IllegalArgumentException if the argument is zero
     */
    public Vector(double _x, double _y, double _z) {
        if (Point3D.ZERO.equals(new Point3D(_x, _y, _z)))
            throw new IllegalArgumentException("Vector cannot be ZERO");
        this._head = new Point3D(_x, _y, _z);
    }

    /**
     * constructor
     *
     * @param point3D the point 3d
     * @throws IllegalArgumentException if the argument is zero
     */
    public Vector(Point3D point3D) {
        if (Point3D.ZERO.equals(point3D))
            throw new IllegalArgumentException("Vector cannot be ZERO");
        this._head = new Point3D(point3D);
    }

    /**
     * Copy constructor
     *
     * @param other the other
     */
    public Vector(Vector other) {
        this._head = new Point3D(other._head);
    }

    /******************* getters ****************/
    /**
     * get head
     *
     * @return Point3D head
     */
    public Point3D get_head() {
        return _head;
    }

    /************functions*************/
    /**
     * add vector to another using add method in Point3D class
     *
     * @param other the other
     * @return Vector vector
     */
    public Vector add(Vector other) {
        return new Vector(this._head.add(other));//go to ctor of point3d
    }

    /**
     * subtract vector from another using subtract method in Point3D class
     *
     * @param _vector the vector
     * @return vector
     */
    public Vector subtract(Vector _vector) {
        return new Vector(this._head.subtract(_vector._head));//go to ctor of vector
    }

    /**
     * Scale
     *
     * @param _scalar the scalar
     * @return Vector vector
     */
    public Vector scale(double _scalar) {
        return new Vector(this._head.get_x()._coord * _scalar,
                this._head.get_y()._coord * _scalar,
                this._head.get_z()._coord * _scalar);
    }

    /**
     * dotProduct
     *
     * @param _vec the vec
     * @return double
     */
    public double dotProduct(Vector _vec) {
        return (this._head.get_x()._coord * _vec._head.get_x()._coord) +
                (this._head.get_y()._coord * _vec._head.get_y()._coord) +
                (this._head.get_z()._coord * _vec._head.get_z()._coord);

    }

    /**
     * Cross product vector.
     *
     * @param _vector the vector
     * @return vector
     */
    public Vector crossProduct(Vector _vector) {
        return new Vector((new Point3D(
                this._head.get_y()._coord * _vector._head.get_z()._coord - this._head.get_z()._coord * _vector._head.get_y()._coord,
                this._head.get_z()._coord * _vector._head.get_x()._coord - this._head.get_x()._coord * _vector._head.get_z()._coord,
                this._head.get_x()._coord * _vector._head.get_y()._coord - this._head.get_y()._coord * _vector._head.get_x()._coord)));
    }

    /**
     * Length squared double.
     *
     * @return the double
     */
    public double lengthSquared(){
        return Point3D.ZERO.distanceSquared(this._head);
    }

    /**
     * Length double.
     *
     * @return the double
     */
    public double length(){
        return Point3D.ZERO.distance(this._head);
    }

    /**
     * Normalize vector return the same vector after normalization.
     *
     * @return the vector
     */
    public Vector normalize(){
         this._head=new Point3D(this._head.get_x()._coord/this.length(),
                 this._head.get_y()._coord/this.length(),
                 this._head.get_z()._coord/length());
         return this;
    }

    /**
     * Normalized vector create new vector.
     *
     * @return the vector
     */
    public Vector normalized(){
        Vector temp=new Vector(this._head);
        return temp.normalize();
    }

    /************override functions*************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _head.equals(vector._head);
    }

    @Override
    public String toString() {
        return "Vector: " +
                "_head=" + _head +
                ". ";
    }


}
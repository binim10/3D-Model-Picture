package primitives;

/**
 * The type Ray.
 */
public class Ray {
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
        if(Math.sqrt(direction._head.get_x()._coord*direction._head.get_x()._coord+
                direction._head.get_y()._coord*direction._head.get_y()._coord+
                direction._head.get_z()._coord*direction._head.get_z()._coord)!=1)
            throw new IllegalArgumentException("the vector is not normalized");
        this._POO = new Point3D(poo);
        this._direction = new Vector(direction);
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
        return this._direction;
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
       return _POO.equals(temp._POO)&&_direction.equals(temp._direction);
    }

}

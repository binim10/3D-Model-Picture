package primitives;

public class Ray {
    // Point of origin
    private Point3D _POO;

    // Ray direction
    private Vector _direction;

    // ***************** Constructors                        ********************** //
    public Ray() {
        this._POO = new Point3D();
        this._direction = new Vector(_direction);
    }

    public Ray(Ray ray) {
        this._POO = ray.getPOO();
        this._direction = ray.getDirection();
    }

    public Ray(Point3D poo, Vector direction) {
        this._POO = new Point3D(poo);
        this._direction = new Vector(direction);
        this._direction.normalize();
    }

    // ***************** Getters/Setters ********************** //

    public void setPOO(Point3D _POO) {
        this._POO = new Point3D(_POO);
    }

    public void setDirection(Vector _direction) {
        this._direction = new Vector(_direction);
    }

    public Vector getDirection() {
        return new Vector(_direction);
    }

    public Point3D getPOO() {
        return new Point3D(_POO);
    }

}

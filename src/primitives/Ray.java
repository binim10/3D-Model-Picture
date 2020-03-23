package primitives;

public class Ray {
    // Point of origin
    private Point3D _POO;

    // Ray direction
    private Vector _direction;


    public Ray(Point3D poo, Vector direction) {
        this._POO = new Point3D(poo);
        this._direction = new Vector(direction);
    }
    public Ray(Ray ray) {
        this._POO = new Point3D(ray.getPOO());
        this._direction = new Vector(ray.getDirection());
    }

    public Point3D getPOO() {
        return this._POO;
    }

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

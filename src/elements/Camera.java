package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The type Camera represent the rays/vectors the "camera" send to the view plane.
 */
public class Camera {
    private Point3D _p0;
    private Vector _vUp, _vTo, _vRight;

    /**
     * Instantiates a new Camera.
     *
     * @param p0  the p 0
     * @param vUp the v up
     * @param vTo the v to
     */
    public Camera(Point3D p0, Vector vTo, Vector vUp) {
        //check if they are vertical
        if (!isZero(vUp.dotProduct(vTo))) {
            throw new IllegalArgumentException("the vectors up and to are not vertical");
        }
        this._p0 = p0;
        this._vTo = vTo.normalized();
        this._vUp = vUp.normalized();
        //create the vRight vector by using crossProduct that give vertical vector for both.
        this._vRight = new Vector(vTo.crossProduct(vUp)).normalized();
    }

    /**
     * Gets p0 the location of the start point.
     *
     * @return the p 0
     */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * Gets v up.
     *
     * @return the v up
     */
    public Vector getVUp() {
        return _vUp;
    }

    /**
     * Gets v to.
     *
     * @return the v to
     */
    public Vector getVTo() {
        return _vTo;
    }

    /**
     * Gets v right.
     *
     * @return the v right
     */
    public Vector getVRight() {
        return _vRight;
    }

    /**
     * Construct ray through pixel ray.
     * find the ray starts from camera and end in the view plane point by given pixel.
     *
     * @param nX             the number of horizontal pixels
     * @param nY             the number of vertical pixels
     * @param j              the index j represent index of columns
     * @param i              the index i represent index of rows
     * @param screenDistance the screen distance
     * @param screenWidth    the screen width
     * @param screenHeight   the screen height
     * @return the ray
     */
    public Ray constructRayThroughPixel(int nX, int nY,
                                        int j, int i, double screenDistance,
                                        double screenWidth, double screenHeight) {

        Point3D Pc = _p0.add(_vTo.scale(screenDistance));//Pc = P0 + d∙Vto
        double Ry = screenHeight / nY;//Ry = h/Ny- size of pixel
        double Rx = screenWidth / nX;//Rx = w/Nx

        double yi = ((i - (nY / 2d)) * Ry + Ry / 2d);
        double xj = ((j - (nX / 2d)) * Rx + Rx / 2d);

        Point3D Pij = Pc;

        if (!isZero(xj)) {
            Pij = Pij.add(_vRight.scale(xj));
        }
        if (!isZero(yi)) {
            Pij = Pij.add(_vUp.scale(-yi));
        }

        Vector Vij = Pij.subtract(_p0);

        return new Ray(_p0, Vij.normalize());

    }

}

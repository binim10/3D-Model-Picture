package primitives;

/**
 * The type Material.
 */
public class Material {
    private double _kD, _kS, _kT, _kR;
    private int _nShininess;

    /**
     * Instantiates a new Material.
     *
     * @param kD         the k d
     * @param kS         the k s
     * @param nShininess the n shininess
     */
    public Material(double kD, double kS, int nShininess) {
        this(0, 0, kD, kS, nShininess);
    }

    public Material(double kt, double kr, double kD, double kS, int nShininess) {
        _kT = kt;
        _kR = kr;
        _kD = kD;
        _kS = kS;
        _nShininess = nShininess;
    }

    /**
     * Gets kd.
     *
     * @return the kd
     */
    public double getKD() {
        return _kD;
    }

    /**
     * Gets ks.
     *
     * @return the ks
     */
    public double getKS() {
        return _kS;
    }

    /**
     * Gets kt.
     *
     * @return the kt
     */
    public double getKT() {
        return _kT;
    }

    /**
     * Gets kr.
     *
     * @return the kr
     */
    public double getKR() {
        return _kR;
    }

    /**
     * Gets nShininess.
     *
     * @return the n shininess
     */
    public int getNShininess() {
        return _nShininess;
    }
}

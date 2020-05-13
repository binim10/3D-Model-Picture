package primitives;

/**
 * The type Material.
 */
public class Material {
    private double _kD, _kS;
    private int _nShininess;

    /**
     * Instantiates a new Material.
     *
     * @param kD         the k d
     * @param kS         the k s
     * @param nShininess the n shininess
     */
    public Material(double kD, double kS, int nShininess) {
        this._kD = kD;
        this._kS = kS;
        this._nShininess = nShininess;
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
     * Gets nShininess.
     *
     * @return the n shininess
     */
    public int getNShininess() {
        return _nShininess;
    }
}

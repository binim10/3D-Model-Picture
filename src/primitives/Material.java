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

    public double getKD() {
        return _kD;
    }

    public double getKS() {
        return _kS;
    }

    public int getNShininess() {
        return _nShininess;
    }
}

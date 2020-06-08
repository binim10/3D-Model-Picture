package primitives;

/**
 * The type Material.
 */
public class Material {
    private final double _kD;
    private final double _kS;
    private final double _kT;
    private final double _kR;
    private final int _nShininess;

    /**
     * Instantiates a new Material.
     *
     * @param kD         the mekadem of diffusive
     * @param kS         the mekadem of specular
     * @param nShininess the n shininess
     */
    public Material(double kD, double kS, int nShininess) {
        this(kD, kS, nShininess, 0, 0);
    }

    /**
     * Instantiates a new Material.
     *
     * @param kD         the mekadem of diffusive
     * @param kS         the mekadem of specular
     * @param nShininess the shininess
     * @param kr         the mekadem of refrected
     * @param kt         the mekadem of reflected
     */
    public Material(double kD, double kS, int nShininess, double kr, double kt) {
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

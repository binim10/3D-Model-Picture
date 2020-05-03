package elements;

import primitives.Color;

/**
 * The type Ambient light.
 */
public class AmbientLight {
    private Color _intensity;

    /**
     * Instantiates a new Ambient light.
     * calculate the intensity by scale the kA and iA.
     *
     * @param iA the a
     * @param kA the k a
     */
    public AmbientLight(Color iA,double kA) {
        _intensity=new Color(iA.scale(kA));
    }

    /**
     * Gets intensity.
     *
     * @return the intensity
     */
    public Color getIntensity() {
        return _intensity;
    }
}

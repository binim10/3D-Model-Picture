package elements;

import primitives.Color;

/**
 * The type Ambient light.
 */
public class AmbientLight extends Light {

    /**
     * Instantiates a new Ambient light.
     * calculate the intensity by scale the kA and iA.
     *
     * @param iA the a
     * @param kA the k a
     */
    public AmbientLight(Color iA, double kA) {
        super(new Color(iA.scale(kA)));
    }

    /**
     * Gets intensity.
     *
     * @return the intensity
     */

}

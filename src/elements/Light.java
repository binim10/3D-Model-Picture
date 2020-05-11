package elements;

import primitives.Color;

/**
 * The type Light.
 */
abstract class Light {
    protected Color _intensity;

    /**
     * Instantiates a new Light.
     *
     * @param intensity the intensity
     */
    public Light(Color intensity) {
        this._intensity = intensity;
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

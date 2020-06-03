package elements;

import primitives.Color;

/**
 * The type Light
 * abstract class extended by all of light sources.
 */
abstract class Light {
    protected Color _intensity;

    /**
     * Instantiates a new Light.
     *
     * @param intensity the intensity
     */
    public Light(Color intensity) {
        _intensity = intensity;
    }

    /**
     * Gets the intensity.
     *
     * @return the intensity
     */
    public Color getIntensity() {
        return _intensity;
    }

}

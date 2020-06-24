package scene;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Color;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Scene, represent the entire scene in an image.
 */
public class Scene {
    private final String _name;
    private Color _background;
    private AmbientLight _ambientLight;
    private final Geometries _geometries;
    private Camera _camera;
    private double _distance;
    List<LightSource> _lights = new LinkedList<>();

    /**
     * Instantiates a new Scene.
     *
     * @param _name the name
     */
    public Scene(String _name) {
        this._name = _name;
        _geometries = new Geometries();
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return _name;
    }

    /**
     * Gets background.
     *
     * @return the background
     */
    public Color getBackground() {
        return _background;
    }

    /**
     * Gets ambient light.
     *
     * @return the ambient light
     */
    public AmbientLight getAmbientLight() {
        return _ambientLight;
    }

    /**
     * Gets geometries.
     *
     * @return the geometries
     */
    public Geometries getGeometries() {
        return _geometries;
    }

    /**
     * Gets camera.
     *
     * @return the camera
     */
    public Camera getCamera() {
        return _camera;
    }

    /**
     * Gets distance.
     *
     * @return the distance
     */
    public double getDistance() {
        return _distance;
    }

    /**
     * Gets lights.
     *
     * @return the lights
     */
    public List<LightSource> getLights() {
        return _lights;
    }

    /**
     * Sets background.
     *
     * @param background the background
     */
    public void setBackground(Color background) {
        this._background = background;
    }

    /**
     * Sets ambient light.
     *
     * @param ambientLight the ambient light
     */
    public void setAmbientLight(AmbientLight ambientLight) {
        this._ambientLight = ambientLight;
    }

    /**
     * Sets camera.
     *
     * @param camera the camera
     */
    public void setCamera(Camera camera) {
        this._camera = camera;
    }

    /**
     * Sets distance.
     *
     * @param distance the distance
     */
    public void setDistance(double distance) {
        this._distance = distance;
    }

    /**
     * Add geometries to the list.
     *
     * @param geometries the geometries
     */
    public void addGeometries(Intersectable... geometries) {
        _geometries.add(geometries);
    }

    /**
     * Add lights.
     *
     * @param lights the lights
     */
    public void addLights(LightSource... lights) {
        _lights.addAll(Arrays.asList(lights));
    }


}

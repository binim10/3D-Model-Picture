package scene;

import elements.AmbientLight;
import elements.Camera;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Color;

public class Scene {
    private String _name;
    private Color _background;
    private AmbientLight _ambientLight;
    private Geometries _geometries;
    private Camera _camera;
    private double _distance;

    public Scene(String _name) {
        this._name = _name;
        _geometries=new Geometries();
    }

    public String getName() {
        return _name;
    }

    public Color getBackground() {
        return _background;
    }

    public AmbientLight getAmbientLight() {
        return _ambientLight;
    }

    public Geometries getGeometries() {
        return _geometries;
    }

    public Camera getCamera() {
        return _camera;
    }

    public double getDistance() {
        return _distance;
    }

    public void setBackground(Color background) {
        this._background = background;
    }

    public void setAmbientLight(AmbientLight ambientLight) {
        this._ambientLight = ambientLight;
    }

    public void setCamera(Camera camera) {
        this._camera = camera;
    }

    public void setDistance(double distance) {
        this._distance = distance;
    }

    void addGeometries(Intersectable... geometries) {
        _geometries.add(geometries);
    }


}

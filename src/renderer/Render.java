package renderer;

import elements.Camera;
import geometries.Intersectable;
import primitives.Point3D;
import scene.Scene;

import java.awt.*;
import java.util.List;

public class Render {
    private ImageWriter _imageWriter;
    private Scene _scene;
    public Render(ImageWriter imageWriter, Scene scene) {


    }

    public void renderImage() {
        Camera  camera = _scene.getCamera();
        Intersectable geometries = _scene.getGeometries();
        java.awt.Color background = _scene.getBackground().getColor();
        int nX = _imageWriter.getNx();
        int nY=_imageWriter.getNy();
        for (int row = 0; row < nY; row++) {
            for (int col = 0; col < nX; col++) {


            }
        }


    }
    public void calcColor(Point3D p){

    }
    public Point3D	getClosestPoint(List<Point3D> points){
        //TODO
        return null;
    }

    public void printGrid(int interval, java.awt.Color color){

    }
}

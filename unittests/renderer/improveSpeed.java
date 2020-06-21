package renderer;

import elements.AmbientLight;
import elements.Camera;
import elements.PointLight;
import elements.SpotLight;
import geometries.*;
import org.junit.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;

import static primitives.Util.getRandom;

public class improveSpeed {
    @Test
    public void magicalRoom() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -10000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        Geometries aRight = new Geometries(), bLeft = new Geometries(), c = new Geometries();
        for (int i = 1; i < 300; i++) {
            aRight.add(new Sphere(new Color(100 * 5 / i + 1, 100 * 3 / i + 1.5, 100 * 6 / i * 0.8), new Material(0.2, 0.2, 200, 0, getRandom(0.1, 0.8)), // )
                    50 , new Point3D(-600+i*10, -600+i*10, 10000)));
//                    new Sphere(new Color(800, 0, 0), new Material(0.5, 0.5, 200, 0.5, 0), // )
//                            140, new Point3D(260, 260, 500)),
//                    new Sphere(new Color(0, 0, 200), new Material(0.25, 0.25, 20, 0, 0.5), // )
//                            140, new Point3D(-260, 260, 0)),
//                    new Polygon(Color.BLACK, new Material(0.2, 0.2, 200, 0.9, 0),
//                            new Point3D(10, -150, 1999), new Point3D(10, 200, 1999), new Point3D(310, 200, 1999), new Point3D(310, -150, 1999))
//                            );
        }
        for (int i = 1; i < 300; i++) {
            bLeft.add(new Sphere(new Color(100 * 5 / i + 1, 100 * 3 / i + 1.5, 100 * 6 / i * 0.8), new Material(0.5, 0.5, 200, 0.5, 0), // )
                    50, new Point3D(300+i*5, 400+i*5, 10000)));
        }
        bLeft.add(new Triangle(new Color(getRandom(1, 255), getRandom(1, 250), getRandom(1, 255)), new Material(0.5, 0.5, 100, 0.5, 0.5),
                new Point3D(-100, 400, 150), new Point3D(100, 400, 350), new Point3D(0, 200, 250)));
        c.add(aRight, bLeft);
        scene.addGeometries(c);


        scene.addLights(new SpotLight(new Color(java.awt.Color.white), //
                        new Point3D(0, 0, -1500), 1, 4E-5, 2E-7, new Vector(0, 0, 1)).setRadius(10),
                new PointLight(new Color(java.awt.Color.white), new Point3D(0.001, -100, 499), 1, 4E-5, 2E-7).setRadius(9));

        ImageWriter imageWriter = new ImageWriter("The magical room with BVH 601Bodies", 200, 200, 1000, 1000);
        Render render = new Render(imageWriter, scene).setSuperSampling(200).setMultithreading(3).setDebugPrint();

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void magicalRoomNonBVH() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -10000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));


        scene.addGeometries(new Sphere(new Color(java.awt.Color.yellow), new Material(0.2, 0.2, 200, 0, 0.8), // )
                        80, new Point3D(200, 0, 500)),
                new Sphere(new Color(800, 0, 0), new Material(0.5, 0.5, 200, 0.5, 0), // )
                        140, new Point3D(260, 260, 500)),
                new Sphere(new Color(0, 0, 200), new Material(0.25, 0.25, 20, 0, 0.5), // )
                        140, new Point3D(-260, 260, 0)), new Sphere(new Color(700, 20, 20), new Material(0.5, 0.5, 200, 0.5, 0), // )
                        100, new Point3D(-300, 300, 1500)),
                new Triangle(new Color(100, 300, 100), new Material(0.5, 0.5, 100, 0.5, 0.5),
                        new Point3D(-100, 400, 150), new Point3D(100, 400, 350), new Point3D(0, 200, 250)),
                new Polygon(Color.BLACK, new Material(0.2, 0.2, 200, 0.9, 0),
                        new Point3D(10, -150, 1999), new Point3D(10, 200, 1999), new Point3D(310, 200, 1999), new Point3D(310, -150, 1999)));


        scene.addLights(new SpotLight(new Color(java.awt.Color.white), //
                        new Point3D(0, 0, -1500), 1, 4E-5, 2E-7, new Vector(0, 0, 1)).setRadius(10),
                new PointLight(new Color(java.awt.Color.white), new Point3D(0.001, -100, 499), 1, 4E-5, 2E-7).setRadius(9));

        ImageWriter imageWriter = new ImageWriter("The magical room without BVH", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene).setSuperSampling(200).setMultithreading(3).setDebugPrint();

        render.renderImage();
        render.writeToImage();
    }
}

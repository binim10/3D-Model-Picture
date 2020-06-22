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

        Geometries aRight = new Geometries(), aLeft = new Geometries(), bRight = new Geometries(), bLeft = new Geometries(), c = new Geometries(), a = new Geometries(), b = new Geometries();
        for (int i = 1; i < 100; i++) {
            aRight.add(new Sphere(new Color(getRandom(1, 180), getRandom(1, 400), getRandom(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5),
                    60, new Point3D(getRandom(10, 1000), getRandom(-1000, 10), 10000)));
        }
        for (int i = 1; i < 100; i++) {
            aLeft.add(new Sphere(new Color(getRandom(1, 180), getRandom(1, 400), getRandom(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5),
                    60, new Point3D(getRandom(-1000, 10), getRandom(-1000, 10), 10000)));
        }
        a.add(aRight, aLeft);
        for (int i = 1; i < 100; i++) {
            bLeft.add(new Sphere(new Color(getRandom(1, 180), getRandom(1, 400), getRandom(1, 300)), new Material(0.5, 0.5, 200, 0.5, 0), // )
                    60, new Point3D(getRandom(-1000, 10), getRandom(10, 1000), 10000)));
        }
        for (int i = 1; i < 100; i++) {
            bRight.add(new Sphere(new Color(getRandom(1, 300), getRandom(1, 300), getRandom(1, 500)), new Material(0.5, 0.5, 200, 0.5, 0), // )
                    60, new Point3D(getRandom(10, 1000), getRandom(10, 1000), 10000)));
        }
        bLeft.add(new Triangle(new Color(getRandom(1, 255), getRandom(1, 250), getRandom(1, 255)), new Material(0.5, 0.5, 100, 0.5, 0.5),
                new Point3D(-100, 400, 150), new Point3D(100, 400, 350), new Point3D(0, 200, 250)));
        b.add(bRight, bLeft);
        c.add(a, b);
        scene.addGeometries(c);


        scene.addLights(new SpotLight(new Color(java.awt.Color.white), //
                        new Point3D(0, 0, -1500), 1, 4E-5, 2E-7, new Vector(0, 0, 1)),
                new PointLight(new Color(java.awt.Color.white), new Point3D(0.001, -100, 499), 1, 4E-5, 2E-7));

        ImageWriter imageWriter = new ImageWriter("The magical room with BVH 601Bodies 3 no SS", 200, 200, 2000, 2000);
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

    @Test
    public void allRandom() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -10000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        Geometries aRight = new Geometries(), aLeft = new Geometries(), bRight = new Geometries(), bLeft = new Geometries(), c = new Geometries(), a = new Geometries(), b = new Geometries();
        for (int i = 1; i < 100; i++) {
            aRight.add(new Sphere(new Color(getRandom(1, 180), getRandom(1, 400), getRandom(1, 300)), new Material(getRandom(0.1, 0.5), getRandom(0.1, 0.5), 200, 0, getRandom(0.1, 0.9)),
                    60, new Point3D(getRandom(10, 1000), getRandom(-1000, 10), 10000)));
        }
        for (int i = 1; i < 100; i++) {
            aLeft.add(new Sphere(new Color(getRandom(1, 180), getRandom(1, 400), getRandom(1, 300)), new Material(getRandom(0.1, 0.5), getRandom(0.1, 0.5), 200, 0, getRandom(0.1, 0.9)),
                    60, new Point3D(getRandom(-1000, 10), getRandom(-1000, 10), 10000)));
        }
        a.add(aRight, aLeft);
        for (int i = 1; i < 100; i++) {
            bLeft.add(new Sphere(new Color(getRandom(1, 180), getRandom(1, 400), getRandom(1, 300)), new Material(getRandom(0.1, 0.5), getRandom(0.1, 0.5), 200, getRandom(0.1, 0.8), 0), // )
                    60, new Point3D(getRandom(-1000, 10), getRandom(10, 1000), 10000)));
        }
        for (int i = 1; i < 100; i++) {
            double x = getRandom(10, 1000), y = getRandom(10, 1000), z = getRandom(8000, 10000);
            bRight.add(new Sphere(new Color(getRandom(1, 300), getRandom(1, 300), getRandom(1, 500)), new Material(getRandom(0.1, 0.5), getRandom(0.1, 0.5), 200, 0, getRandom(0.1, 0.8)), // )
                            60, new Point3D(getRandom(10, 1000), getRandom(10, 1000), 10000)),
                    new Triangle(new Color(getRandom(1, 300), getRandom(1, 350), getRandom(1, 255)), new Material(getRandom(0.1, 0.5), getRandom(0.1, 0.5), 200, getRandom(0.1, 0.5), getRandom(0.1, 0.5)),
                            new Point3D(x, y, z), new Point3D(x + 200, y, z + 200), new Point3D(x + 100, y - 200, z + 100)));
        }
        b.add(bRight, bLeft);
        c.add(a, b);
        scene.addGeometries(c);


        scene.addLights(new SpotLight(new Color(java.awt.Color.white), //
                        new Point3D(0, 0, -1500), 1, 4E-5, 2E-7, new Vector(0, 0, 1)).setRadius(10),
                new PointLight(new Color(java.awt.Color.white), new Point3D(0.001, -100, 499), 1, 4E-5, 2E-7));

        ImageWriter imageWriter = new ImageWriter("The Random Spheres with BVH 500Bodies with SS", 200, 200, 2000, 2000);
        Render render = new Render(imageWriter, scene).setSuperSampling(200).setMultithreading(3).setDebugPrint();

        render.renderImage();
        render.writeToImage();
    }
}

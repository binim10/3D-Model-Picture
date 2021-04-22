package renderer;

import elements.*;
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
        for (int i = 1; i < 500; i++) {
            aRight.add(new Sphere(new Color(getRandom(1, 180), getRandom(1, 400), getRandom(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5),
                    30, new Point3D(getRandom(10, 3000), getRandom(-3000, 10), 10000)));
        }
        for (int i = 1; i < 500; i++) {
            aLeft.add(new Sphere(new Color(getRandom(1, 180), getRandom(1, 400), getRandom(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5),
                    30, new Point3D(getRandom(-3000, 10), getRandom(-3000, 10), 10000)));
        }
        a.add(aRight, aLeft);
        for (int i = 1; i < 500; i++) {
            bLeft.add(new Sphere(new Color(getRandom(1, 180), getRandom(1, 400), getRandom(1, 300)), new Material(0.5, 0.5, 200, 0.5, 0), // )
                    30, new Point3D(getRandom(-3000, 10), getRandom(10, 3000), 10000)));
        }
        for (int i = 1; i < 500; i++) {
            bRight.add(new Sphere(new Color(getRandom(1, 300), getRandom(1, 300), getRandom(1, 500)), new Material(0.5, 0.5, 200, 0.5, 0), // )
                    30, new Point3D(getRandom(10, 3000), getRandom(10, 3000), 10000)));
        }
        bLeft.add(new Triangle(new Color(getRandom(1, 255), getRandom(1, 250), getRandom(1, 255)), new Material(0.5, 0.5, 100, 0.5, 0.5),
                new Point3D(-100, 400, 150), new Point3D(100, 400, 350), new Point3D(0, 200, 250)));
        b.add(bRight, bLeft);
        c.add(a, b);
        scene.addGeometries(c);


        scene.addLights(new SpotLight(new Color(java.awt.Color.white), //
                        new Point3D(0, 0, -1500), 1, 4E-5, 2E-7, new Vector(0, 0, 1)).setRadius(10),
                new PointLight(new Color(java.awt.Color.white), new Point3D(0.001, -100, 499), 1, 4E-5, 2E-7));

        ImageWriter imageWriter = new ImageWriter("BVH 2001Bodies 3 with SS 200 big pic", 400, 400, 1000, 1000);
        Render render = new Render(imageWriter, scene).setSuperSampling(200).setMultithreading(3).setDebugPrint().setBVHImprove(true);

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

    @Test
    public void newTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -5000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        int j = 50;
        int n = 0;
        Geometries aRight = new Geometries(), aLeft = new Geometries(), bRight = new Geometries(), bLeft = new Geometries(), c = new Geometries(), a = new Geometries(), b = new Geometries();
        for (int i = 1; i < 500; i++) {
            aRight.add(new Sphere(new Color(getRandom(1, 180), getRandom(1, 400), getRandom(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5),
                    50, new Point3D(1, n -= 30, j += 200)));
        }
        j = 50;
        n = 0;
        for (int i = 1; i < 500; i++) {
            aLeft.add(new Sphere(new Color(getRandom(1, 180), getRandom(1, 400), getRandom(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5),
                    50, new Point3D(1, n += 30, j += 200)));
        }
        a.add(aRight, aLeft);
        j = 50;
        n = 0;
        for (int i = 1; i < 500; i++) {
            bLeft.add(new Sphere(new Color(getRandom(1, 180), getRandom(1, 400), getRandom(1, 300)), new Material(0.5, 0.5, 200, 0.5, 0), // )
                    50, new Point3D(n -= 30, 1, j += 200)));
        }
        j = 50;
        n = 0;
        for (int i = 1; i < 500; i++) {
            bRight.add(new Sphere(new Color(getRandom(1, 300), getRandom(1, 300), getRandom(1, 500)), new Material(0.5, 0.5, 200, 0.5, 0), // )
                    50, new Point3D(n += 30, 1, j += 200)));
        }
//        bLeft.add(new Triangle(new Color(getRandom(1, 255), getRandom(1, 250), getRandom(1, 255)), new Material(0.5, 0.5, 100, 0.5, 0.5),
//                new Point3D(-100, 400, 150), new Point3D(100, 400, 350), new Point3D(0, 200, 250)));
        b.add(bRight, bLeft);
        c.add(a, b);
        scene.addGeometries(c);


        scene.addLights(new SpotLight(new Color(java.awt.Color.white), //
                        new Point3D(0, 0, -1500), 1, 4E-5, 2E-7, new Vector(0, 0, 1)).setRadius(10),
                new PointLight(new Color(java.awt.Color.white), new Point3D(0.001, -100, 499), 1, 4E-5, 2E-7));

        ImageWriter imageWriter = new ImageWriter("no BVH 2001Bodies 5 with SS 200 like plus", 400, 400, 1000, 1000);
        Render render = new Render(imageWriter, scene).setSuperSampling(200).setMultithreading(3).setDebugPrint().setBVHImprove(false);

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void newTest2() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -5000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        int j = 50;
        int n = 0;
        Geometries cRight = new Geometries(), cLeft = new Geometries(), cUp = new Geometries(), cDown = new Geometries(),
                aRight = new Geometries(), aLeft = new Geometries(), bRight = new Geometries(), bLeft = new Geometries(),
                e = new Geometries(), f = new Geometries(), g = new Geometries(), d = new Geometries(), c = new Geometries(), a = new Geometries(), b = new Geometries();
        for (int i = 1; i < 500; i++) {
            cUp.add(new Sphere(new Color(getRandom(1, 180), getRandom(1, 400), getRandom(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5),
                    50, new Point3D(1, n -= 30, j += 200)));
        }
        j = 50;
        n = 0;
        for (int i = 1; i < 500; i++) {
            cDown.add(new Sphere(new Color(getRandom(1, 180), getRandom(1, 400), getRandom(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5),
                    50, new Point3D(1, n += 30, j += 200)));
        }

        j = 50;
        n = 0;
        for (int i = 1; i < 500; i++) {
            cLeft.add(new Sphere(new Color(getRandom(1, 180), getRandom(1, 400), getRandom(1, 300)), new Material(0.5, 0.5, 200, 0.5, 0), // )
                    50, new Point3D(n -= 30, 1, j += 200)));
        }
        j = 50;
        n = 0;
        for (int i = 1; i < 500; i++) {
            cRight.add(new Sphere(new Color(getRandom(1, 300), getRandom(1, 300), getRandom(1, 500)), new Material(0.5, 0.5, 200, 0.5, 0), // )
                    50, new Point3D(n += 30, 1, j += 200)));
        }
        j = 50;
        n = 0;
        for (int i = 1; i < 500; i++) {
            aRight.add(new Sphere(new Color(getRandom(1, 180), getRandom(1, 400), getRandom(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5),
                    50, new Point3D(n += 30, n -= 30, j += 200)));
        }
        j = 50;
        n = 0;
        for (int i = 1; i < 500; i++) {
            aLeft.add(new Sphere(new Color(getRandom(1, 180), getRandom(1, 400), getRandom(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5),
                    50, new Point3D(n -= 30, n -= 30, j += 200)));
        }
        j = 50;
        n = 0;
        for (int i = 1; i < 500; i++) {
            bRight.add(new Sphere(new Color(getRandom(1, 180), getRandom(1, 400), getRandom(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5),
                    50, new Point3D(n += 30, n += 30, j += 200)));
        }
        j = 50;
        n = 0;
        for (int i = 1; i < 500; i++) {
            bLeft.add(new Sphere(new Color(getRandom(1, 180), getRandom(1, 400), getRandom(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5),
                    50, new Point3D(n -= 30, n += 30, j += 200)));
        }
        a.add(cUp, aRight);
        b.add(aLeft, cLeft);
        c.add(a, b);
        d.add(cRight, bRight);
        e.add(cDown, bLeft);
        f.add(d, e);
        g.add(c, f);
        scene.addGeometries(g);

        scene.addLights(new SpotLight(new Color(java.awt.Color.white), //
                        new Point3D(0, 0, -1500), 1, 4E-5, 2E-7, new Vector(0, 0, 1)).setRadius(10),
                new PointLight(new Color(java.awt.Color.white), new Point3D(0.001, -100, 499), 1, 4E-5, 2E-7));

        ImageWriter imageWriter = new ImageWriter("BVH 4000Bodies with SS 200 like star", 400, 400, 500, 500);
        Render render = new Render(imageWriter, scene).setSuperSampling(100).setMultithreading(3).setDebugPrint().setBVHImprove(false);

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void newTest2Triangle() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -5000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        int j = 50;
        int n1 = 0;
        int n2;

        Geometries cRight = new Geometries(), cLeft = new Geometries(), cUp = new Geometries(), cDown = new Geometries(),
                aRight = new Geometries(), aLeft = new Geometries(), bRight = new Geometries(), bLeft = new Geometries(),
                e = new Geometries(), f = new Geometries(), g = new Geometries(), d = new Geometries(), c = new Geometries(), a = new Geometries(), b = new Geometries();
        //        bLeft.add(new Triangle(new Color(getRandom(1, 255), getRandom(1, 250), getRandom(1, 255)), new Material(0.5, 0.5, 100, 0.5, 0.5),
//                new Point3D(-100, 400, 150), new Point3D(100, 400, 350), new Point3D(0, 200, 250)));
        for (int i = 1; i < 500; i++) {
            cUp.add(new Triangle(new Color(getRandom(1, 180), getRandom(1, 400), getRandom(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5)
                    , new Point3D(-50, n1 -= 30, j += 200), new Point3D(50, n1, j), new Point3D(0, n1 - 100, j)));
        }
        j = 50;
        n1 = 0;

        for (int i = 1; i < 500; i++) {
            cDown.add(new Triangle(new Color(getRandom(1, 180), getRandom(1, 400), getRandom(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5)
                    , new Point3D(-50, n1 += 30, j += 200), new Point3D(50, n1, j), new Point3D(0, n1 + 100, j)));
        }

        j = 50;
        n1 = 0;

        for (int i = 1; i < 500; i++) {
            cLeft.add(new Triangle(new Color(getRandom(1, 180), getRandom(1, 400), getRandom(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5)
                    , new Point3D(n1 -= 50, 50, j += 200), new Point3D(n1 + 100, 50, j), new Point3D(n1 + 50, -50, j)));
        }
        j = 50;
        n1 = 0;

        for (int i = 1; i < 500; i++) {
            cRight.add(new Triangle(new Color(getRandom(1, 180), getRandom(1, 400), getRandom(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5)
                    , new Point3D(n1 += 50, 50, j += 200), new Point3D(n1 + 100, 50, j), new Point3D(n1 + 50, -50, j)));
        }
        j = 50;
        n1 = 0;
        n2 = 0;

        for (int i = 1; i < 500; i++) {
            aRight.add(new Triangle(new Color(getRandom(1, 180), getRandom(1, 400), getRandom(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5)
                    , new Point3D(n1 += 50, n2 -= 50, j += 200), new Point3D(n1 + 100, n2, j), new Point3D(n1 + 50, n2 - 100, j)));
        }
        j = 50;
        n1 = 0;
        n2 = 0;

        for (int i = 1; i < 500; i++) {
            aLeft.add(new Triangle(new Color(getRandom(1, 180), getRandom(1, 400), getRandom(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5)
                    , new Point3D(n1 -= 50, n2 -= 50, j += 200), new Point3D(n1 + 100, n2, j), new Point3D(n1 + 50, n2 - 100, j)));
        }
        j = 50;
        n1 = 0;
        n2 = 0;

        for (int i = 1; i < 500; i++) {
            bRight.add(new Triangle(new Color(getRandom(1, 180), getRandom(1, 400), getRandom(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5)
                    , new Point3D(n1 += 50, n2 += 50, j += 200), new Point3D(n1 + 100, n2, j), new Point3D(n1 + 50, n2 - 100, j)));
        }
        j = 50;
        n1 = 0;
        n2 = 0;

        for (int i = 1; i < 500; i++) {
            bLeft.add(new Triangle(new Color(getRandom(1, 180), getRandom(1, 400), getRandom(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5)
                    , new Point3D(n1 -= 50, n2 += 50, j += 200), new Point3D(n1 + 100, n2, j), new Point3D(n1 + 50, n2 - 100, j)));
        }
        a.add(cUp, aRight);
        b.add(aLeft, cLeft);
        c.add(a, b);
        d.add(cRight, bRight);
        e.add(cDown, bLeft);
        f.add(d, e);
        g.add(c, f);
        scene.addGeometries(g);

        scene.addLights(new SpotLight(new Color(java.awt.Color.white), //
                        new Point3D(0, 0, -1500), 1, 4E-5, 2E-7, new Vector(0, 0, 1)).setRadius(10),
                new PointLight(new Color(java.awt.Color.white), new Point3D(0.001, -100, 499), 1, 4E-5, 2E-7));

        ImageWriter imageWriter = new ImageWriter("no BVH 4000Triangles with SS 200 like star", 400, 400, 500, 500);
        Render render = new Render(imageWriter, scene).setSuperSampling(100).setDebugPrint().setBVHImprove(false);

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void improvePerformance() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        Geometries
                a = new Geometries(),
                b = new Geometries(),
                c = new Geometries(),
                d = new Geometries();


        scene.addGeometries(
                //the sea
                new Plane(new Material(0.5, 0.4, 100, 0.001, 0), new Color(java.awt.Color.BLUE), //
                        new Point3D(-150, 200, 115), new Vector(0, 1, 0)),
                new Sphere(new Color(java.awt.Color.yellow), new Material(0.1, 0, 200, 0.1, 0), // )
                        3000, new Point3D(12900, -12900, 100000)),
                new Sphere(new Color(java.awt.Color.BLACK), new Material(0, 0, 100, 0, 1), // )
                        50, new Point3D(60, -50, -10000)),
                //the sky
                new Plane(new Material(0.2, 0.2, 70, 0, 0.5), new Color(100, 100, 500), //
                        new Point3D(0, 0, 100000), new Vector(0, 0, 1)),
                //the right shift
                new Triangle(new Color(java.awt.Color.LIGHT_GRAY), new Material(0, 0.2, 60), new Point3D(0, 125, 1200),
                        new Point3D(0, -100, 1200), new Point3D(90, 125, 1210)),
                new Triangle(new Color(java.awt.Color.LIGHT_GRAY), new Material(0, 0.2, 60), new Point3D(0, 125, 1200),
                        new Point3D(0, -100, 1200), new Point3D(-90, 125, 1210)),
                new Polygon(new Color(java.awt.Color.DARK_GRAY), new Material(0, 0.5, 60), new Point3D(-150, 125, 1200),
                        new Point3D(-105, 200, 1200), new Point3D(105, 200, 1200), new Point3D(150, 125, 1200)),
                //the left shift
                new Triangle(new Color(java.awt.Color.LIGHT_GRAY), new Material(0, 0.2, 60), new Point3D(-300, 125, 2400),
                        new Point3D(-300, -100, 2400), new Point3D(-210, 125, 2420)),
                new Triangle(new Color(java.awt.Color.LIGHT_GRAY), new Material(0, 0.2, 60), new Point3D(-300, 125, 2400),
                        new Point3D(-300, -100, 2400), new Point3D(-390, 125, 2420)),
                new Polygon(new Color(java.awt.Color.DARK_GRAY), new Material(0, 0.5, 60), new Point3D(-450, 125, 2400),
                        new Point3D(-405, 200, 2400), new Point3D(-195, 200, 2400), new Point3D(-150, 125, 2400)),
                //the clouds
                new Sphere(new Color(java.awt.Color.WHITE), new Material(0.5, 0.5, 200, 0, 0.2), // )
                        500, new Point3D(-1900, -1900, 15000)),
                new Sphere(new Color(java.awt.Color.WHITE), new Material(0.5, 0.5, 200, 0, 0.2), // )
                        500, new Point3D(-1300, -2200, 15000)),
                new Sphere(new Color(java.awt.Color.WHITE), new Material(0.5, 0.5, 200, 0, 0.2), // )
                        500, new Point3D(-300, -1900, 15000)),
                new Sphere(new Color(java.awt.Color.WHITE), new Material(0.5, 0.5, 200, 0, 0.2), // )
                        500, new Point3D(-1250, -1500, 15000)),
                new Sphere(new Color(java.awt.Color.WHITE), new Material(0.5, 0.5, 200, 0, 0.2), // )
                        500, new Point3D(-700, -2200, 15000)),
                new Sphere(new Color(java.awt.Color.WHITE), new Material(0.5, 0.5, 200, 0, 0.2), // )
                        500, new Point3D(-750, -1500, 15000)),
                //the flag
                new Polygon(new Color(java.awt.Color.WHITE), new Material(0.5, 0, 60),
                        new Point3D(100, -10, 500), new Point3D(100, 300, 500),
                        new Point3D(102, 300, 500), new Point3D(102, -10, 500)),
                new Triangle(new Color(java.awt.Color.RED), new Material(0, 0, 60),
                        new Point3D(102, -10, 500), new Point3D(135, 0, 500),
                        new Point3D(102, 10, 500)),
                //sand
                new Sphere(new Color(226, 158, 0), new Material(0.01, 0, 100),
                        130, new Point3D(0, 280, 0)),
                new Sphere(new Color(226, 158, 0), new Material(0.01, 0, 100),
                        130, new Point3D(95, 282, 0)),
                new Sphere(new Color(226, 158, 0), new Material(0.01, 0, 100),
                        130, new Point3D(-100, 275, 0)),
                new Sphere(new Color(226, 158, 0), new Material(0.01, 0, 100),
                        130, new Point3D(-190, 260, 0)),
                new Sphere(new Color(226, 158, 0), new Material(0.01, 0, 100),
                        130, new Point3D(185, 270, 0)),
                //matkot
                new Polygon(new Color(java.awt.Color.BLACK), new Material(0.1, 0, 60),
                        new Point3D(-100, 150, -10), new Point3D(-98, 152, -10),
                        new Point3D(-88, 142, -10), new Point3D(-90, 140, -10)),
                new Polygon(new Color(java.awt.Color.BLACK), new Material(0.1, 0, 60),
                        new Point3D(-90, 140, -10), new Point3D(-88, 142, -10)
                        , new Point3D(-86, 142, -10), new Point3D(-90, 138, -10)),
                new Sphere(new Color(108, 52, 0), new Material(0, 0, 60),
                        13, new Point3D(-79.5, 131, -10)),
                //ball
                new Sphere(Color.BLACK, new Material(0.01, 0.2, 60),
                        3, new Point3D(-105, 143, -15)));
        //********************Waves*****************************
        double j = 3000, n = 200, n1 = 20000, n2, n3 = 10, n4 = 2, n6 = 15, n7 = -3000, count = 1;
        for (int i = 1; i < 10000; i++) {
            if (j <= n7) {
                j = 3000;
                j -= 20 * count++;
                n7 += 20;
                n1 -= 6000 / n4;
                n4 += 1;
                j = j - n6;
                n6 *= -1;
            }
            n2 = n1 - n3;
            scene.addGeometries(new Triangle(new Color(java.awt.Color.BLUE), new Material(0, 0.8, 300, 0.5, 0.3),
                    new Point3D(j -= 45, n, n2), new Point3D(j + 12, n - 5, n2),
                    new Point3D(j + 24, n, n2)));
            n3 *= -1;

        }
        scene.addLights(
                new SpotLight(new Color(400, 400, 700), //
                        new Point3D(100, -100, -1000), 1, 4E-5, 2E-7, new Vector(0, 0, 1)).setRadius(15),
                new DirectionalLight(new Color(java.awt.Color.yellow), //
                        new Vector(-0.2, 1, -1)),
                new PointLight(new Color(java.awt.Color.yellow), new Point3D(12900, -12900, 100000), 1, 4E-5, 2E-7));

        ImageWriter imageWriter = new ImageWriter("the Beach", 400, 400, 1500, 1500);
        Render render = new Render(imageWriter, scene).setSuperSampling(100).setBVHImprove(false).setDebugPrint().setMultithreading(1);
        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void magicalRoomBVH() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -10000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                //right wall
//                new Plane(new Material(0.2, 0.2, 60, 0.2, 0.1), Color.BLACK,
//                        new Point3D(400, 0, 100), new Vector(-1, 0, 0)),
                //floor
                new Polygon(Color.BLACK, new Material(0.2, 0.3, 200, 0.4, 0),
                        new Point3D(-10000, 400, 10000), new Point3D(10000, 400, 10000),
                        new Point3D(10000, 400, -10000), new Point3D(-10000, 400, -10000)),
//                //left wall
//                new Plane(new Material(0.2, 0.2, 60, 0.2, 0.1), Color.BLACK,
//                        new Point3D(-400, 0, 100), new Vector(1, 0, 0)),
                new Plane(new Material(0.2, 0.8, 200, 0.2, 0), Color.BLACK,
                        new Point3D(0, -300, 500), new Vector(0, 1, 0)),
                new Polygon(Color.BLACK, new Material(0.2, 0.2, 200, 0.5, 0),
                        new Point3D(-1, -300, 500), new Point3D(-1, -140, 500), new Point3D(1, -140, 500), new Point3D(1, -300, 500)),
                new Sphere(new Color(java.awt.Color.yellow), new Material(0.2, 0.2, 200, 0, 0.8), // )
                        80, new Point3D(-1, -120, 500)),
                new Polygon(Color.BLACK, new Material(0.2, 0.2, 200, 0.9, 0),
                        new Point3D(-150, -150, 1999), new Point3D(-150, 200, 1999), new Point3D(150, 200, 1999), new Point3D(150, -150, 1999)),
                new Sphere(new Color(800, 0, 0), new Material(0.5, 0.5, 200, 0.5, 0), // )
                        140, new Point3D(260, 260, 500)),
                new Sphere(new Color(0, 0, 200), new Material(0.25, 0.25, 20, 0, 0.5), // )
                        140, new Point3D(-260, 260, 0)),
                new Sphere(new Color(700, 20, 20), new Material(0.5, 0.5, 200, 0.5, 0), // )
                        100, new Point3D(-300, 300, 1500))/*,
                new Triangle(new Color(100, 300, 100), new Material(0.5, 0.5, 100, 0.5, 0.5),
                        new Point3D(-100, 400, 150), new Point3D(100, 400, 350), new Point3D(0, 200, 250))*/);
        int n1 = -1000, n2;
        for (int i = 1; i < 10000; i++) {
            n1 += 1;
            n2 = n1 * -1;
            scene.addGeometries(new Sphere(new Color(getRandom(1, 255), getRandom(1, 255), getRandom(1, 255)), new Material(0.5,
                    0.5, 300, 0.5, 0), getRandom(10, 20),
                    new Point3D(getRandom(-2000, 2000), -300, n2)));
        }

        scene.addLights(new SpotLight(new Color(java.awt.Color.white), //
                        new Point3D(0, 0, -1500), 1, 4E-5, 2E-7, new Vector(0, 0, 1)).setRadius(10),
                new PointLight(new Color(java.awt.Color.white), new Point3D(0.001, -100, 499), 1, 4E-5, 2E-7).setRadius(10));

        ImageWriter imageWriter = new ImageWriter("The magical room with BVH algo", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene).setMultithreading(3).setDebugPrint().setBVHImprove(true).setSuperSampling(100);
        render.renderImage();
        render.writeToImage();
    }

}

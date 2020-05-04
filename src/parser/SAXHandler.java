package parser;

import elements.AmbientLight;
import elements.Camera;
import geometries.Geometries;
import geometries.Sphere;
import geometries.Triangle;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

/**
 * The type Sax handler.
 * extends the default handler of SAX.
 */
public class SAXHandler extends DefaultHandler {


    private Scene scene = null;
    private ImageWriter image = null;
    private Camera camera = null;
    private Geometries geometriesList = new Geometries();
    private Double d = null;

    //boolean ver for start/ end elements
    boolean bScene = false;
    boolean bAmbientLight = false;
    boolean bGeometries = false;
    boolean bSphere = false;
    boolean bTriangle = false;
    boolean bPlane = false;
    boolean bTube = false;
    boolean bCamera = false;
    boolean bImage = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //if an element has attribute whats the length
        int attributeLength = attributes.getLength();
        if (qName.equalsIgnoreCase("scene")) {
            String name = attributes.getValue("name");
            scene = new Scene(name);
            //array of string to store the coordinates splited
            String[] rgb;
            for (int i = 0; i < attributeLength; i++) {
                if (attributes.getQName(i).equalsIgnoreCase("background-color")) {
                    rgb = attributes.getValue("background-color").split(" ");
                    scene.setBackground(new Color(d = Double.parseDouble(rgb[0]), d = Double.parseDouble(rgb[1]), d = Double.parseDouble(rgb[2])));
                } else if (attributes.getQName(i).equalsIgnoreCase("screen-distance")) {
                    scene.setDistance(d = Double.parseDouble(attributes.getValue("screen-distance")));
                }
            }
            bScene = true;
        } else if (qName.equalsIgnoreCase("ambient-light")) {
            String[] rgb = attributes.getValue("color").split(" ");
            scene.setAmbientLight(new AmbientLight(new Color(d = Double.parseDouble(rgb[0]), d = Double.parseDouble(rgb[1]), d = Double.parseDouble(rgb[2])), 1));
            bAmbientLight = true;
        } else if (qName.equalsIgnoreCase("geometries")) {
            bGeometries = true;
        } else if (qName.equalsIgnoreCase("camera")) {
            String[] point = new String[3];
            String[] vUp = new String[3];
            String[] vTo = new String[3];
            for (int i = 0; i < attributeLength; i++) {
                if (attributes.getQName(i).equalsIgnoreCase("P0")) {
                    point = attributes.getValue(i).split(" ");
                } else if (attributes.getQName(i).equalsIgnoreCase("Vto")) {
                    vTo = attributes.getValue(i).split(" ");
                } else if (attributes.getQName(i).equalsIgnoreCase("Vup")) {
                    vUp = attributes.getValue(i).split(" ");
                }
            }
            camera = new Camera(new Point3D(d = Double.parseDouble(point[0]), d = Double.parseDouble(point[1]), d = Double.parseDouble(point[2])),
                    new Vector(d = Double.parseDouble(vTo[0]), d = Double.parseDouble(vTo[1]), d = Double.parseDouble(vTo[2])),
                    new Vector(d = Double.parseDouble(vUp[0]), d = Double.parseDouble(vUp[1]), d = Double.parseDouble(vUp[2])));
            bCamera = true;
        } else if (qName.equalsIgnoreCase("image")) {
            String name = attributes.getValue("name");
            if(name==null)
                name="Stam";
            double imageWidth = 0;
            double imageHeight = 0;
            int nX = 0;
            int nY = 0;
            for (int i = 0; i < attributeLength; i++) {
                if (attributes.getQName(i).equalsIgnoreCase("screen-width")) {
                    imageWidth = Double.parseDouble(attributes.getValue("screen-width"));
                } else if (attributes.getQName(i).equalsIgnoreCase("screen-height")) {
                    imageHeight = Double.parseDouble(attributes.getValue("screen-height"));
                } else if (attributes.getQName(i).equalsIgnoreCase("Nx")) {
                    nX = Integer.parseInt(attributes.getValue("Nx"));
                } else if (attributes.getQName(i).equalsIgnoreCase("Ny")) {
                    nY = Integer.parseInt(attributes.getValue("Ny"));
                }
            }
            image = new ImageWriter(name, imageWidth, imageHeight, nX, nY);
            bImage = true;
        } else if (qName.equalsIgnoreCase("triangle")) {
            String[] p0 = new String[3];
            String[] p1 = new String[3];
            String[] p2 = new String[3];
            for (int i = 0; i < attributeLength; i++) {
                if (attributes.getQName(i).equalsIgnoreCase("p0")) {
                    p0 = attributes.getValue(i).split(" ");
                } else if (attributes.getQName(i).equalsIgnoreCase("p1")) {
                    p1 = attributes.getValue(i).split(" ");
                } else if (attributes.getQName(i).equalsIgnoreCase("p2")) {
                    p2 = attributes.getValue(i).split(" ");
                }
            }
            geometriesList.add(new Triangle(new Point3D(d = Double.parseDouble(p0[0]), d = Double.parseDouble(p0[1]), d = Double.parseDouble(p0[2])),
                    new Point3D(d = Double.parseDouble(p1[0]), d = Double.parseDouble(p1[1]), d = Double.parseDouble(p1[2])),
                    new Point3D(d = Double.parseDouble(p2[0]), d = Double.parseDouble(p2[1]), d = Double.parseDouble(p2[2]))));
            bTriangle = true;
        } else if (qName.equalsIgnoreCase("sphere")) {
            String[] center = new String[3];
            double rad = 0;
            for (int i = 0; i < attributeLength; i++) {
                if (attributes.getQName(i).equalsIgnoreCase("center")) {
                    center = attributes.getValue(i).split(" ");
                } else if (attributes.getQName(i).equalsIgnoreCase("radius")) {
                    rad = Double.parseDouble(attributes.getValue(i));
                }
            }
            Point3D cePoint = new Point3D(d = Double.parseDouble(center[0]), d = Double.parseDouble(center[1]), d = Double.parseDouble(center[2]));
            geometriesList.add(new Sphere(rad, cePoint));
            bSphere = true;
        } else if (qName.equalsIgnoreCase("plane")) {
            //TODO parser for plane

            bPlane = true;
        } else if (qName.equalsIgnoreCase("tube")) {
            //TODO parser for tube
            bTube = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("scene")) {
            Render render = new Render(image, scene);
            render.renderImage();
            //grid from the test render
            render.printGrid(50, java.awt.Color.YELLOW);
            render.writeToImage();
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if (bScene) {
            scene.addGeometries(geometriesList);
            bScene = false;
        }
        if (bImage) {
            bImage = false;
        }
        if (bAmbientLight) {
            bAmbientLight = false;
        }
        if (bCamera) {
            scene.setCamera(camera);
            bCamera = false;
        }
        if (bGeometries) {
            bGeometries = false;
        }
    }
}


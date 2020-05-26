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

import java.util.*;

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
    boolean bLights;

    public Scene getScene(){
        return scene;
    }
    public ImageWriter getImage(){
        return image;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //if an element has attribute whats the length
        int attributeLength = attributes.getLength();
        if (qName.equalsIgnoreCase("scene")) {
            String name = attributes.getValue("name");
            scene = new Scene(name);
            for (int i = 0; i < attributeLength; i++) {
                if (attributes.getQName(i).equalsIgnoreCase("background-color")) {
                    //List of Doubles to store the coordinates
                    List<Double> listOfD=getDoubleList(attributes.getValue("background-color"));
                    scene.setBackground(new Color(listOfD.get(0),listOfD.get(1),listOfD.get(2)));
                } else if (attributes.getQName(i).equalsIgnoreCase("screen-distance")) {
                    scene.setDistance(d = Double.parseDouble(attributes.getValue("screen-distance")));
                }
            }
            bScene = true;
        } else if (qName.equalsIgnoreCase("ambient-light")) {
            List<Double> listOfD=getDoubleList(attributes.getValue("color"));
            scene.setAmbientLight(new AmbientLight(new Color(listOfD.get(0),listOfD.get(1),listOfD.get(2)), 1));
            bAmbientLight = true;
        } else if (qName.equalsIgnoreCase("geometries")) {
            bGeometries = true;
        } else if (qName.equalsIgnoreCase("camera")) {
            List<Double> listOfDP0=getDoubleList(attributes.getValue("P0"));
            List<Double> listOfVup=getDoubleList(attributes.getValue("Vup"));
            List<Double> listOfVto=getDoubleList(attributes.getValue("Vto"));
            Point3D P0=null;
            Vector Vup=null;
            Vector Vto=null;
            for (int i = 0; i < attributeLength; i++) {
                if (attributes.getQName(i).equalsIgnoreCase("P0")) {
                    P0=new Point3D(listOfDP0.get(0),listOfDP0.get(1),listOfDP0.get(2));
                } else if (attributes.getQName(i).equalsIgnoreCase("Vto")) {
                    Vto =new Vector(listOfVto.get(0),listOfVto.get(1),listOfVto.get(2));
                } else if (attributes.getQName(i).equalsIgnoreCase("Vup")) {
                    Vup =new Vector(listOfVup.get(0),listOfVup.get(1),listOfVup.get(2));
                }
            }
            assert Vto != null;
            assert P0 != null;
            assert Vup != null;
            camera = new Camera(new Point3D(P0), new Vector(Vto), new Vector(Vup));
            bCamera = true;
        } else if (qName.equalsIgnoreCase("image")) {
            String name = attributes.getValue("name");
            if (name == null)
                name = "Stam";
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
            Point3D p0 =null;
            Point3D p1 =null;
            Point3D p2 = null;
            for (int i = 0; i < attributeLength; i++) {
                if (attributes.getQName(i).equalsIgnoreCase("p0")) {
                    List<Double> listOfDP0=getDoubleList(attributes.getValue("p0"));
                    p0 = new Point3D(listOfDP0.get(0),listOfDP0.get(1),listOfDP0.get(2));
                } else if (attributes.getQName(i).equalsIgnoreCase("p1")) {
                    List<Double> listOfDP1=getDoubleList(attributes.getValue("p1"));
                    p1 =new Point3D(listOfDP1.get(0),listOfDP1.get(1),listOfDP1.get(2));
                } else if (attributes.getQName(i).equalsIgnoreCase("p2")) {
                    List<Double> listOfDP2=getDoubleList(attributes.getValue("p2"));
                    p2 = new Point3D(listOfDP2.get(0),listOfDP2.get(1),listOfDP2.get(2));
                }
            }
            //points cannot be null
            assert p0 != null;
            assert p1 != null;
            assert p2 != null;
            geometriesList.add(new Triangle(new Point3D(p0), new Point3D(p1), new Point3D(p2)));
            bTriangle = true;
        } else if (qName.equalsIgnoreCase("sphere")) {
            Point3D center=null;
            double rad = 0;
            for (int i = 0; i < attributeLength; i++) {
                if (attributes.getQName(i).equalsIgnoreCase("center")) {
                    List<Double> listOfDCenter=getDoubleList(attributes.getValue("center"));
                    center = new Point3D(listOfDCenter.get(0),listOfDCenter.get(1),listOfDCenter.get(2));
                } else if (attributes.getQName(i).equalsIgnoreCase("radius")) {
                    rad = Double.parseDouble(attributes.getValue(i));
                }
            }
            geometriesList.add(new Sphere(rad, center));
            bSphere = true;
        } else if (qName.equalsIgnoreCase("plane")) {
            //TODO parser for plane

            bPlane = true;
        } else if (qName.equalsIgnoreCase("tube")) {
            //TODO parser for tube
            bTube = true;
        } else if (qName.equalsIgnoreCase("lights")) {
            //TODO parser for tube
            bLights = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("scene")) {
           bScene=false;
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
        if (bLights) {
            bLights = false;
        }
    }

    public List<Double> getDoubleList(String s) {
        String[] arrString = s.split(" ");
        List<Double> lisDouble = new ArrayList<Double>();
        for (int i = 0; i < arrString.length; i++) {
            lisDouble.add(d = Double.parseDouble(arrString[i]));
        }
        return lisDouble;
    }
}




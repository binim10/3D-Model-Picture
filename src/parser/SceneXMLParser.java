package parser;

import elements.AmbientLight;
import elements.Camera;
import geometries.Geometries;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import scene.Scene;

import java.util.ArrayList;
import java.util.List;

public class SceneXMLParser extends DefaultHandler {
    SceneDescriptor temp = new SceneDescriptor();
    private List<Scene> scenes = null;
    private Scene scene = null;
    private ImageWriter image=null;
    private Camera camera = null;
    private List<Geometries> geometriesList = null;
    private String data = null;
    private Double d = null;


    boolean bScene = false;
    boolean bColor = false;
    boolean bAmbientLight = false;
    boolean bGeometries = false;
    boolean bSphere=false;
    boolean bTriangle=false;
    boolean bPlane=false;
    boolean bTube=false;
    boolean bCamera = false;
    boolean bImage = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("scene")) {
            String name = temp._sceneAttributes.get("name");
            scene = new Scene(name);
            if (scenes == null)
                scenes = new ArrayList<>();
            bScene=true;
        }
        if (qName.equalsIgnoreCase("background")) {
            bColor = true;
        }
        if (qName.equalsIgnoreCase("ambient-light")) {
            bAmbientLight = true;
        }
        if (qName.equalsIgnoreCase("geometries")) {
            if (geometriesList==null)
                geometriesList=new ArrayList<>();
            bGeometries = true;
        }
        if (qName.equalsIgnoreCase("camera")) {

            bCamera = true;
        }
        if (qName.equalsIgnoreCase("image")) {
            String name=temp._imageWriter.get("name");
            double imageWidth = Double.parseDouble(temp._imageWriter.get("screen-width"));
            double imageHeight = Double.parseDouble(temp._imageWriter.get("screen-height"));
            int nX=Integer.parseInt(temp._imageWriter.get("Nx"));
            int nY=Integer.parseInt(temp._imageWriter.get("Ny"));
            image=new ImageWriter(name,imageWidth,imageHeight,nX,nY);
            bImage = true;
        }
        if (qName.equalsIgnoreCase("triangle")) {

            bTriangle = true;
        }
        if (qName.equalsIgnoreCase("sphere")) {

            bSphere = true;
        }
        if (qName.equalsIgnoreCase("plane")) {

            bPlane = true;
        }
        if (qName.equalsIgnoreCase("tube")) {

            bTube = true;
        }

        data = new String();

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (bColor) {
            String[] rgb = data.split(" ");
            scene.setBackground(new Color(d=Double.parseDouble(rgb[0]), d=Double.parseDouble(rgb[1]), d=Double.parseDouble(rgb[2])));
            bColor=false;
        }
        if(bImage){
            bImage=false;
        }
        if (bAmbientLight){
            String[] rgb = temp._ambientLightAttributes.get("color").split(" ");
            scene.setAmbientLight(new AmbientLight(new Color(d=Double.parseDouble(rgb[0]), d=Double.parseDouble(rgb[1]), d=Double.parseDouble(rgb[2])),1));
            bAmbientLight=false;
        }
        if(bCamera){
            String[] point = temp._cameraAttributes.get("P0").split(" ");
            String[] vUp = temp._cameraAttributes.get("Vup").split(" ");
            String[] vTo = temp._cameraAttributes.get("Vto").split(" ");
            camera = new Camera(new Point3D(d=Double.parseDouble(point[0]), d=Double.parseDouble(point[1]), d=Double.parseDouble(point[2])),
                    new Vector(d=Double.parseDouble(vTo[0]), d=Double.parseDouble(vTo[1]), d=Double.parseDouble(vTo[2])),
                    new Vector(d=Double.parseDouble(vUp[0]), d=Double.parseDouble(vUp[1]), d=Double.parseDouble(vUp[2])));
        }
        if(bGeometries){
            bGeometries=false;
        }
    }
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        data= data + new String(ch, start, length);
    }
}

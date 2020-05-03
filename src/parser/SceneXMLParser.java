package parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import primitives.Color;
import renderer.ImageWriter;
import scene.Scene;

import java.util.ArrayList;
import java.util.List;

public class SceneXMLParser extends DefaultHandler {
    SceneDescriptor temp = new SceneDescriptor();
    private List<Scene> scenes = null;
    private Scene scene = null;
    private ImageWriter image=null;
    private String data = null;
    private Double d = null;


    boolean bScene = false;
    boolean bColor = false;
    boolean bAmbientLight = false;
    boolean bGeometries = false;
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
        if (qName.equalsIgnoreCase("ambientLight")) {

            bAmbientLight = true;
        }
        if (qName.equalsIgnoreCase("geometries")) {
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
        data = new String();

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (bColor) {
            String[] rgb = data.split(" ");
            scene.setBackground(new Color(d=Double.parseDouble(rgb[0]), d=Double.parseDouble(rgb[1]), d=Double.parseDouble(rgb[2])));
        }
        if(bImage){
        }
    }
}

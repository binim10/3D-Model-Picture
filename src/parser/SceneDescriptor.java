package parser;
import javax.xml.parsers.SAXParser;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import scene.Scene;

public class SceneDescriptor {
    Map<String,String> _sceneAttributes;
    Map <String, String> _cameraAttributes;
    Map <String, String> _ambientLightAttributes;
    List <Map <String, String>> _spheres;
    List < Map <String, String>> _triangles;


    public void InitializeFromXMLstring(String xmlText){

    }



}

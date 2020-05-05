package parser;

import org.xml.sax.SAXException;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

/**
 * The type Scene builder.
 */
public class SceneBuilder {

    Scene _scene;
    ImageWriter _imageWriter;
    String filePath;

    /**
     * Load scene from file .
     *
     * @param uri the uri
     * @return the render
     */
    public Render loadSceneFromFile(String uri) {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        File file=new File(uri);
        if (!file.canRead()){
            throw new IllegalArgumentException("file corrupted");
        }
        filePath=uri;
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            SAXHandler handler = new SAXHandler();
            saxParser.parse(file, handler);
            _scene=handler.getScene();
            _imageWriter=handler.getImage();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return new Render(_imageWriter,_scene);
    }

}



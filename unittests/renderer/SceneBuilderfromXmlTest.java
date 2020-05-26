package renderer;

import org.junit.Test;
import org.xml.sax.SAXException;
import parser.SceneBuilder;
import renderer.Render;
import scene.Scene;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class SceneBuilderfromXmlTest {

    @Test
    public void loadSceneFromFiletest() {
        SceneBuilder sceneBuilder = new SceneBuilder();
        Render fromXml = sceneBuilder.loadSceneFromFile("C:\\Users\\binim\\IdeaProjects\\IME5780\\basicRenderTestTwoColors.xml");
        fromXml.renderImage();
        fromXml.printGrid(50, Color.yellow);
        fromXml.writeToImage();
    }
}

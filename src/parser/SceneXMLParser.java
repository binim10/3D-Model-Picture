package parser;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class SceneXMLParser {
    public static void main(String[] args) {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            SAXHandler handler =new SAXHandler();
            saxParser.parse(new File("C:\\Users\\binim\\IdeaProjects\\IME5780\\basicRenderTestTwoColors.xml"),handler);
        }
        catch(ParserConfigurationException | SAXException | IOException e){
                e.printStackTrace();

        }
    }
}


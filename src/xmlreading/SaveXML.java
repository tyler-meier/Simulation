package xmlreading;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class SaveXML {

    private final DocumentBuilder DOCUMENT_BUILDER;

    /**
     * Create Parser for a given XML file
     */
    public SaveXML() throws ParserConfigurationException {
        DOCUMENT_BUILDER = getDocumentBuilder();
        Document mySaveFile = DOCUMENT_BUILDER.newDocument();
    }
    public DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        SaveXML mySave = new SaveXML();

    }

}

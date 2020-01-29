package cellsociety;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class ReadXML {
    private final DocumentBuilder DOCUMENT_BUILDER;
    private final String TYPE;

    /**
     * Create Parser for a given XML file
     */
    public ReadXML(String type) throws ParserConfigurationException {
        DOCUMENT_BUILDER = getDocumentBuilder();
        TYPE = type;
    }

    /**
     * Acquires the root element of the xml file
     */
    public Element getRootElement(File xmlFile) throws IOException, SAXException {
        DOCUMENT_BUILDER.reset();
        Document xmlDoc = DOCUMENT_BUILDER.parse(xmlFile);
        return xmlDoc.getDocumentElement();
    }

    public DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    public boolean isValidFile(Element root, String type){
        return getAttribute(root, TYPE).equals(type);
    }

    public String getAttribute(Element e, String attributeName){
        return e.getAttribute(attributeName);
    }

    public String getTextValue(Element e, String tagName){
        NodeList list = e.getElementsByTagName(tagName);
        if(list != null && list.getLength()>0){
            return list.item(0).getTextContent();
        }
        else return "";
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        ReadXML mySim = new ReadXML("simulationType");
        File xmlFile = new File("data/sample.xml");
        Element root = mySim.getRootElement(xmlFile);
        System.out.println("Simulation Type is: "+ mySim.getAttribute(root,"simulationType"));
        System.out.println("My first val is: "+ mySim.getTextValue(root, "author"));
    }
}


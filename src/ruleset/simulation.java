package ruleset;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

abstract public class simulation {

     abstract public void update();

     abstract public int cellStatus(int row, int column);

     abstract public void readFile() throws IOException, SAXException, ParserConfigurationException;

}

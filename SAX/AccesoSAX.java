import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AccesoSAX {

    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) {
         try {
            // Crear una instancia de SAXParserFactory
            SAXParserFactory factory = SAXParserFactory.newInstance();

            // Crear una nueva instancia de SAXParser
            SAXParser saxParser = factory.newSAXParser();

            // Crear un nuevo manejador de eventos (handler)
            DefaultHandler handler = new LibrosHandler();

            // Parsear el archivo XML
            saxParser.parse(new File("libros.xml"), handler);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
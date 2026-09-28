
import java.io.File;
import java.io.IOError;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Programa{

    public static void main(String[] args) {


        try{

           //Crear instancia con SAXParserFactory
           SAXParserFactory factory = SAXParserFactory.newInstance();
           SAXParser sp = factory.newSAXParser();

           //Crear un nuevo manejador de eventos (Handler)
           DefaultHandler handler = new NasaHandler();

           //La URL del feed RSS de internet
           String urlNasa = "https://www.nasa.gov/feeds/iotd-feed/";


           //Parsear el XML
           SAXParser.parse(urlNasa, handler);    


        
    }catch (ParserConfigurationException | SAXException | IOException e){

        System.out.println(e.printStackTrace());
    }

}
}
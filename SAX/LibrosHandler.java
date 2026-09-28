import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class LibrosHandler extends DefaultHandler {

    boolean bTitulo = false;
    boolean bAutor = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("Libro")) {
            System.out.println("Elemento inicial: " + qName);
            System.out.println("\tFecha de publicación: "+attributes.getValue("publicado_en"));
        } else if (qName.equalsIgnoreCase("Titulo")) {
            bTitulo = true;
        } else if (qName.equalsIgnoreCase("Autor")) {
            bAutor = true;
        } 
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("Libro")) {
            System.out.println("Elemento final: " + qName);
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if (bTitulo) {
            System.out.println("\tTítulo: " + new String(ch, start, length));
            bTitulo = false;
        } else if (bAutor) {
            System.out.println("\tAutor: " + new String(ch, start, length));
            bAutor = false;
        }
    }
}

import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.File;

public class DOMExample {
    public static void main(String[] args) {
        try {
            // Obtener una fábrica de parsers DOM
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            // Parsear el documento XML
            Document doc = builder.parse(new File("Libros.xml"));
            
            // Acceder al nodo raíz
            Element root = doc.getDocumentElement();
            System.out.println("Elemento raíz: " + root.getNodeName());
            
            // Recorrer los nodos hijos
            NodeList libros = root.getElementsByTagName("Libro");
            for (int i = 0; i < libros.getLength(); i++) { 
                Element libro = (Element) libros.item(i);
                System.out.println("Título: " +
                    libro.getElementsByTagName("Titulo").item(0).getTextContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

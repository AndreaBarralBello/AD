import java.io.File;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class DOMnasa {
    public static void main(String[] args) {
        try {
            // Obtener una fábrica de parsers DOM
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            // Parsear el documento XML
            Document doc = builder.parse(new File("nasa.xml"));
            
            // Acceder al nodo raíz: rss
            Element root = doc.getDocumentElement();
            //el nodo raíz es rss
            System.out.println("Elemento raíz: " + root.getNodeName());
            
            //Hacemos una lista con los elementos list, que son las imágenes que queremos
            NodeList listaNoticias = (NodeList) root.getElementsByTagName("item");
            
    
            for (int i = 0; i < listaNoticias.getLength(); i++) {
               //Ahora separamos cada elemento
                Element imagen = (Element) listaNoticias.item(i);

                System.out.println("Noticia " +i+ " Titulo: " +
                imagen.getElementsByTagName("title").item(0).getTextContent());

                System.out.println("\tDescripcion: " + 
                imagen.getElementsByTagName("description").item(0).getTextContent());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

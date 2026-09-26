
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


/*Aplicación que permita mostrar el árbol de directorios de una carpeta
indicando el tipo de archivos que contiene según la cabecera del mismo 
(no por la extensión)f
 */
public class MagicNumbers {

    public static void main(String[] args) {

        File carpeta = pedirDatos();

        //Si no es null, es un directorio y existe
        if (carpeta != null && carpeta.isDirectory() && carpeta.exists()) {

            System.out.println("** Árbol de directorios **");

            listarArchivos(carpeta);

        } else {
            //No es un directorio
            System.out.println("No es un directorio");
        }
    }


    /*
    * Pedimos la ruta absoluta por consola
    *
    * @return File de la ruta pedida por consola
     */
    public static File pedirDatos() {

        Scanner teclado = new Scanner(System.in);
        String rutaDirectorio = "a";

        //Pido la ruta por teclado
        System.out.println("Introduce la ruta completa del directorio");
        rutaDirectorio = teclado.nextLine();

        //Creo el archivo File con el que vamos a trabajar
        File carpeta = new File(rutaDirectorio);

        return carpeta;
    }

    /*
    *Recorre las carpetas recursivamente hasta los ficheros
    *
    *@param File archivo que vamos a recorrer
     */
    public static void listarArchivos(File archivo) {

        //Si es un directorio
        if (archivo.isDirectory() && archivo.exists()) {
            System.out.println("Directorio: " + archivo.getName());

            File[] listaArchivos = archivo.listFiles();

            //Si tiene archivos, lo recorremos
            if (listaArchivos.length > 0 && listaArchivos != null) {

                for (int i = 0; i < listaArchivos.length; i++) {
                    listarArchivos(listaArchivos[i]);
                }
            } else {
                //No tiene archivos
                System.out.println("\nEl directorio no tiene archivos");
            }
            //Si es un fichero
        } else {
            try {

                String datosSeleccionados = seleccionarDato(archivo);
                comprobarTipoDato(datosSeleccionados);

            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
    }

    /* 
    * Selecciona solo los datos de la cabecera del archivo
    *
    * @param File archivo el archivo del que queremos conocer su cabecera
    * @return String obtenemos el código hexadecimal de los bytes seleccionados
     */
    public static String seleccionarDato(File archivo) {

        //Leemos en bytes y queremos pasarlo a String
        byte[] datosByte = new byte[4];
        StringBuilder agruparDatos = new StringBuilder();
        String datos = " ";

        try (FileInputStream fis = new FileInputStream(archivo)) {
            //Leo los datos en bytes, se parsean a String
            //con el método .read se copian los 4 bytes del archivo al array
            fis.read(datosByte);

            for (int i = 0; i < datosByte.length; i++) {
                agruparDatos.append(String.format("%02X", datosByte[i] & 0xFF));
            }

            datos = agruparDatos.toString();

            System.out.println("\t -> Nombre:  " + archivo.getName() + " " + datos);

        } catch (FileNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
        } catch (IOException e) {
            System.out.println("Error al leer los datos del archivo");
        }
        return datos;
    }

    /*
    * Clasifica el tipo de dato según su cabecera
    *
    * @param String dato cadena de texto de la cabecera del archivo
     */
 /*
    * Clasifica el tipo de dato según su cabecera
    *
    * @param String dato cadena de texto de la cabecera del archivo
     */
    public static void comprobarTipoDato(String dato) {

        //System.out.println(dato+" prueba que llega a swith");
        switch (dato.toUpperCase()) {
            case "89504E47":
                System.out.println("\t\tEs un archivo tipo PNG");
                break;
            case "47494638":
                System.out.println("\t\tEs un archivo de tipo GIF");
                break;
            case "FFD8FFE0":
                System.out.println("\t\tEs un archivo de tipo JPG");
                break;
            case "25504446":
                System.out.println("\t\tEs un archivo de tipo PDF");
                break;
            case "52617221":
                System.out.println("\t\tEs un archivo de tipo RAR");
                break;
            case "504B0304":
                System.out.println("\t\tEs un archivo de tipo ZIP");
                break;
            default:
                System.out.println("\t\tNO SE RECONOCE EL TIPO DE ARCHIVO");
        }
    }
}


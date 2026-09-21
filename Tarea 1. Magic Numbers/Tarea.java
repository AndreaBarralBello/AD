
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

/*Aplicación que permita mostrar el árbol de directorios de una carpeta
indicando el tipo de archivos que contiene según la cabecera del mismo 
(no por la extensión)f
 */
public class Tarea {

    public static void main(String[] args) {

        //Variables
        Scanner teclado = new Scanner(System.in);
        String rutaDirectorio = "a";
        String datosSeleccionados = " ";

        //Pido la ruta por teclado
        System.out.println("Introduce la ruta completa del directorio");
        rutaDirectorio = teclado.nextLine();

        //Creo el archivo File con el que vamos a trabajar
        File carpeta = new File(rutaDirectorio);

        //Ahora verificamos si es un directorio para poder recorrerlo


        /*TO DO AQUÍ ME QUEDA PENDIENTE SI 
            - EXISTE
            - ES DIRECTORIO
            - SI NO ESTÁ VACÍO
            - SI SE PUEDE RECORRER (LEER)
        */
        if (carpeta.isDirectory()) {

            System.out.println("Es un directorio");

            File[] listaArchivos = carpeta.listFiles();

            for (int i = 0; i < listaArchivos.length; i++) {
                File cadaArchivo = new File(listaArchivos[i].toString());
                datosSeleccionados = seleccionarDato(cadaArchivo);
                comprobarTipoDato(datosSeleccionados);
            }

        } else {

            System.out.println("No es un directorio");
        }

    }

    //Como FileInputStream lee el archivo completo, usamos RandomAccessFile
    //Primero leemos los 16 primeros bytes y los guardamos en String
    //Esto solo lo podemos hacer con RAF
    //los ajustamos al tamaño con StringBuilder
    //con switch comprobamos a qué tipo pertenecen
    //mostramos los resultados obtenidos por consola

    /* 
    * Selecciona solo los datos de la cabecera del archivo
    *
    * @param File archivo el archivo del que queremos conocer su cabecera
    * @return String obtenemos el código hexadecimal de los bytes seleccionados
     */
    public static String seleccionarDato(File archivo) {

        //Leemos en bytes y queremos pasarlo a String
        byte[] datosByte = new byte[4];
        StringBuilder datosBuilder = new StringBuilder();
        String datos = " ";

        try (RandomAccessFile raf = new RandomAccessFile(archivo, "r")) {
            //Leo los datos en bytes, se parsean a String
            //con el método .readFully se copian los 4 bytes del archivo al array
            raf.read(datosByte); //ahora el array de bytes que creamos
            // tiene los datos en bytes

            for (int i = 0; i < datosByte.length; i++) {
                datosBuilder.append(String.format("%02X", datosByte[i] & 0xFF));
                
            }

            datos = datosBuilder.toString();

            System.out.println("\tNombre:  " + archivo.getName() + " " + datos);

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

    public static void comprobarTipoDato(String dato) {
        
        //System.out.println(dato+" prueba que llega a swith");

        switch (dato.toUpperCase()) {
            case "89504E47":
                System.out.println("Es un archivo tipo PNG");
                break;
            case "47494638":
                System.out.println("Es un archivo de tipo GIF");
                break;
            case "FFD8FFE0":
                System.out.println("Es un archivo de tipo JPG");
                break;
            default:
                System.out.println("NO SE RECONOCE EL TIPO DE ARCHIVO");
        }

    }
}

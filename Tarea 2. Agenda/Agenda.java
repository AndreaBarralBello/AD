
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Agenda {

    // Variables
    private static final String ARCHIVO = "agenda.dat";
    private static final int TAMANO_REGISTRO = 96;
    private static final int MAX_NOMBRE = 80;
    private static final int MAX_TELEFONO = 16;

    public static void main(String[] args) throws IOException {

        Scanner teclado = new Scanner(System.in);
        int opcion = 0;

        do {

            System.out.println("******** AGENDA *********\n" +
                    " Escoge una opción\n" +
                    " 1. Muestra la agenda completa\n" +
                    " 2. Muestra contacto por posición\n" +
                    " 3. Añade un nuevo contacto\n" +
                    " 4. Elimina un contacto\n" +
                    " 5. Salir del programa\n");

            opcion = teclado.nextInt();

            switch (opcion) {
                case 1:
                    mostrarAgenda();
                    break;
                case 2:
                    mostrarContacto();
                    break;
                case 3:
                    añadirContacto();
                    break;
                case 4:
                    eliminarContacto();
                    break;
                case 5:
                    System.out.println("FIN DEL PROGRAMA");
                    break;
                default:
                    System.out.println("ERROR AL ESCOGER LA OPCIÓN");
            }

        } while (opcion >= 1 && opcion <= 5);
    }

    /*
     * Muestra todos los contactos de la agenda
     */
    public static void mostrarAgenda() throws IOException {

        try (RandomAccessFile raf = new RandomAccessFile(ARCHIVO, "r")) {

            raf.seek(0);

            if (raf.length() == 0) {
                System.out.println("NO HAY CONTACTOS EN LA AGENDA");
                return;
            }

            for (int i = 0; i < raf.length(); i++) {

                StringBuilder nombre = new StringBuilder();
                for (int d = 0; d < MAX_NOMBRE; d++) {
                    char c = raf.readChar();
                    if (c != ' ') {
                        nombre.append(c);
                    }
                }
                System.out.println("Nombre: " + nombre.toString());

                StringBuilder telefono = new StringBuilder();
                for (int x = 0; x < MAX_TELEFONO; x++) {
                    char b = raf.readChar();
                    if (b != ' ') {
                        telefono.append(b);
                    }
                }
                System.out.println("\tTelefono: " + telefono.toString());

            }

        } catch (IOException e) {

            System.out.println(e.getLocalizedMessage());
        }

    }

    /*
     * Muestra el contacto de la posición indicada
     */
    public static void mostrarContacto() {

        Scanner teclado = new Scanner(System.in);
        int posicion = 1;

        System.out.println("Introduce el número de posición a mostrar");
        posicion = teclado.nextInt();

        try (RandomAccessFile raf = new RandomAccessFile(ARCHIVO, "rw")) {

            long posicionBytes = (posicion -1) * TAMANO_REGISTRO;
            if (raf.length() < posicionBytes + TAMANO_REGISTRO) {

                System.out.println("No hay registros en la posicion " + posicion);
                return;
            }
          
            raf.seek(posicionBytes);

            StringBuilder nombre = new StringBuilder();
            for (int e = 0; e < MAX_NOMBRE; e++) {
                char c = raf.readChar();
                if (c != ' ') {
                    nombre.append(c);
                }
            }
        
            StringBuilder telefono = new StringBuilder();
            for (int d = 0; d < MAX_TELEFONO; d++) {
                char e = raf.readChar();
                if (e != ' ') {
                    telefono.append(e);
                }
            }
        
                        System.out.println("Contacto nº " + posicion + " Nombre: " + nombre.toString()
                    +"\n Telefono: " + telefono.toString());
        
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }

    }
    /*
     * Añade un nuevo contacto al final del archivo
     *
     */

    public static void añadirContacto() {

        Scanner teclado = new Scanner(System.in);
        String nombre = " ";
        String telefono = " ";

        System.out.println("\tIntroduce el nombre");
        nombre = teclado.nextLine();

        System.out.println("Introduce el teléfono: ");
        telefono = teclado.nextLine();

        try (RandomAccessFile raf = new RandomAccessFile(ARCHIVO, "rw")) {

            // lo añado al final del fichero
            long posicionBytes = raf.length();

            raf.seek(posicionBytes);

            StringBuilder nombreAjustado = new StringBuilder(nombre);
            nombreAjustado.setLength(MAX_NOMBRE);
            raf.writeChars(nombreAjustado.toString());

            StringBuilder telefonoAjustado = new StringBuilder(telefono);
            telefonoAjustado.setLength(MAX_TELEFONO);
            raf.writeChars(telefonoAjustado.toString());

            System.out.println(" -> Contacto creado correctamente");

        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
        }

    }

    /*
     * Elimina un contacto según su posición
     */

    public static void eliminarContacto() {

        Scanner teclado = new Scanner(System.in);
        int posicion = 1;

        System.out.println("Introduce la posición que quieres eliminar");
        posicion = teclado.nextInt();

        //Creo un array de bytes donde se van a guardar todos los registros antes de eliminar
        //el selecionado
        List<byte[]> listaContactos = new ArrayList<>();

        try (RandomAccessFile raf = new RandomAccessFile(ARCHIVO, "rw")) {

            long posicionBytes = (posicion -1) * TAMANO_REGISTRO;
            raf.seek(posicionBytes);

           

            for (int i = 0; i < listaContactos.size(); i++) {
                raf.read();
                raf.writeChars(ARCHIVO);

                System.out.println(i);

            }
            

            // Elimino la posición seleccionada

            // Creo una nueva lista sin el elemento seleccionado

        } catch (IOException e) {

            System.out.println(e.getLocalizedMessage());
        }

    }

}
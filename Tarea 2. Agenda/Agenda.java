
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Scanner;

public class Agenda {

    // Variables
    private static final String ARCHIVO = "amigos.dat";
    private static final int TAMANO_REGISTRO = 110;
    private static final int MAX_NOMBRE = 40;
    private static final int MAX_TELEFONO = 15;

    public static void main(String[] args) throws IOException {

        Scanner teclado = new Scanner(System.in);
        int opcion = 0;

        do {

            System.out.println("******** AGENDA *********\n"
                    + " Escoge una opción\n"
                    + " 1. Muestra la agenda completa\n"
                    + " 2. Muestra contacto por posición\n"
                    + " 3. Añade un nuevo contacto\n"
                    + " 4. Elimina un contacto\n"
                    + " 5. Salir del programa\n");

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
                default:
                    System.out.println("****  FIN DEL PROGRAMA");

            }

        } while (opcion >= 1 && opcion <= 4);
    }

    /*
     * Muestra todos los contactos de la agenda
     */
    public static void mostrarAgenda() throws IOException {

        try (RandomAccessFile raf = new RandomAccessFile(ARCHIVO, "rw")) {
        

            raf.seek(0);

            if (raf.length() == 0) {
                System.out.println("NO HAY CONTACTOS EN LA AGENDA");
                return;
            }

            for (int i = 0; i < raf.length(); i++) {
                String [] leyendoAmigos = leerAmigo(raf);


                System.err.println("Nombre: "+leyendoAmigos[0]);
                System.out.println("\tTelefono: " +leyendoAmigos[1]);

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

            //nos posicionamos en el contacto solicitado
            long posicionBytes = (posicion - 1) * TAMANO_REGISTRO;
            //Si la posición solicitada es mayor al tamaño del registro, no existe
            if (posicionBytes > raf.length()) {
                System.out.println("No hay registros en la posicion " + posicion);
                return;
            }

            //lo posiciono
            raf.seek(posicionBytes);

            //Uso el método leerAmigos() en esa posición
            String [] amigoSeleccionado = leerAmigo(raf);

            System.out.println("Contacto nº " + posicion + " Nombre: " +amigoSeleccionado[0]
                    + "\n Telefono: " + amigoSeleccionado[1]);

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

            escribirAmigo(raf, nombre, telefono);

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

        String nombreEliminado = "";

        System.out.println("Introduce la posición que quieres eliminar");
        posicion = teclado.nextInt();

        //Creo un HashMap donde se van a guardar todos los registros antes de eliminar
        //el selecionado
        HashMap<String, String> listaContactos = new HashMap<>();

        try (RandomAccessFile raf = new RandomAccessFile(ARCHIVO, "rw")) {

            //Sabemos que el nombre son 80 bytes y el teléfono 30 bytes
            //asi que recorro raf y voy a añadiendo al HashMap
            //Esto es el total del tamaño del fichero en bytes
            //me devuelve un long, y al /110 de cada amigo, me da el numero de amigos 
            //que tengo en la agenda          
            long totalRegistros = raf.length() / 110;

            //Comprobamos que el contacto exista en la agenda
            if (posicion < 1 || posicion > totalRegistros) {
                System.err.println("No existe ese contacto en la posición " + posicion);
                return;
            }

            //Recorro todo el raf, se lee y se guarda en HashMap
            raf.seek(0);

            for (int i = 0; i < totalRegistros; i++) {

              //leo cada amigo y lo añado al HashMap
              String [] listaAmigos = leerAmigo(raf);

                listaContactos.put(listaAmigos[0], listaAmigos[1]);

                //HashMap se define por clave-valor no por posición
                //Para elimiar en HashMap tiene que recibir la clave, en este caso el String nombre
                if (i == (posicion - 1)) {

                    //cuando llegue a la posición a eliminar guardamos en una variable el nombre
                    nombreEliminado = listaAmigos[0];

                    //Ahora que sabemos el nombre lo podemos eliminar en el HashMap
                    listaContactos.remove(nombreEliminado);

                }
            }

            //Ahora reescribo el fichero desde HashMap
            //Lo vacío y lo posiciono en el inicio
            raf.setLength(0);
            raf.seek(0);

//Con HashMap solo se pueden recorrer las claves o los valores, aquí es más cómodo
//recorrer las claves, los nombres
            for (String name : listaContactos.keySet()) {
                //para cada clave, coge el valor con get, que es el teléfono
                String phone = listaContactos.get(name);

                escribirAmigo(raf, name, phone);
            }

        } catch (IOException e) {

            System.out.println(e.getLocalizedMessage());
        }
        //Muestra mensaje de confirmación por consola
        System.out.println("->Contacto " + posicion + " - " + nombreEliminado + " eliminado");
    }

    /*
    * Lee un registro desde la posición del RandomAccessFile que se le pasa por parámetro
    *@param RandomAccessFile en una posición concreta
    *@return String [] con dos elementos, nombre y teléfono de cada registro
     */
    public static String[] leerAmigo(RandomAccessFile raf) throws IOException {

        StringBuilder nombre = new StringBuilder();
        for (int e = 0; e < MAX_NOMBRE; e++) {
            char c;
            c = raf.readChar();
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

        return new String[]{nombre.toString(), telefono.toString()};
    }


    /*
    *Escribe con RandomAccessFile un registo
    *@param RandomAccessFile, String, String la posición del RandomAccessFile donde vamos a escribir
    *el nombre y el teléfono que vamos a guardar en formato String
     */
    public static void escribirAmigo(RandomAccessFile raf, String nombre, String telefono) throws IOException {

        StringBuilder nombreAjustado = new StringBuilder(nombre);
        nombreAjustado.setLength(MAX_NOMBRE);
        raf.writeChars(nombreAjustado.toString());

        StringBuilder telefonoAjustado = new StringBuilder(telefono);
        telefonoAjustado.setLength(MAX_TELEFONO);
        raf.writeChars(telefonoAjustado.toString());

    }
}

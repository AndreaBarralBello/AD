
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;


public class Agenda{
    

    //Variables
    private static final String ARCHIVO = "agenda.dat";
    private static final int TAMANO_REGISTRO = 96;
    private static final int MAX_NOMBRE = 80;
    private static final int MAX_TELEFONO = 16;

    public static void main(String[] args) {

        String opcion = "";
        Scanner teclado = new Scanner (System.in);
        opcion = teclado.nextLine();


        do (

            System.out.println("******** AGENDA *********
            \n  Escoge una opción 
            \n 1. Muestra la agenda completa
            \n 2. Muestra contacto por posición
            \n 3. Añade un nuevo contacto
            \n 4. Elimina un contacto
            \n 5. Salir del programa");

            switch (opcion) {
                case "1":

                    
                    break;

                case "2":
                    break;

                case "3":
                    break;

                default:
                    System.out.println("ERROR AL ESCOGER LA OPCIÓN");
            }

          

        ) while (num>=1 && num<=4);
    }


public static void mostrarAgenda() throws IOException{

    try(RandomAccessFile raf = new RandomAccessFile (ARCHIVO, "rw")){
        
        StringBuilder nombre = new StringBuilder();
        for (int i = 0; i < MAX_NOMBRE; i++) {
            char c = raf.readChar();
            if (c != ' '){
                nombre.append(c);
            }
            
        }


    }catch(IOException e){

        System.out.println("Error en la lectura del archivo");
    }



}

}
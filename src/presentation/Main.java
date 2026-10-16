package presentation;

import data.TransferSockets;
import domain.MetadatosFichero;
import domain.PeticionAuth;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            menu();
            System.out.println("elije una opcion del menu");
            opcion = sc.nextInt();
            sc.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:

                    conectarEnviar(sc);

                    break;
                case 2:
                    System.out.println("salir del programa");
                    break;
                default:
                    System.out.println("opcion no válida");

            }

        } while (opcion != 2);

    }

    public static void menu() {
        System.out.println("========menu===========");
        System.out.println("opcion1 :  Conectar y enviar copia de seguridad");
        System.out.println("opcion2 : Salir");
    }

    public static void conectarEnviar(Scanner sc){

        System.out.println("introduce el usuario :");
        String usuario = sc.nextLine();
        System.out.println("introduce la contraseña :");
        String contrasenia = sc.nextLine();

        try(Socket socket=new Socket("localhost",16000)){
            TransferSockets transfer=new TransferSockets(socket);
            PeticionAuth peticionAuth=new PeticionAuth(usuario,contrasenia);
            transfer.sendPeticioAuth(peticionAuth);

            try {
                transfer.receiveRespuestaAuth();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }



            System.out.println("introduce la ruta del fichero que quieres subir");
            String ruta=sc.nextLine();

            File file=new File(ruta);

            MetadatosFichero metadatosFichero=new MetadatosFichero(file.getName(),file.length());
            transfer.sendMetaDatos(metadatosFichero);


            try {
                transfer.receiveRespuestaMetadatos();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }



        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
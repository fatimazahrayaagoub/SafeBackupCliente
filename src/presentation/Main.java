package presentation;

import data.TransferSockets;
import domain.model.MetadatosFichero;
import domain.model.PeticionAuth;
import domain.model.RespuestaAuth;
import domain.model.RespuestaMetadatos;

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

    public static void conectarEnviar(Scanner sc) {

        System.out.println("introduce el usuario :");
        String usuario = sc.nextLine();
        System.out.println("introduce la contraseña :");
        String contrasenia = sc.nextLine();

        try (Socket socket = new Socket("localhost", 16000)) {
            System.out.println("esperando al servidor:..............");
            TransferSockets transfer = new TransferSockets(socket);
            PeticionAuth peticionAuth = new PeticionAuth(usuario, contrasenia);
            transfer.sendPeticioAuth(peticionAuth);
            System.out.println("enviando peticion auth al sevidor (ususario,contraseña)...........");

            try {

                RespuestaAuth respuestaAuth = transfer.receiveRespuestaAuth();
                if (respuestaAuth.isExito()) {
                    System.out.println("envio terminado con exito,recebimos un respuesta");
                    System.out.println("introduce la ruta del fichero que quieres subir");
                    String ruta = sc.nextLine();

                    File file = new File(ruta);
                    if (file.exists()) {

                        MetadatosFichero metadatosFichero = new MetadatosFichero(file.getName(), file.length());
                        transfer.sendMetaDatos(metadatosFichero);
                        System.out.println("enviando los meta datos del fichero que queremos enviar");
                        try {
                            RespuestaMetadatos respuestaMetadatos = transfer.receiveRespuestaMetadatos();
                            if (respuestaMetadatos.isAceptado()) {
                                System.out.println("envio terminado con exito, recebimos la respuesta");
                                transfer.SendBody(metadatosFichero, "./ficheros");
                                System.out.println("enviando el archiv al servidor");

                            } else {
                                System.out.println("los metadatos que hemos enviado están mal");
                            }


                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.out.println("el archivo no existe ");
                    }

                } else {
                    System.out.println("las credenciales está mal ");


                }

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
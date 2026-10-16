package presentation;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("elije una opcion del menu");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("introduce el usuario :");
                    String usuario = sc.nextLine();
                    System.out.println("introduce la contraseña :");
                    String contrasenia = sc.nextLine();




                    break;
                case 2:
                    System.out.println("salir del programa");
                    break;
                default:
                    System.out.println("opcion no válida");

            }

        } while (opcion != 2);

    }

    public void menu() {
        System.out.println("========menu===========");
        System.out.println("opcion1 :  Conectar y enviar copia de seguridad");
        System.out.println("opcion2 : Salir");
    }
}
package EjerciciosUD;

import java.util.Scanner;

public class Archivo {
    public static void main(String[] args) {
        boolean salir=false;
        Scanner sc = new Scanner(System.in);

        while (!salir) {
            System.out.println("Edad: ");
            int edad = sc.nextInt();

            if (edad>=18) {//1
                System.out.println("Eres español?");//2
                String respuesta = sc.next();
                if (respuesta.equalsIgnoreCase("si")) {//3
                    System.out.println("Tienes "+edad+" eres mayor de edad");//4
                    System.out.println("Y de nacionalidad española.");//5
                }else{
                    System.out.println("Eres europeo?");
                    String europeo = sc.next();
                    if (europeo.equalsIgnoreCase("si")) {//6
                        System.out.println("Tienes "+edad+" eres mayor de edad y eres Europeo.");//8
                    }else{
                        System.out.println("Tienes "+edad+" eres mayor de edad y no eres Europeo.");//7
                    }
                }
            }else{salir=true;}
        }
        sc.close();
    }
}
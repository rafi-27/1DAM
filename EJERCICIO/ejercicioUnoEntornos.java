package EJERCICIO;

import java.util.Scanner;

public class ejercicioUnoEntornos {
    public static void main(String[] args) {
        Scanner tc = new Scanner(System.in);
        //Creamos un array bidimensional:
        System.out.println("Introduce las dimensiones del array: ");
        int[][] arrayB = new int[tc.nextInt()][tc.nextInt()];

        /**
         * Rellenamos el array
         */
        for (int i = 0; i < arrayB.length; i++) {
            for (int j = 0; j < arrayB[0].length; j++) {
                System.out.println("Posicion: "+i+"."+j);
                arrayB[i][j]=tc.nextInt();
            }
        }

        int contMay=0;
        int contMen=0;
        int contIgual=0;
        /**
         * Contamos cuantos son mayores, menores, iguales.
         */
        for (int i = 0; i < arrayB.length; i++) {
            for (int j = 0; j < arrayB[0].length; j++) {
                if (arrayB[i][j]>0) {
                    contMay++;
                }else if (arrayB[i][j]<0) {
                    contMen++;
                }else if (arrayB[i][j]==0) {
                    contIgual++;
                }
            }
        }
        /**
         * Lo printeamos.
         */
        System.out.println("Mayores a 0: "+contMay);
        System.out.println("Menores a 0: "+contMen);
        System.out.println("Iguales a 0: "+contIgual);
        tc.close();
    }
}
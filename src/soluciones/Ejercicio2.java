package soluciones;

import java.io.*;
import java.util.*;

public class Ejercicio2{
    public static void main(String[] args) throws IOException {
        Scanner capt = new Scanner(System.in);
        long p = capt.nextLong();
        Run(p);
        capt.close();
    }
    public static void Run(long p){
        //--Valor de la máximo exponente que puede llegar a tener un valor +1 o -1
        int maxExponente = (int) Math.ceil(Math.log(p)/Math.log(3));
        //--Lista que va a almacenar los exponentes que tengan asignadoun +1
        LinkedList<Long> listaPositivos = new LinkedList<Long>();
        //--Lista que va a almacenar los exponentes que tengan asignadoun -1
        LinkedList<Long> listaNegativos = new LinkedList<Long>();

        int[] potencias = new int[maxExponente+1];
        potencias[0] = 1;
        for (int i = 1; i<potencias.length; ++i) {
          potencias[i] = 3*potencias[i-1];
        }

        //--Llama a la función recursiva que resuelve el problema
        dameCombinacion(p, maxExponente, listaPositivos, listaNegativos, potencias);
        //---Llama a la función que imprime el resultado en el formato correcto
        imprimirSolucion(listaPositivos, listaNegativos);
    }
    private static void dameCombinacion(long p, int exponenteActual, LinkedList<Long> listaPositivos, LinkedList<Long> listaNegativos, int[] potencias) {
        if (exponenteActual < 0) {
            return;
        }

        long potenciaActual = potencias[exponenteActual];

        //--- Elije el valor para acercarse mas al número p
        if (Math.abs(p - potenciaActual) < Math.ceil((double)potenciaActual / 2)) {
            //--- Le conviene poner +1
            listaPositivos.push(potenciaActual);
            p -= potenciaActual;
        } else if (Math.abs(p + potenciaActual) < Math.ceil((double)potenciaActual / 2)) {
            //--- Le conviene poner -1
            listaNegativos.push(potenciaActual);
            p += potenciaActual;
        }
        //--- Luego de restar/sumar la potencia actual
        //--- disminuye el coeficiente y se llama recursivamente
        dameCombinacion(p, exponenteActual - 1, listaPositivos, listaNegativos, potencias);
    }

    private static void imprimirSolucion(LinkedList<Long> listaPositivos, LinkedList<Long> listaNegativos){
        //--Imprime la cantidad de positivos y negativos
        System.out.println(listaPositivos.size() + " " + listaNegativos.size());
        //--Imprime todos los exponente que tienen asignado un valor +1
        for (int i = 0; i<listaPositivos.size(); ++i){
            System.out.print(listaPositivos.get(i));
            if(i != listaPositivos.size()-1){
                System.out.print(" ");
            }
        }
        System.out.println();
        //--Imprime todos los exponente que tienen asignado un valor -1
        for (int i = 0; i<listaNegativos.size(); ++i){
            System.out.print(listaNegativos.get(i));
            if(i != listaNegativos.size()-1){
                System.out.print(" ");
            }
        }
        System.out.println();
    }
}

package soluciones;

import java.util.*;

public class Ejercicio3_td{

  public static class Tesoro{
    public int peso;
    public int valor;
    public int tipo;
    public Tesoro(int p, int v, int t){
      peso = p;
      valor = v;
      tipo = t;
    }
  }

  public static class Casilla{
    public int beneficio; // el beneficio maximo hasta ahi
    public int mochila; // mochila donde puse el objeto actual (si es que lo puse en alguno)
    public int hijoWeight1;
    public int hijoWeight2;
    public int hijoWeight3;
    public boolean visitado;

    public Casilla(int b){
      beneficio = b;
      mochila = -1;
      hijoWeight1 = -1;
      hijoWeight2 = -1;
      hijoWeight3 = -1;
      visitado = false;
    }

    public Casilla(int b, int m, int h1,int h2, int h3){
      beneficio = b;
      mochila = m;
      hijoWeight1 = h1;
      hijoWeight2 = h2;
      hijoWeight3 = h3;
      visitado = true;
    }
  }


  public static void main(String[] args){
    Scanner capt = new Scanner(System.in);
    int m = capt.nextInt();
    int n = capt.nextInt();
    int k[] = new int[3];
    for(int i = 0; i<m; ++i){
      k[i] = capt.nextInt();
    }
    for (int i = m; i<3; ++i){
      k[i] = 0;
    }
    LinkedList<Tesoro> tesoros = new LinkedList<Tesoro>();

    for(int i = 0; i<n; ++i){
      int cantidad = capt.nextInt();
      int peso = capt.nextInt();
      int valor = capt.nextInt();
      int j = 0;
      while(j<cantidad){
        Tesoro nuevo = new Tesoro(peso, valor, i+1);
        tesoros.add(nuevo);
        ++j;
      }
    }
    capt.close();
    Run(k,m,tesoros);
  }

  public static void Run(int[] k, int m, LinkedList<Tesoro> tesoros){
    Casilla[][][][] matriz = new Casilla[tesoros.size()][k[0]+1][k[1]+1][k[2]+1];
    for (int i = 0; i<tesoros.size(); ++i){
      for (int j = 0; j<k[0]+1; ++j){
        for (int q = 0; q<k[1]+1; ++q){
          for (int w = 0; w<k[2]+1; ++w){
            matriz[i][j][q][w] = new Casilla(tesoros.get(i).valor);
          }
        }
      }
    }
    knapsack(matriz,tesoros,tesoros.size()-1, k[0], k[1], k[2]);
    System.out.println(matriz[tesoros.size()-1][k[0]][k[1]][k[2]].beneficio);
    LinkedList<Integer> mochila1 = new LinkedList<Integer>();
    LinkedList<Integer> mochila2 = new LinkedList<Integer>();
    LinkedList<Integer> mochila3 = new LinkedList<Integer>();
    for (int i = tesoros.size()-1; i>=0 ; --i){ // piso k porque no lo voy a volver a usar
      Casilla actual = matriz[i][k[0]][k[1]][k[2]];
      switch (actual.mochila){
        case 0:
          mochila1.add(tesoros.get(i).tipo);
          break;
        case 1:
          mochila2.add(tesoros.get(i).tipo);
          break;
        case 2:
          mochila3.add(tesoros.get(i).tipo);
          break;
      }
      k[0] = actual.hijoWeight1;
      k[1] = actual.hijoWeight2;
      k[2] = actual.hijoWeight3;
    }
    System.out.print(mochila1.size());
    for (Integer a : mochila1){
      System.out.print(" " + a);
    }
    System.out.println();
    if(m>=2){
      System.out.print(mochila2.size());
      for (Integer a : mochila2){
        System.out.print(" " + a);
      }
      System.out.println();
    }
    if(m>=3){
      System.out.print(mochila3.size());
      for (Integer a : mochila3){
        System.out.print(" " + a);
      }
      System.out.println();
    }
  }

  public static int knapsack(Casilla[][][][] matriz, LinkedList<Tesoro> tesoros, int i, int weight1, int weight2, int weight3){
    if(i<0){ // no hay mas objetos para recorrer
      return 0;
    }

    if (matriz[i][weight1][weight2][weight3].visitado){ // ya calcule este casillero de mi matriz
      return matriz[i][weight1][weight2][weight3].beneficio;
    }else{ // tengo que calcular esa posicion de la matriz
      int[] beneficios = new int[4];
      int pesoActual = tesoros.get(i).peso;
      int valorActual = tesoros.get(i).valor;
      if (pesoActual<= weight1) {
        beneficios[0] = valorActual + knapsack(matriz, tesoros, i-1, weight1-pesoActual, weight2, weight3);
      }
      if (pesoActual<= weight2) {
        beneficios[1] = valorActual + knapsack(matriz, tesoros, i-1, weight1, weight2-pesoActual, weight3);
      }
      if (pesoActual<= weight3) {
        beneficios[2] = valorActual + knapsack(matriz, tesoros, i-1, weight1, weight2, weight3-pesoActual);
      }
      beneficios[3] = knapsack(matriz, tesoros, i-1, weight1,weight2, weight3); // no lo puse

      int max = 0;
      int indMax = 0;
      for (int j = 0; j <4; ++j){
        if(max<= beneficios[j]){
          max = beneficios[j];
          indMax = j;
        }
      }	

      switch (indMax){ // indMax es la mochila en la que decidi poner el tesoro
        case 3:
          matriz[i][weight1][weight2][weight3] = new Casilla(beneficios[indMax], indMax, weight1, weight2, weight3);
          break;
        case 2:
          matriz[i][weight1][weight2][weight3] = new Casilla(beneficios[indMax], indMax, weight1, weight2, weight3-pesoActual);
          break;
        case 1:
          matriz[i][weight1][weight2][weight3] = new Casilla(beneficios[indMax], indMax, weight1, weight2-pesoActual, weight3);
          break;
        case 0:
          matriz[i][weight1][weight2][weight3] = new Casilla(beneficios[indMax], indMax, weight1-pesoActual, weight2, weight3);
          break;
      }
      return matriz[i][weight1][weight2][weight3].beneficio;
    }
  }
}

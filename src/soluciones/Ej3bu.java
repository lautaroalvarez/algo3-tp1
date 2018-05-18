package soluciones;

import java.util.*;

public class Ej3bu{

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

    public Casilla(int b){
      beneficio = b;
      mochila = -1;
      hijoWeight1 = -1;
      hijoWeight2 = -1;
      hijoWeight3 = -1;
    }

    public Casilla(int b, int m, int h1,int h2, int h3){
      beneficio = b;
      mochila = m;
      hijoWeight1 = h1;
      hijoWeight2 = h2;
      hijoWeight3 = h3;
    }
  }


  public static void main(String[] args){
    long time = System.currentTimeMillis();
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
    Casilla[][][][] matriz = new Casilla[tesoros.size()+1][k[0]+1][k[1]+1][k[2]+1];
    for (int i = 0; i<tesoros.size(); ++i){
      for (int j = 0; j<k[0]+1; ++j){
        for (int q = 0; q<k[1]+1; ++q){
          for (int w = 0; w<k[2]+1; ++w){
            if(i == 0){
              matriz[i][j][q][w] = new Casilla(0);
            }else{
              matriz[i][j][q][w] = new Casilla(tesoros.get(i-1).valor);
            }
          }
        }
      }
    }
    System.out.println(knapsack(matriz,tesoros, k));
    LinkedList<Integer> mochila1 = new LinkedList<Integer>();
    LinkedList<Integer> mochila2 = new LinkedList<Integer>();
    LinkedList<Integer> mochila3 = new LinkedList<Integer>();
    for (int i = tesoros.size(); i>=1 ; --i){ // piso k porque no lo voy a volver a usar
      Casilla actual = matriz[i][k[0]][k[1]][k[2]];
      switch (actual.mochila){
        case 0:
          mochila1.add(tesoros.get(i-1).tipo);
          break;
        case 1:
          mochila2.add(tesoros.get(i-1).tipo);
          break;
        case 2:
          mochila3.add(tesoros.get(i-1).tipo);
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

  public static int knapsack(Casilla[][][][] matriz, LinkedList<Tesoro> tesoros, int pesos[]){
    for (int i = 1; i<tesoros.size()+1; ++i) {
      for (int peso1 = 0; peso1<=pesos[0]; ++peso1) {
        for (int peso2 = 0; peso2<=pesos[1]; ++peso2) {
          for (int peso3 = 0; peso3<=pesos[2]; ++peso3) {
            int[] beneficios = new int[4];
            int pesoActual = tesoros.get(i-1).peso;
            int valorActual = tesoros.get(i-1).valor;
            if (tesoros.get(i-1).peso <= peso1) {
              beneficios[0] = tesoros.get(i-1).valor+matriz[i-1][peso1-tesoros.get(i-1).peso][peso2][peso3].beneficio;
            }
            if (tesoros.get(i-1).peso <= peso2) {
              beneficios[1] = tesoros.get(i-1).valor+matriz[i-1][peso1][peso2-tesoros.get(i-1).peso][peso3].beneficio;
            }
            if (tesoros.get(i-1).peso <= peso3) {
              beneficios[2] = tesoros.get(i-1).valor+matriz[i-1][peso1][peso2][peso3-tesoros.get(i-1).peso].beneficio;
            }
            beneficios[3] = matriz[i-1][peso1][peso2][peso3].beneficio;

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
                matriz[i][peso1][peso2][peso3] = new Casilla(beneficios[indMax], indMax, peso1, peso2, peso3);
                break;
              case 2:
                matriz[i][peso1][peso2][peso3] = new Casilla(beneficios[indMax], indMax, peso1, peso2, peso3-pesoActual);
                break;
              case 1:
                matriz[i][peso1][peso2][peso3] = new Casilla(beneficios[indMax], indMax, peso1, peso2-pesoActual, peso3);
                break;
              case 0:
                matriz[i][peso1][peso2][peso3] = new Casilla(beneficios[indMax], indMax, peso1-pesoActual, peso2, peso3);
                break;
            }
          }
        }
      }
    }
    return matriz[tesoros.size()][pesos[0]][pesos[1]][pesos[2]].beneficio;
  }

}

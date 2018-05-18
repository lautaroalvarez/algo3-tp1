package soluciones;

import java.util.*;

public class Ejercicio3_bu{

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

  public static LinkedList<Tesoro> tesoros = new LinkedList<Tesoro>();
  public static Casilla[][][][] matriz;

  public static void main(String[] args){
    //long time_start, time_end;
    //time_start = System.currentTimeMillis();

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

    for(int i = 0; i<n; ++i){
      int cantidad = capt.nextInt();
      int peso = capt.nextInt();
      int valor = capt.nextInt();
      int j = 0;
      while(j<cantidad){
        Tesoro nuevo = new Tesoro(peso, valor, i+1);
        Ejercicio3_bu.tesoros.add(nuevo);
        ++j;
      }
    }
    capt.close();
    Run(k,m);

    //time_end = System.currentTimeMillis();
    //System.out.println(time_end - time_start);
    //double currentMemory = (double)((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1024);
    //System.out.println(currentMemory);
  }

  public static void Run(int[] k, int m){
    Ejercicio3_bu.matriz = new Casilla[Ejercicio3_bu.tesoros.size()][k[0]+1][k[1]+1][k[2]+1];
 
    int weight[] = new int[3];
    for (int i = Ejercicio3_bu.tesoros.size()-1; i >= 0; i--){
      for (int j = 0; j<= k[0]; ++j){
        weight[0] = j;
        for (int q = 0; q <= k[1]; ++q){
          weight[1] = q;
          for (int w = 0; w <= k[2]; ++w){
            weight[2] = w;
            Ejercicio3_bu.matriz[i][j][q][w] = new Casilla(Ejercicio3_bu.tesoros.get(i).valor);
            knapsack(i, weight);
          }
        }
      }
    }

    System.out.println(Ejercicio3_bu.matriz[0][k[0]][k[1]][k[2]].beneficio);
    LinkedList<Integer>[] mochila = new LinkedList[m];
    for (int i = 0; i < m; i++) {
      mochila[i] = new LinkedList<Integer>();
    }

    for (int i = 0; i < Ejercicio3_bu.tesoros.size(); ++i) { // piso k porque ya no lo voy a usar
      Casilla actual = Ejercicio3_bu.matriz[i][k[0]][k[1]][k[2]];
      if (actual.mochila >= 0 && actual.mochila < 3) {
          mochila[actual.mochila].add(Ejercicio3_bu.tesoros.get(i).tipo);
      }
      k[0] = actual.hijoWeight1;
      k[1] = actual.hijoWeight2;
      k[2] = actual.hijoWeight3;
    }


    for (int i = 0; i < m; i++) {
      System.out.print(mochila[i].size());
      for (Integer a : mochila[i]) {
        System.out.print(" " + a);
      }
      System.out.println();
    }

  }

  public static int knapsack(int i, int weight[]){
    if (i >= Ejercicio3_bu.tesoros.size()) {
      // no hay mas objetos para recorrer
      return 0;
    }

    if (Ejercicio3_bu.matriz[i][weight[0]][weight[1]][weight[2]].visitado) {
      // ya calcule este casillero de mi Ejercicio3_bu.matriz
      return Ejercicio3_bu.matriz[i][weight[0]][weight[1]][weight[2]].beneficio;
    } else {
      // tengo que calcular esa posicion de la Ejercicio3_bu.matriz
      int pesoActual = Ejercicio3_bu.tesoros.get(i).peso;
      int valorActual = Ejercicio3_bu.tesoros.get(i).valor;

      int weightAux[][] = new int[4][3];
      for (int k = 0; k < 4; k++) {
        for (int j = 0; j < 3; j++) {
          weightAux[k][j] = weight[j];
          if (j==k) {
            weightAux[k][j] -= pesoActual;
          }
        }
      }

      int maximo = 3;
      int beneficio_maximo = knapsack(i+1, weightAux[maximo]);

      for (int j = 0; j < 3; j++) {
        if (pesoActual <= weight[j]) {
          int nuevoBeneficio = valorActual + knapsack(i+1, weightAux[j]);
          if (nuevoBeneficio > beneficio_maximo) {
            maximo = j;
            beneficio_maximo = nuevoBeneficio;
          }
        }
      }

      Ejercicio3_bu.matriz[i][weight[0]][weight[1]][weight[2]].beneficio = beneficio_maximo;
      Ejercicio3_bu.matriz[i][weight[0]][weight[1]][weight[2]].mochila = maximo;
      Ejercicio3_bu.matriz[i][weight[0]][weight[1]][weight[2]].hijoWeight1 = weightAux[maximo][0];
      Ejercicio3_bu.matriz[i][weight[0]][weight[1]][weight[2]].hijoWeight2 = weightAux[maximo][1];
      Ejercicio3_bu.matriz[i][weight[0]][weight[1]][weight[2]].hijoWeight3 = weightAux[maximo][2];
      Ejercicio3_bu.matriz[i][weight[0]][weight[1]][weight[2]].visitado = true;
      //Ejercicio3_bu.matriz[i][weight[0]][weight[1]][weight[2]] = new Casilla(beneficio_maximo, maximo, weightAux[maximo][0], weightAux[maximo][1], weightAux[maximo][2]);

      return beneficio_maximo;
    }
  }
}

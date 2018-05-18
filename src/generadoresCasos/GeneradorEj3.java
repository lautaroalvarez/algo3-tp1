package generadoresCasos;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class GeneradorEj3 {

	public static class Caso{ // clase qeu encapsula los elementos para poder armar un "caso"
		private LinkedList<Tesoro> tesoro;
		private int n;
		private int m;
		private List<Integer> pesosMochilas;
		
		public LinkedList<Tesoro> getTesoro() {
			return tesoro;
		}
		public int getN() {
			return n;
		}
		public int getM() {
			return m;
		}
		public List<Integer> getPesosMochilas() {
			return pesosMochilas;
		}
		public void setTesoro(LinkedList<Tesoro> tesoro) {
			this.tesoro = tesoro;
		}
		public void setM(int k) {
			this.m = k;
		}
		public void setN(int n) {
			this.n = n;
		}
		public void setPesosMochilas(List<Integer> pesosMochilas) {
			this.pesosMochilas = pesosMochilas;
		}
	}
	
	public static class Tesoro {
		public int peso;
		public int valor;
		public int tipo;

		public Tesoro(int p, int v, int t) {
			peso = p;
			valor = v;
			tipo = t;
		}
	
	
    public int getPeso() {
			return peso;
		}

		public int getValor() {
			return valor;
		}

		public int getTipo() {
			return tipo;
		}

		public void setPeso(int peso) {
			this.peso = peso;
		}

		public void setValor(int valor) {
			this.valor = valor;
		}

		public void setTipo(int tipo) {
			this.tipo = tipo;
		}

	}
	public static String parseString(List<Tesoro> coso) {
        String line = "";
        for (int i=0;i<coso.size(); i++) {
    		line = line.concat(String.valueOf(coso.get(i).getPeso()));
    		if (i != coso.size() - 1) {
    			line = line.concat(" ");              
    		}
        }
        return line;      
    }
    
		/*
		 * N M
		 * V_1 V_2 V_3
		 * W_1 W_2 W_3
		 */
	    public static LinkedList<Caso> GenerarCasosAleatoriosEj3(int cantCasos) throws IOException {
	    	LinkedList<Caso> res = new LinkedList<Caso>();
	    	Random rnd = new Random(); //el generador de numeros random que usaremos para la cantidad de personas
//    		File file = new File("AleatoriosEj3.in");
//    		file.createNewFile();
//    		FileWriter fw = new FileWriter(file.getAbsoluteFile());
//    		BufferedWriter bw = new BufferedWriter(fw);
	        for(int i = 1; i < cantCasos+1; i++) {
	        	Caso caso_i = new Caso();
	        	int n = (rnd.nextInt() % 60)+1;
	        	int m = (rnd.nextInt() % 3)+1;
	        	caso_i.setN(n);
	        	caso_i.setM(m);
//	        	bw.write(m + " " + n );
//	        	bw.newLine();
	        	LinkedList<Integer> ks = new LinkedList<Integer>();
	        	for (int j = 0; j < m; j++){
	        		int k = rnd.nextInt() % 50 + 1;
	        		ks.add(k);
//	        		bw.write(k+ " ");
//	        		if (j == m-1) {
//	        			bw.write(k);
//	        		}else{
//	        			bw.write(k+" ");
//	        		}
	        	}
	        	int c = rnd.nextInt() % 50 + 1; //FIXME esto hay que calcularlo bien
	        	int p = rnd.nextInt() % 100 + 1;
	        	int v = rnd.nextInt() % 50 + 1;
	            LinkedList<Tesoro> tesoros = new LinkedList<Tesoro>();
	            for(int j = 0; j < n; j++) {// rellenamos cada arreglo.
	            	Tesoro can = new Tesoro(p, v, c); 
	            	tesoros.add(can);
	            }
	            caso_i.setPesosMochilas(ks);
	            caso_i.setTesoro(tesoros);
//			    bw.write(parseString(tesoros));
//			    bw.newLine();
//			    bw.newLine();
//			    bw.newLine();
	            res.add(caso_i);
	        }
	        
//	        bw.close();
	        return res;
	    }
	    
	    public static void main(String[] args) throws IOException 
	    {
			Scanner capt = new Scanner(System.in);
			int cantCasos = capt.nextInt();
			capt.close();
			GenerarCasosAleatoriosEj3(cantCasos);
	    }
	}



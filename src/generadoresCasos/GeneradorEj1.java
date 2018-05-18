package generadoresCasos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import utils.Persona;

public class GeneradorEj1 {


	
    public static String parseString(List<Persona> coso) {
        String line = "";
        for (int i=0;i<coso.size(); i++) {
    		line = line.concat(String.valueOf(coso.get(i).dameTiempo()));
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
	    public static void GenerarCasosAleatoriosEj1(int cantCasos) throws IOException {
	        Random rnd_can = new Random() ; //el generador de numeros random que usaremos para la cantidad de personas
	        Random rnd_arq = new Random(); // cantidad de arqueologos.
	        Random velocidad = new Random();
    		File file = new File("AleatoriosEj1.in");
    		file.createNewFile();
    		FileWriter fw = new FileWriter(file.getAbsoluteFile());
    		BufferedWriter bw = new BufferedWriter(fw);
	        for(int i = 1; i < cantCasos+1; i++) {
	        	int intermedio_n = rnd_can.nextInt(3);
	        	int n = intermedio_n == 0 ? 1 :intermedio_n;
	        	int intermedio_m = rnd_arq.nextInt(3);
	        	int m = intermedio_m == 0 ? 1 :intermedio_m;
	        	bw.write(n + " " + m );
	        	bw.newLine();
	            LinkedList<Persona> canibales = new LinkedList<Persona>();
	            LinkedList<Persona> arqueologos = new LinkedList<Persona>();
	            for(int j = 0; j < n; j++) {// rellenamos cada arreglo.
	            	Persona can = new Persona("C", velocidad.nextInt((int)((Math.pow(10,6))))); 
	            	canibales.add(can);
	            }
	            for(int j=0; j< m; j++)
	            {
	            	Persona arq = new Persona("A", velocidad.nextInt((int)((Math.pow(10,6))))); 
	            	arqueologos.add(arq);
	            } 
	            
			    bw.write(parseString(canibales));
			    bw.newLine();
			    bw.write(parseString(arqueologos));
			    bw.newLine();
			    bw.newLine();
	        }
	        bw.close();
	    }
	    
	    public static void main(String[] args) throws IOException 
	    {
			Scanner capt = new Scanner(System.in);
			int cantCasos = capt.nextInt();
			capt.close();
			GenerarCasosAleatoriosEj1(cantCasos);
	    }
}



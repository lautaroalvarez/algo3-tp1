package soluciones;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeSet;

import utils.Persona;

public class Ejercicio1{


	public static void main(String[] args){
		
		long time_start, time_end;
		time_start = System.currentTimeMillis();
		
		Ejercicio1 e = new Ejercicio1();
        
        
		Scanner capt = new Scanner(System.in);
        int estrategia = capt.nextInt();
        int N_arqs = capt.nextInt();
        int M_canis = capt.nextInt();
        capt.nextLine();
        LinkedList<Persona> inicial = new LinkedList<Persona>();
       
        
        while(N_arqs > 0){
        	Persona p = new Persona("A", capt.nextInt());
        	inicial.add(p);
        	N_arqs--;
        }

        capt.nextLine();
        while(M_canis > 0){
        	Persona p = new Persona("C", capt.nextInt());
        	inicial.add(p);
        	M_canis--;
        }
        capt.close();

        //int estrategia= 2;
        //System.out.println(e.Resolver(inicial,estrategia));
        int asd = e.Resolver(inicial,estrategia);
        //System.out.println("termine");
		time_end = System.currentTimeMillis();
		System.out.println(time_end - time_start);
	}
	
	@SuppressWarnings("unchecked")
	private int Resolver(LinkedList<Persona> iniciales, int tipo_estrategia){
		int tiempoMax = -1;/*Pensar una cota maxima para los iniciales*/

		int k = 0;
		for(Persona p : iniciales){	//Seteo las ids con números primos.
			p.setID(k);
			k++;					
		}
		
		Integer[] arr_instancia = new Integer[iniciales.size() + 1]; //Arreglo que indentifica la instancia actual personas mas linterna
		for(int i = 0; i < arr_instancia.length; i++){
			arr_instancia[i] = 1; //Estan todos en la izquierda, inclusive la linterna.
		}
		boolean linternaIzq = true;	//Sólo por motivos declarativos
		LinkedList<Persona> vacia = new LinkedList<Persona>();
		LinkedList<Persona> izq = (LinkedList<Persona>) iniciales.clone();	//Clono para no arruinar la lista original.
		
		TreeSet<Integer> instancias = new TreeSet<Integer>();
		instancias.add(sacarID(arr_instancia));			//Agrego a la raíz como instancia.
		
		int res = -1;

		
		if(tipo_estrategia == 0) res = auxResolver0(izq, vacia, tiempoMax, 0, linternaIzq, instancias, arr_instancia);
		else if(tipo_estrategia == 1) res = auxResolver1(izq, vacia, tiempoMax, 0, linternaIzq, instancias, arr_instancia);
		else if(tipo_estrategia == 2) res = auxResolver2(izq, vacia, tiempoMax, 0, linternaIzq, instancias, arr_instancia);
		
		return res;
	}

@SuppressWarnings("unchecked")
private int auxResolver0(LinkedList<Persona> izq, LinkedList<Persona> der, int tiempoOptimo, int tiempoActual, boolean linternaIzq, TreeSet<Integer> instancias, Integer[] instancia_actual){
		
		/*CON LA ESTRATEGIA 0 SIEMPRE EMPIEZO MANDANDO UNO DE LOS DOS LADOS, Y LUEGO DOS*/
		
		if(izq.size() == 0) return minimo(tiempoOptimo, tiempoActual);	//Si la lista izquierda vino vacía es porque llegué a una solución 

		else if(maximo(tiempoOptimo, tiempoActual) == tiempoActual) return tiempoOptimo;	//Si tiempoActual es mas grande que el optimo no sigo
		
		
		
		boolean mandarUno = true; //Mando uno sii estoy en la derecha o en la izquierda pero con un solo tipo.
		boolean termine = false;

		int i = 0;
		int j = 1;
		Persona persona1;
		Persona persona2;

		int tMAX;
		do{
			
			Integer[] aux_instanciaActual = instancia_actual.clone();					
			
			
			LinkedList<Persona> auxIzq = (LinkedList<Persona>) izq.clone();
			LinkedList<Persona> auxDer = (LinkedList<Persona>) der.clone();

			if(linternaIzq){	//Si linterna esta en izquierda sacó uno de la izquierda
				aux_instanciaActual[aux_instanciaActual.length-1] = 0;
				
				persona1 = izq.get(i);

				auxIzq.remove(i);
				aux_instanciaActual[persona1.dameID()] = 0;
				
				
				auxDer.add(persona1);
			}
			else { 	//Idem con derecha
				aux_instanciaActual[aux_instanciaActual.length-1] = 1;

				persona1 = der.get(i);

				auxDer.remove(i);
				aux_instanciaActual[persona1.dameID()] = 1;
				
				auxIzq.add(persona1);
			}

			tMAX = persona1.dameTiempo();

			if(mandarUno){	//Si tenía que mandar uno actualizo el índice y mas;
				i++;
				if(i == izq.size() && linternaIzq){
					mandarUno = false;
					i = 0;
					if(izq.size()==1) termine = true;
				}
				else if (i == der.size() && !linternaIzq){
					mandarUno = false;
					i = 0;
					if(der.size()==1) termine = true;
				}

			}

			else{		//Si tenía que mandar 2 tengo que sacar otro de donde esté la linterna y actualizar los índices.
				if(linternaIzq) {
					persona2 = izq.get(j);
					auxIzq.remove(j-1);
					aux_instanciaActual[persona2.dameID()] = 0;

					
					auxDer.add(persona2);
					
				}
				else {
					persona2 = der.get(j);
					auxDer.remove(j-1);
					aux_instanciaActual[persona2.dameID()] = 1;
					
					auxIzq.add(persona2);
				}
				j++;

				if((linternaIzq && j == izq.size()) || (!linternaIzq && j == der.size())){	//Si j llegó al final tengo que avanzar el i y seguir mandando.
					i++;
					j = i+1;
				}

				if(i==izq.size()-1 && linternaIzq){	//Si i llegó al final es porque terminé de mandar 2
					termine = true;

				}
				else if(i==der.size()-1 && !linternaIzq){
					termine = true;
				}

				tMAX = persona2.dameTiempo() > tMAX ? persona2.dameTiempo() : tMAX;

			}

			tiempoActual+= tMAX; //Sumo lo que cuesta este viaje.
			
			
			Integer instID = sacarID(aux_instanciaActual);			//calculo el ID de la nueva instancia				
			if(valido(auxIzq) && valido(auxDer) && !instancias.contains(instID)){	//Si la instancia nuva no es válida, o ya la repetí en esta rama, no sigo bajando.
			
				instancias.add(instID);	//Agrego la instancia "hijo" al conjunto 
				
				tiempoOptimo = auxResolver0( auxIzq, auxDer, tiempoOptimo, tiempoActual, !linternaIzq, instancias, aux_instanciaActual);
				
				instancias.remove(instID);	//Saco la instancia "hijo" anterior porque voy a bajar por otra rama.
			}
			tiempoActual-=tMAX;	//Arreglo el tiempoActual;

		}while(!termine);
		
		return tiempoOptimo;

	}


	@SuppressWarnings("unchecked")
	private int auxResolver1(LinkedList<Persona> izq, LinkedList<Persona> der, int tiempoOptimo, int tiempoActual, boolean linternaIzq, TreeSet<Integer> instancias, Integer[] instancia_actual){
		
		/*Con la ESTRATEGIA 1 siempre que estoy a la izquierda empiezo mandando de a dos y siempre que estoy a la derecha empiezo mandando de a uno*/
		
		if(izq.size() == 0) return minimo(tiempoOptimo, tiempoActual);	//Si la lista izquierda vino vacía es porque llegué a una solución 

		else if(maximo(tiempoOptimo, tiempoActual) == tiempoActual) return tiempoOptimo;	//Si tiempoActual es mas grande que el optimo no sigo
		
		
		
		boolean mandarUno = !linternaIzq || (linternaIzq && izq.size() == 1); //Mando uno sii estoy en la derecha o en la izquierda pero con un solo tipo.
		boolean termine = false;

		int i = 0;
		int j = 1;
		Persona persona1;
		Persona persona2;

		int tMAX;
		do{
			
			Integer[] aux_instanciaActual = instancia_actual.clone();					
			
			
			LinkedList<Persona> auxIzq = (LinkedList<Persona>) izq.clone();
			LinkedList<Persona> auxDer = (LinkedList<Persona>) der.clone();

			if(linternaIzq){	//Si linterna esta en izquierda sacó uno de la izquierda
				aux_instanciaActual[aux_instanciaActual.length-1] = 0;
				
				persona1 = izq.get(i);

				auxIzq.remove(i);
				aux_instanciaActual[persona1.dameID()] = 0;
				
				
				auxDer.add(persona1);
			}
			else { 	//Idem con derecha
				aux_instanciaActual[aux_instanciaActual.length-1] = 1;

				persona1 = der.get(i);

				auxDer.remove(i);
				aux_instanciaActual[persona1.dameID()] = 1;
				
				auxIzq.add(persona1);
			}

			tMAX = persona1.dameTiempo();

			if(mandarUno){	//Si tenía que mandar uno actualizo el índice y mas;
				i++;
				if(i == izq.size() && linternaIzq){
					termine = true;
				}
				else if (i == der.size() && !linternaIzq){
					mandarUno = false;
					i = 0;
					if(der.size()==1) termine = true;
				}

			}

			else{		//Si tenía que mandar 2 tengo que sacar otro de donde esté la linterna y actualizar los índices.
				if(linternaIzq) {
					persona2 = izq.get(j);
					auxIzq.remove(j-1);
					aux_instanciaActual[persona2.dameID()] = 0;

					
					auxDer.add(persona2);
					
				}
				else {
					persona2 = der.get(j);
					auxDer.remove(j-1);
					aux_instanciaActual[persona2.dameID()] = 1;
					
					auxIzq.add(persona2);
				}
				j++;

				if((linternaIzq && j == izq.size()) || (!linternaIzq && j == der.size())){	//Si j llegó al final tengo que avanzar el i y seguir mandando.
					i++;
					j = i+1;
				}

				if(i==izq.size()-1 && linternaIzq){	//Si i llegó al final es porque terminé de mandar 2
					mandarUno = true;
					i=0;
				}
				else if(i==der.size()-1 && !linternaIzq){
					termine = true;
				}

				tMAX = persona2.dameTiempo() > tMAX ? persona2.dameTiempo() : tMAX;

			}

			tiempoActual+= tMAX; //Sumo lo que cuesta este viaje.
			
			
			Integer instID = sacarID(aux_instanciaActual);			//calculo el ID de la nueva instancia				
			if(valido(auxIzq) && valido(auxDer) && !instancias.contains(instID)){	//Si la instancia nuva no es válida, o ya la repetí en esta rama, no sigo bajando.
			
				instancias.add(instID);	//Agrego la instancia "hijo" al conjunto 
				
				tiempoOptimo = auxResolver1( auxIzq, auxDer, tiempoOptimo, tiempoActual, !linternaIzq, instancias, aux_instanciaActual);
				
				instancias.remove(instID);	//Saco la instancia "hijo" anterior porque voy a bajar por otra rama.
			}
			tiempoActual-=tMAX;	//Arreglo el tiempoActual;

		}while(!termine);
		
		return tiempoOptimo;

	}

	
	@SuppressWarnings("unchecked")
	private int auxResolver2(LinkedList<Persona> izq, LinkedList<Persona> der, int tiempoOptimo, int tiempoActual, boolean linternaIzq, TreeSet<Integer> instancias, Integer[] instancia_actual){
		
		/* ESTRATEGIA 2 ES IGUAL A LA 1, SALVO QUE CUANDO MANDO UNA PERSONA, MANDO AL MINIMO */
		if(izq.size() == 0) return minimo(tiempoOptimo, tiempoActual);	//Si la lista izquierda vino vacía es porque llegué a una solución 

		else if(maximo(tiempoOptimo, tiempoActual) == tiempoActual) return tiempoOptimo;	//Si tiempoActual es mas grande que el optimo no sigo
		
		
		
		boolean mandarUno = !linternaIzq || (linternaIzq && izq.size() == 1); //Mando uno sii estoy en la derecha o en la izquierda pero con un solo tipo.
		boolean termine = false;

		int tipo_mas_rapido = 0;
		int i = 0;
		int j = 1;
		Persona persona1;
		Persona persona2;

		int tMAX;
		do{
			
			Integer[] aux_instanciaActual = instancia_actual.clone();					
			
			
			LinkedList<Persona> auxIzq = (LinkedList<Persona>) izq.clone();
			LinkedList<Persona> auxDer = (LinkedList<Persona>) der.clone();

			if(linternaIzq){	//Si linterna esta en izquierda sacó uno de la izquierda
				aux_instanciaActual[aux_instanciaActual.length-1] = 0;
				
				if(mandarUno){
					if(tipo_mas_rapido == 0) i = Dame_mas_veloz(izq, "canibal");
					else if(tipo_mas_rapido == 1) i = Dame_mas_veloz(izq, "arqueologo");
				}
				
				persona1 = izq.get(i);

				auxIzq.remove(i);
				aux_instanciaActual[persona1.dameID()] = 0;
				
				
				auxDer.add(persona1);
			}
			else { 	//Idem con derecha
				aux_instanciaActual[aux_instanciaActual.length-1] = 1;
				
				if(mandarUno){
					if(tipo_mas_rapido == 0) i = Dame_mas_veloz(der, "canibal");
					else if(tipo_mas_rapido == 1) i = Dame_mas_veloz(der, "arqueologo");
				}
				
				persona1 = der.get(i);

				auxDer.remove(i);
				aux_instanciaActual[persona1.dameID()] = 1;
				
				auxIzq.add(persona1);
			}

			tMAX = persona1.dameTiempo();

			if(mandarUno){	//Si tenía que mandar uno actualizo el índice y mas;
				tipo_mas_rapido++;
				if(tipo_mas_rapido == 2 && linternaIzq){
					termine = true;
				}
				else if (tipo_mas_rapido == 2 && !linternaIzq){
					mandarUno = false;
					i = 0;
					if(der.size()==1) termine = true;
				}

			}

			else{		//Si tenía que mandar 2 tengo que sacar otro de donde esté la linterna y actualizar los índices.
				if(linternaIzq) {
					persona2 = izq.get(j);
					auxIzq.remove(j-1);
					aux_instanciaActual[persona2.dameID()] = 0;

					
					auxDer.add(persona2);
					
				}
				else {
					persona2 = der.get(j);
					auxDer.remove(j-1);
					aux_instanciaActual[persona2.dameID()] = 1;
					
					auxIzq.add(persona2);
				}
				j++;

				if((linternaIzq && j == izq.size()) || (!linternaIzq && j == der.size())){	//Si j llegó al final tengo que avanzar el i y seguir mandando.
					i++;
					j = i+1;
				}

				if(i==izq.size()-1 && linternaIzq){	//Si i llegó al final es porque terminé de mandar 2
					mandarUno = true;
					i=0;
				}
				else if(i==der.size()-1 && !linternaIzq){
					termine = true;
				}

				tMAX = persona2.dameTiempo() > tMAX ? persona2.dameTiempo() : tMAX;

			}

			tiempoActual+= tMAX; //Sumo lo que cuesta este viaje.
			
			
			Integer instID = sacarID(aux_instanciaActual);			//calculo el ID de la nueva instancia				
			if(valido(auxIzq) && valido(auxDer) && !instancias.contains(instID)){	//Si la instancia nuva no es válida, o ya la repetí en esta rama, no sigo bajando.
			
				instancias.add(instID);	//Agrego la instancia "hijo" al conjunto 
				
				tiempoOptimo = auxResolver2( auxIzq, auxDer, tiempoOptimo, tiempoActual, !linternaIzq, instancias, aux_instanciaActual);
				
				instancias.remove(instID);	//Saco la instancia "hijo" anterior porque voy a bajar por otra rama.
			}
			tiempoActual-=tMAX;	//Arreglo el tiempoActual;

		}while(!termine);
		
		return tiempoOptimo;

	}
	
	private int Dame_mas_veloz(LinkedList<Persona> lista, String s) {
		int i = 0;
		Persona candidato = null;
		
		int k = 0;
		for(Persona p : lista){
			if (s == "canibal" && p.esCanibal()){
				if( candidato == null || candidato.dameTiempo() > p.dameTiempo() ){
					candidato = p;
					i = k;
				}
			}
			else if(s == "arqueologo" && !lista.get(k).esCanibal()){
				if( candidato == null || candidato.dameTiempo() > p.dameTiempo() ){
					candidato = p;
					i = k;
				}
			}
			k++;
		}
		
		
		return i;
	}

	private Integer sacarID(Integer[] arr_binario) {
		int acum = 0;
		int i = 0;
		for(Integer a : arr_binario){
			acum += a*Math.pow(2, i++);
		}
		return acum;
	}

	private boolean valido(LinkedList<Persona> grupo){
		int canibales = 0;
		int arqueologos = 0;
		for(Persona p : grupo ){
			if(p.esCanibal()) canibales++;
			else arqueologos++;
		}
		return (arqueologos >= canibales || canibales == grupo.size());
	}
	
	private int maximo(int a, int b){
		if(a== -1) return -1;
		else if(b==-1) return -1;
		else return Math.max(a,b);
	}
			
	private int minimo(int a, int b){
		if(maximo(a,b) == a) return b;
		else return a;
	}
			


}

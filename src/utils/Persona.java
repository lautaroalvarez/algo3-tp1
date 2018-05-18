package utils;

public class Persona {
		private boolean esCani;
		private int tiempo;
		int ident;
		public Persona(String type, int t){
			esCani = type.equals("C");
			tiempo = t;
		}
		
		public void setID(int n){
			ident = n;
		}
		
		public int dameTiempo(){
			return tiempo;
		}
		public boolean esCanibal(){
			return esCani;
		}
		public int dameID(){
			return ident;
		}
	

}

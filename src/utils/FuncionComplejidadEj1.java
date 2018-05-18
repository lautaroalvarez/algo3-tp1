package utils;

public class FuncionComplejidadEj1 {
	
	public long complejidadEj1(int n){
		return (long) Math.pow(n, 2*(exponente(n))+1);
	}
	
	private long factorial(int n){
		int producto = 1;
		while(n > 0){
			producto *= n;
			n--;
		}
		return producto;
	}
	
	private long combinatorio(int n, int m){
		return (factorial(n) /(factorial(n-m)*factorial(m)));
	}
	private long exponente(int n){
		int suma = 0;
		for(int i = 0; i <= n; i++){
			suma += combinatorio(n,i);
		}
		return 2*suma;
	}

}

import java.util.Scanner;


public class ConversorMoeda {

	public static void main(String[] args) {
	   Scanner entrada = new Scanner(System.in);
	   float cotacaoDolar, valorEmReal, valorEmDolar;
	   
	   	System.out.println("Qual a cota��o do d�lar hoje?: ");
	   	cotacaoDolar = entrada.nextFloat();
	   	
	   	System.out.println("Quanto deseja converter?: ");
	   	valorEmReal = entrada.nextFloat();
	   	
	   	valorEmDolar = valorEmReal / cotacaoDolar;
	   	
	   	System.out.println("O valor em d�lar ser�: " + valorEmDolar);

	}

}

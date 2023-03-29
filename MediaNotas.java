package aula_lp02;

import java.util.Scanner;

public class aulalp02 {

	public static void main(String[] args) {
		Scanner leitura = new Scanner (System.in);
	    double n1, n2, m, nf, m2;
		System.out.println("Digite uma nota: " );
	    n1 = leitura.nextDouble();
		System.out.println("Digite uma nota: ");
		n2 = leitura.nextDouble();
	
	    m = (n1+n2)/2;
		System.out.println("A sua média será: " + m);
		
		if (m >= 7) {
			System.out.println("Você está aprovado!");
		} else {
			System.out.println("Avaliação final");
	        System.out.println("Digite a nota da prova final:");
	        nf = leitura.nextDouble();
	        m2 = (n1 + n2 + nf) / 3;
	        System.out.println("Sua média é:" + m2);
	        if (m2 >= 5) {
	        	System.out.println("Você está aprovado!");
	        } else {
	        	System.out.println("Você está reprovado!");
	        }
	        
		}
		
		
		leitura.close();
		
		

	}

}

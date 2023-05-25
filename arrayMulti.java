package arrayMulti;


import java.util.Scanner;

public class arrayMulti {

	public static void main(String[] args) {
		
		int num [][] = {{1,2,3}, {6,7,8}, {9,10,11}};
		
		
		for(int i=0;i<num.length;i++) {
			for(int j=0; j<num.length;j++) {
			System.out.println("num["+i+"]["+j+"]="+num[i][j]);	
			}
		}
 
		

}
	
}

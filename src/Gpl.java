import java.util.Scanner;
import java.util.Stack;


public class Gpl {
	
	//Pilex et c0
	private Stack<Comparable> pilex = new Stack<Comparable>();
	private Stack<Integer> pcode = new Stack< Integer>();
	
	//Pointeurs de pile
	private int spx = 0;
	private int c0 = 0;
	
	//Variables temp
	private int valeurInteger = 0;
	private String valeurString = "";
	private Scanner sc = new Scanner(System.in);

	public void interpreter(int x){

		
		
		
		
		
		
		switch(x){
		//Inst de chargement
		case 1: //LDA
			//spx ++;
			pilex.push( pcode.get(c0 + 1) );
			c0 = c0 +2;
			
			break;
		case 2: //LDV
			//spx ++;
			pilex.push( pilex.get(pcode.get(c0+1)) );
			c0 = c0 +2;
			break;
		case 3://LDC
			//spx ++;
			pilex.push( pcode.get(c0 + 1) );
			c0 = c0 +2;
			break;
			
			//Inst de saut
		case 4 ://JMP @
			break;
		case 5 ://JIF @
			break;
		case  6://JSR @
			break;
		case  7://RSR @
			break;
			
			//Opérateurs relationnels
		case  8://SUP
			valeurInteger = 0;
			if((Integer)pilex.get(spx) > (Integer)pilex.get(spx-1)){
				valeurInteger = 1;
			}
			pilex.remove(spx);
			spx --;
			pilex.remove(spx);
			pilex.push(valeurInteger);
			c0 ++;
			break;
		case  9://SUPE
			valeurInteger = 0;
			if((Integer)pilex.get(spx) >= (Integer)pilex.get(spx-1)){
				valeurInteger = 1;
			}
			pilex.remove(spx);
			spx --;
			pilex.remove(spx);
			pilex.push(valeurInteger);
			c0 ++;
			break;
		case  10://INF
			valeurInteger = 0;
			if((Integer)pilex.get(spx) < (Integer)pilex.get(spx-1)){
				valeurInteger = 1;
			}
			pilex.remove(spx);
			spx --;
			pilex.remove(spx);
			pilex.push(valeurInteger);
			c0 ++;
			break;
		case  11://INFE
			valeurInteger = 0;
			if((Integer)pilex.get(spx) <= (Integer)pilex.get(spx-1)){
				valeurInteger = 1;
			}
			pilex.remove(spx);
			spx --;
			pilex.remove(spx);
			pilex.push(valeurInteger);
			c0 ++;
			break;
		case  12://EG
			valeurInteger = 0;
			if((Integer)pilex.get(spx) == (Integer)pilex.get(spx-1)){
				valeurInteger = 1;
			}
			pilex.remove(spx);
			spx --;
			pilex.remove(spx);
			pilex.push(valeurInteger);
			c0 ++;
			break;
		case  13://DIFF
			valeurInteger = 0;
			if((Integer)pilex.get(spx) != (Integer)pilex.get(spx-1)){
				valeurInteger = 1;
			}
			pilex.remove(spx);
			spx --;
			pilex.remove(spx);
			pilex.push(valeurInteger);
			c0 ++;
			break;
			
			//Inst pour entrée/sortie
		case 14: //RD
			spx ++;
			valeurString = sc.next();
			pilex.push(valeurString);
			c0 ++;
			break;
		case  15://RDCN
			break;
		case  16://WRT
			break;
		case  17://WRTLN
			break;
			
			//Opérateurs
		case  18://ADD
			valeurInteger = (Integer)pilex.get(spx) + (Integer)pilex.get(spx-1);
			pilex.remove(spx);
			spx --;
			pilex.remove(spx);
			pilex.push(valeurInteger);
			c0 ++;
			break;
		case  19://Moins
			valeurInteger = (Integer)pilex.get(spx) - (Integer)pilex.get(spx-1);
			pilex.remove(spx);
			spx --;
			pilex.remove(spx);
			pilex.push(valeurInteger);
			c0 ++;
			break;
		case  20://Div
			valeurInteger = (Integer)pilex.get(spx) / (Integer)pilex.get(spx-1);
			pilex.remove(spx);
			spx --;
			pilex.remove(spx);
			pilex.push(valeurInteger);
			c0 ++;
			break;
		case  21://Mult
			valeurInteger = (Integer)pilex.get(spx) * (Integer)pilex.get(spx-1);
			pilex.remove(spx);
			spx --;
			pilex.remove(spx);
			pilex.push(valeurInteger);
			c0 ++;
			break;
		case  22://Neq (-unaire -22)
			break;
		case  23://INC
			break;
		case  24://DEC
			break;
			
			//Opérateurs logiques
		case  25://AND
			valeurInteger = 0;
			if((Integer)pilex.get(spx) == 1 && (Integer)pilex.get(spx-1) == 1){
				valeurInteger = 1;
			}
			pilex.remove(spx);
			spx --;
			pilex.remove(spx);
			pilex.push(valeurInteger);
			c0 ++;
			break;
		case  26://OR
			valeurInteger = 0;
			if((Integer)pilex.get(spx) == 1 || (Integer)pilex.get(spx-1) == 1){
				valeurInteger = 1;
			}
			pilex.remove(spx);
			spx --;
			pilex.remove(spx);
			pilex.push(valeurInteger);
			c0 ++;
			break;
		case  27://NOT
			valeurInteger = 0;
			if((Integer)pilex.get(spx) != 1 && (Integer)pilex.get(spx-1) != 1){
				valeurInteger = 1;
			}
			pilex.remove(spx);
			spx --;
			pilex.remove(spx);
			pilex.push(valeurInteger);
			c0 ++;
			break;
		case 28://AFF
			valeurString = (String) pilex.get(spx);
			pilex.remove(spx);
			spx --;
			pilex.remove(spx);
			pilex.push(valeurString);
			c0++;
			break;
		case 29://STOP inst d'arret
			break;
		case 30://INDA adresse indexée
			break;
		case 31://INDV adresse valuée indexée
			break;
			
		}
		System.out.println("valeur: " + x);
		System.out.println("pilex : " + pilex.get(spx));
		System.out.println("pcode : " + pcode.get(c0));
		System.out.println("------------------------");
	}
	
	public void exec(){
		programSom();

		while(c0 < pcode.size()){
			interpreter(pcode.get(c0));
		}
	}
	
	public void programSom(){
		this.pcode.push(1);
		this.pcode.push(3);
		this.pcode.push(14);//RD
		this.pcode.push(28);
		this.pcode.push(1);
		this.pcode.push(2);
		this.pcode.push(3);
		this.pcode.push(0);
		this.pcode.push(28);
		this.pcode.push(1);
		this.pcode.push(1);
		this.pcode.push(3);
		this.pcode.push(0);
		this.pcode.push(28);
		this.pcode.push(2);
		this.pcode.push(1);
		this.pcode.push(2);
		this.pcode.push(3);
		this.pcode.push(11);//INFE
		this.pcode.push(5);
		this.pcode.push(38);
		this.pcode.push(1);
		this.pcode.push(2);
		this.pcode.push(2);
		this.pcode.push(2);
		this.pcode.push(2);
		this.pcode.push(1);
		this.pcode.push(18);//ADD S+I
		this.pcode.push(28);
		this.pcode.push(1);
		this.pcode.push(1);
		this.pcode.push(2);
		this.pcode.push(1);
		this.pcode.push(18);//ADD I+1
		this.pcode.push(28);
		this.pcode.push(4);
		this.pcode.push(15);
		this.pcode.push(2);
		this.pcode.push(2);
		this.pcode.push(17);//WRTLN
		this.pcode.push(29);
	}
	
	
}

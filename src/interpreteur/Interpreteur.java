package interpreteur;

import java.util.Scanner;
import java.util.Stack;



public class Interpreteur {
	
	//Pilex et c0
	private Stack<Integer> pilex = new Stack<Integer>();
	private Stack<Integer> pcode = new Stack< Integer>();
	
	//Pointeurs de pile
	private int spx = -1;
	private int c0 = 0;
	
	//Variables temporaires
	private Stack<Integer> tmp = new Stack<Integer>();
	private int adresse;
	private int valeur = 0;
	private Scanner sc = new Scanner(System.in);

	
	public Interpreteur(Stack<Integer> pcode, Stack<Integer> pilex) {
		this.pcode = pcode;
		this.pilex = pilex;
	}


	public void interpreter(int x){

		switch(x){
		//Inst de chargement
		case 1: //LDA
			spx ++;		
			adresse = pcode.get(c0 + 1);
			pilex.push( pcode.get(c0 + 1) );
			c0 = c0 +2;
			break;
		case 2: //LDV
			spx ++;
			pilex.push( pilex.get(pcode.get(c0+1)) );
			c0 = c0 +2;
			break;
		case 3://LDC
			spx ++;
			pilex.push( pcode.get(c0 + 1) );
			c0 = c0 +2;
			break;
			
			//Inst de saut
		case 4 ://JMP @
			c0 = pcode.get(c0+1);

			break;
		case 5 ://JIF @			
			if(pilex.get(spx) == 0){
				c0 = pcode.get(c0+1);
			}
			else{
				c0 = c0+2;
			}
			pilex.remove(spx);
			spx --;
			break;
		case  6://JSR @
			break;
		case  7://RSR @
			break;
			
			//Operateurs relationnels
		case  8://SUP
			valeur = 0;
			if((Integer)pilex.get(spx) > (Integer)pilex.get(spx-1)){
				valeur = 1;
			}
			pilex.remove(spx);
			spx --;
			pilex.remove(spx);
			pilex.push(valeur);
			c0 ++;
			break;
		case  9://SUPE
			valeur = 0;
			if((Integer)pilex.get(spx) >= (Integer)pilex.get(spx-1)){
				valeur = 1;
			}
			pilex.remove(spx);
			spx --;
			pilex.remove(spx);
			pilex.push(valeur);
			c0 ++;
			break;
		case  10://INF
			valeur = 0;
			if((Integer)pilex.get(spx) < (Integer)pilex.get(spx-1)){
				valeur = 1;
			}
			pilex.remove(spx);
			spx --;
			pilex.remove(spx);
			pilex.push(valeur);
			c0 ++;
			break;
		case  11://INFE
			valeur = 0;
			if(pilex.get(spx-1) <= pilex.get(spx)){
				valeur = 1;
			}
			pilex.remove(spx);
			spx --;
			pilex.remove(spx);
			pilex.push(valeur);
			c0 ++;
			break;
		case  12://EG
			valeur = 0;
			if((Integer)pilex.get(spx) == (Integer)pilex.get(spx-1)){
				valeur = 1;
			}
			pilex.remove(spx);
			spx --;
			pilex.remove(spx);
			pilex.push(valeur);
			c0 ++;
			break;
		case  13://DIFF
			valeur = 0;
			if((Integer)pilex.get(spx) != (Integer)pilex.get(spx-1)){
				valeur = 1;
			}
			pilex.remove(spx);
			spx --;
			pilex.remove(spx);
			pilex.push(valeur);
			c0 ++;
			break;
			
			//Inst pour entree/sortie
		case 14: //RD
			spx ++;
			valeur = sc.nextInt();
			pilex.push(valeur);
			c0 ++;
			break;
		case  15://RDCN
			break;
		case  16://WRT
			System.out.print(pilex.get(spx));
			pilex.pop();
			spx --;
			c0++;
			break;
		case  17://WRTLN
			System.out.println(pilex.get(spx));
			pilex.pop();
			spx--;
			c0++;
			break;
			
			//Operateurs
		case  18://ADD
			valeur = pilex.get(spx) + pilex.get(spx-1);
			pilex.remove(spx);
			spx --;
			pilex.remove(spx);
			pilex.push(valeur);
			c0 ++;
			break;
		case  19://Moins
			valeur = (Integer)pilex.get(spx) - (Integer)pilex.get(spx-1);
			pilex.remove(spx);
			spx --;
			pilex.remove(spx);
			pilex.push(valeur);
			c0 ++;
			break;
		case  20://Div
			valeur = (Integer)pilex.get(spx) / (Integer)pilex.get(spx-1);
			pilex.remove(spx);
			spx --;
			pilex.remove(spx);
			pilex.push(valeur);
			c0 ++;
			break;
		case  21://Mult
			valeur = (Integer)pilex.get(spx) * (Integer)pilex.get(spx-1);
			pilex.remove(spx);
			spx --;
			pilex.remove(spx);
			pilex.push(valeur);
			c0 ++;
			break;
		case  22://Neq (-unaire -22)
			break;
		case  23://INC
			break;
		case  24://DEC
			break;
			
			//Operateurs logiques
		case  25://AND
			valeur = 0;
			if((Integer)pilex.get(spx) == 1 && (Integer)pilex.get(spx-1) == 1){
				valeur = 1;
			}
			pilex.remove(spx);
			spx --;
			pilex.remove(spx);
			pilex.push(valeur);
			c0 ++;
			break;
		case  26://OR
			valeur = 0;
			if((Integer)pilex.get(spx) == 1 || (Integer)pilex.get(spx-1) == 1){
				valeur = 1;
			}
			pilex.remove(spx);
			spx --;
			pilex.remove(spx);
			pilex.push(valeur);
			c0 ++;
			break;
		case  27://NOT
			valeur = 0;
			if((Integer)pilex.get(spx) != 1 && (Integer)pilex.get(spx-1) != 1){
				valeur = 1;
			}
			pilex.remove(spx);
			spx --;
			pilex.remove(spx);
			pilex.push(valeur);
			c0 ++;
			break;
		case 28://AFF
			valeur =  pilex.pop();
			pilex.pop();
			spx = pilex.size()-1;

			//On empile dans tmp jusqu'� l'adresse charg�e
			for(int i=spx; i>adresse; i--){
				tmp.push(pilex.pop());				
			}
			
			//On met la bonne valeur � la place de l'ancienne
			pilex.pop();
			pilex.push(valeur);
			
			//On remet la pile comme avant sans l'ancienne valeur
			if(!tmp.isEmpty()){
				for(int i=0; i<tmp.size()+1; i++){
					pilex.push(tmp.pop());
				}
			}
			c0++;
			break;
		case 29://STOP inst d'arret
			c0 = pcode.size();
			break;
		case 30://INDA adresse indexee
			break;
		case 31://INDV adresse valuee indexee
			break;
			
		}
	}
	
	public void exec(){
		while(c0 < pcode.size()){
			interpreter(pcode.get(c0));
		}
		
		System.out.println("\nPilex : ");
		System.out.println(pilex);
		/*
		for(int i =pilex.size()-1; i>=0; i--){
			System.out.println("|"+pilex.get(i)+"|");
		}*/
	}

	public void programSom(){
		//VAR
		this.pcode.push(1);//LDA     0
		this.pcode.push(0);//0	
		this.pcode.push(1);//LDA
		this.pcode.push(1);//1	
		this.pcode.push(1);//LDA 
		this.pcode.push(2);//2       5
		
		this.pcode.push(1);//LDA     6
		this.pcode.push(2);//2
		this.pcode.push(14);//RD
		this.pcode.push(28);//AFF
		
		this.pcode.push(1);//LDA    10
		this.pcode.push(1);//1
		this.pcode.push(3);//LDC
		this.pcode.push(0);//0
		this.pcode.push(28);//AFF
		
		this.pcode.push(1);//LDA      15
		this.pcode.push(0);//0      
		this.pcode.push(3);//LDC
		this.pcode.push(0);//0
		this.pcode.push(28);//AFF
		
		this.pcode.push(2);//LDV    20
		this.pcode.push(0);//0      
		this.pcode.push(2);//LDV
		this.pcode.push(2);//2
		this.pcode.push(11);//INFE	
		
		this.pcode.push(5);//JIF    25 
		this.pcode.push(45);//45    
		
		this.pcode.push(1);//LDA S
		this.pcode.push(1);//1
		this.pcode.push(2);//LDV S
		this.pcode.push(1);//1      30
		this.pcode.push(2);//LDV I
		this.pcode.push(0);//0
		this.pcode.push(18);//ADD
		this.pcode.push(28);//AFF
		
		
		this.pcode.push(1);//LDA       35     
		this.pcode.push(0);//0         
		this.pcode.push(2);//LDV
		this.pcode.push(0);//0
		this.pcode.push(3);//LDC
		this.pcode.push(1);//1          40
		this.pcode.push(18);//ADD I+1
		this.pcode.push(28);//AFF
		
		this.pcode.push(4);//JMP
		this.pcode.push(20);//20
		
		this.pcode.push(2);//LDV       45
		this.pcode.push(1);//2
		this.pcode.push(17);//WRTLN

		this.pcode.push(29);//END       48
		
	}
	

	
}

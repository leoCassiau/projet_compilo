import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;


public class Gpl {
	
	private Stack pilex = new Stack();
	private Stack<Integer> pcode = new Stack< Integer>();


	public void interpreter(int x){
		int c0 = 0;
		int spx = 0;
		int valeur = 0;
		
		switch(x){
		//Inst de chargement
		case 1: //LDA
			spx ++;
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
			break;
		case 5 ://JIF @
			break;
		case  6://JSR @
			break;
		case  7://RSR @
			break;
			
			//Opérateurs relationnels
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
			if((Integer)pilex.get(spx) <= (Integer)pilex.get(spx-1)){
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
			
			//Inst pour entrée/sortie
		case 14: //RD
			spx ++;
			pilex.push( "read" );
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
			valeur = (Integer)pilex.get(spx) + (Integer)pilex.get(spx-1);
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
			
			//Opérateurs logiques
		case  25://AND
			valeur = 0;
			if((pilex.get(spx) == pcode.get(c0 -1) && pilex.get(spx-1) == pcode.get(c0 - 3)) || (pilex.get(spx) == pcode.get(c0 -3) && pilex.get(spx-1) == pcode.get(c0 -1))){
				valeur = 1;
			}
			pilex.remove(spx);
			spx --;
			pilex.remove(spx);
			pilex.push(valeur);
			c0 ++;
			break;
		case  26://OR
			break;
		case  27://NOT
			break;
			
			
		
		}
	}
	
	public void exec(){
		int c0 = 0;
		
		while(c0 < pcode.size()){
			interpreter(pcode.get(c0));
		}
	}
	
	
}

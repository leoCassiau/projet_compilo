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
		
		switch(x){
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
			break;
		case 14: //RD
			spx ++;
			pilex.push( "read" );
			c0 ++;
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

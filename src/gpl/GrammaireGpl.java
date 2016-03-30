package gpl;

import grammaireZero.GrammaireZero;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import core.Grammaire;
import core.Noeud;
import core.NoeudAtom;
import core.Token;

public class GrammaireGpl extends Grammaire {
	
	private Stack<Integer> pilex = new Stack<Integer>();
	private Stack<Integer> pcode = new Stack< Integer>();
	private List<String> itab = new ArrayList<String>();
	private Stack<Integer> jump = new Stack<Integer>();
	
	private String chaine = "";
	private int adresse = 0;
	
	
	public GrammaireGpl(String cheminGrammaire, Map<String, Noeud> arbreDependance) throws FileNotFoundException {
		super(cheminGrammaire);
		sc = new ScannerGPL(cheminGrammaire);
		this.arbreDependance = arbreDependance;
		token = this.nextToken();
	}
	
	public Stack<Integer> getPilex() {
		return pilex;
	}

	public void setPilex(Stack<Integer> pilex) {
		this.pilex = pilex;
	}

	public Stack<Integer> getPcode() {
		return pcode;
	}

	public void setPcode(Stack<Integer> pcode) {
		this.pcode = pcode;
	}

	public boolean analyse() {
		return analyseRec(arbreDependance.get("GplPrgm"));
	}
	
	public boolean analyseRec(Noeud p) {
		if (p.getCode().equals("conc")) {
			if (analyseRec(p.getGauche())) {
				return analyseRec(p.getDroit());
			}
		} else if (p.getCode().equals("union")) {
			if (analyseRec(p.getGauche())) {
				return true;
			} else {
				return analyseRec(p.getDroit());
			}
		} else if (p.getCode().equals("star")) {
			while (analyseRec(p.getGauche())) {
			}
			;
			return true;
		} else if (p.getCode().equals("un")) {
			analyseRec(p.getGauche());
			return true;
		} else if (p.getClass().equals(NoeudAtom.class)) {
			NoeudAtom pAtom = (NoeudAtom) p;
			if (pAtom.isTerminal()) {
				if (pAtom.getCode().equals(token)) {
					if (pAtom.getAction() != 0) {
						this.action(pAtom);
					}
					token = this.nextToken();
					return true;
				}
			} else { // p n'est pas terminal
				if (analyseRec(arbreDependance.get("Gpl" + pAtom.getCode()))) {
					if ((pAtom.getAction() != 0)) {
						this.action(pAtom);
					}
					return true;
				}
			}
		}
		return false;
	}
	
	public String nextToken() {
		// Check
		if(!sc.hasNext()) {
			return null;
		} 

		Token result = sc.nextToken();
		chaine = result.chaine;
		return result.noeud.getCode();
	}
	
	public void action(NoeudAtom p) {
		int action = p.getAction();

		switch(action) {
		case 1 : //LDA
			pcode.push(1);
			pcode.push(itab.indexOf(chaine));
			break;
		case 2 : //LDV
			pcode.push(2);
			pcode.push(itab.indexOf(chaine));
			break;
		case 3 : //LDC
			pcode.push(3);
			pcode.push(Integer.parseInt(chaine));
			break;
		case 4 : //JMP
			pcode.push(4);
			//pcode.push(14);
			pcode.push(-1);
			jump.push(pcode.size());
			break;
		case 5 : //JIF
			pcode.push(5);
			pcode.push(-1);
			//pcode.push(39);
			jump.push(adresse);
			break;
		case 8: //SUP
			pcode.push(8);
			break;
		case 9: //SUPE
			pcode.push(9);
			break;
		case 10: //INF
			pcode.push(10);
			break;
		case 11 : //INFE
			pcode.push(11); 
			break;
		case 12: //EG
			pcode.push(12);
			break;
		case 14 ://RD
			pcode.push(14); 
			break;
		case 17 ://WRTLN
			pcode.push(17); 
			break;
		case 18 : //ADD
			pcode.push(18); 
			break;
		case 28 : //AFF
			pcode.push(28);
			break;
		case 29 : //END
			pcode.push(29);
			
			Stack<Integer> tmp = new Stack< Integer>();
			int valeur = 0;
			int nombre = 0;
			//On remplace tous les -1 par les bonnes adresses
			while(!pcode.isEmpty()){
				valeur = pcode.pop();
				if(valeur == -1){
					valeur = jump.get(nombre);
					nombre++;
				}
				tmp.push(valeur);
			}
			//On remet tmp dans pcode
			while(!tmp.isEmpty()){
				pcode.push(tmp.pop());
			}
			break;
		case 100 :
			pilex.push(pilex.size()); // Var
			itab.add(chaine);
			break;
		case 101 :
			int tmpValeur = pcode.pop();
			int tmpType = pcode.pop();
			int tmpCondition = pcode.pop();
			pcode.push(tmpType);
			pcode.push(tmpValeur);
			pcode.push(tmpCondition);
			break;
		case 102:
			adresse = pcode.size();
			break;
		
			
		}
	}
	
	public static void main(String args[]) throws FileNotFoundException {
		Grammaire gzero = new GrammaireZero("grammaireGpl.txt");
		System.out.println("Analyse grammaireGpl.txt = " + gzero.analyse());
		
		GrammaireGpl gpl = new GrammaireGpl("prgmSom.txt", gzero.getArbreDependance());
		System.out.println("Analyse prgm.txt = " + gpl.analyse());
		System.out.println("\nPCode : \n" + gpl.getPcode());
		System.out.println("\nPilex : \n" + gpl.getPilex());
	}
}

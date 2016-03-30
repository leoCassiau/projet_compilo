package grammaires;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import scanners.ScannerGPL;
import scanners.Token;

public class GrammaireGpl extends Grammaire {
	
	private Stack<Integer> pilex = new Stack<Integer>();
	private Stack<Integer> pcode = new Stack< Integer>();
	private List<String> itab = new ArrayList<String>();
	private String chaine = "";
	
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
			//System.out.println("Token = " + token);
			//System.out.println("p.code = " + p.getCode());
			if (pAtom.isTerminal()) {
				if (pAtom.getCode().equals(token)) {
					if (pAtom.getAction() != 0) {
						this.action(pAtom);
					}
					token = this.nextToken();
					return true;
				}
			} else { // p n'est pas terminal
				//System.out.println("GPL = Gpl" + pAtom.getCode());
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
		case 1 :
			pcode.push(1); //LDA
			pcode.push(itab.indexOf(chaine));
			break;
		case 2 :
			pcode.push(2); // LDV
			pcode.push(itab.indexOf(chaine));
			break;
		case 3 :
			pcode.push(3); // LDC
			pcode.push(Integer.parseInt(chaine));
			break;
		case 4 :
			pcode.push(4); // JMP
			pcode.push(14); // TODO
			break;
		case 5 :
			pcode.push(5); // JIF
			pcode.push(39); // TODO
			break;
		case 11 :
			pcode.push(11); //INFE
			break;
		case 14 :
			pcode.push(14); // RD
			break;
		case 17 : 
			pcode.push(17); // WRTLN
			break;
		case 18 : 
			pcode.push(18); // ADD
			break;
		case 28 :
			pcode.push(28); // AFF
			break;
		case 29 :
			pcode.push(29); // END
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
		}
	}
	
	public static void main(String args[]) throws FileNotFoundException {
		Grammaire gzero = new GrammaireZero("grammaireGpl.txt");
		System.out.println("Analyse grammaireGpl.txt = " + gzero.analyse());
		
		GrammaireGpl gpl = new GrammaireGpl("prgmSom.txt", gzero.getArbreDependance());
		System.out.println("Analyse prgm.txt = " + gpl.analyse());
		System.out.println("PCode : " + gpl.getPcode());
		System.out.println("Pilex : " + gpl.getPilex());
	}
}

package grammaires;

import java.io.FileNotFoundException;
import java.util.Map;

import scanners.ScannerGPL;
import scanners.Token;

public class GrammaireGpl extends Grammaire {
	
	public GrammaireGpl(String cheminGrammaire, Map<String, Noeud> arbreDependance) throws FileNotFoundException {
		super(cheminGrammaire);
		sc = new ScannerGPL(cheminGrammaire);
		this.arbreDependance = arbreDependance;
		token = this.nextToken();
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
		//TODO
		
		return result.noeud.getCode();
	}
	
	public void action(NoeudAtom p) {
		//TODO
	}
	
	public static void main(String args[]) throws FileNotFoundException {
		Grammaire gzero = new GrammaireZero("grammaireGpl.txt");
		System.out.println("Analyse grammaireGpl.txt = " + gzero.analyse());
		
		Grammaire gpl = new GrammaireGpl("prgmSom.txt", gzero.getArbreDependance());
		System.out.println("Analyse prgm.txt = " + gpl.analyse());

	}
}

package grammaires;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import scanners.ScannerGZero;
import scanners.ScannerToken;

public abstract class Grammaire {

	protected Map<String, Noeud> arbreDependance = new HashMap<String, Noeud>(); // Arbre de dépendance de la grammaire
	protected ScannerToken sc; // Scanner parcourant le fichier contenant la grammaire à analyser
	protected String token; // Token actuel, récupéré par le scanner

	public Grammaire(String cheminGrammaire) throws FileNotFoundException {
		sc = new ScannerGZero(cheminGrammaire);
	}
	
	protected abstract String nextToken();
	
	public abstract void action(NoeudAtom pAtom);

	//--------------------------------getters & setters--------------------------------
	public Map<String, Noeud> getArbreDependance() {
		return arbreDependance;
	}

	public void setArbreDependance(Map<String, Noeud> arbreDependance) {
		this.arbreDependance = arbreDependance;
	}
	
	// -------------------------------------imprimArbre----------------------------------------
	public void imprimArbre(Noeud p) {
		imprimArbreRec(p, 0);
	}

	protected void imprimArbreRec(Noeud p, int profondeur) {
		profondeur++;
		for (int k = 1; k <= profondeur; k++) {
			System.out.print("---");
		}
		if (p.getCode().equals("conc")) {
			System.out.println(">conc");
			imprimArbreRec(p.getGauche(), profondeur);
			imprimArbreRec(p.getDroit(), profondeur);
		} else if (p.getCode().equals("star")) {
			System.out.println(">star");
			imprimArbreRec(p.getGauche(), profondeur);
		} else if (p.getCode().equals("un")) {
			System.out.println(">un");
			imprimArbreRec(p.getGauche(), profondeur);
		} else if (p.getCode().equals("union")) {
			System.out.println(">union");
			imprimArbreRec(p.getGauche(), profondeur);
			imprimArbreRec(p.getDroit(), profondeur);
		} else if (p.getClass().equals(NoeudAtom.class)) {
			NoeudAtom atom = (NoeudAtom) p;
			if (atom.isTerminal()) {
				System.out.println(">('" + atom.getCode() + "', " + atom.getAction() + ", " + atom.isTerminal() + ")");
			} else {
				System.out.println(">(" + atom.getCode() + ", " + atom.getAction() + ", " + atom.isTerminal() + ")");
			}
		}

	}
	
	// ----------------------------------------analyseur---------------------------------	
	public boolean analyse() {
		return analyseRec(arbreDependance.get("S"));
	}
	
	protected boolean analyseRec(Noeud p) {
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
				if (analyseRec(arbreDependance.get(pAtom.getCode()))) {
					if ((pAtom.getAction() != 0)) {
						this.action(pAtom);
					}
					return true;
				}
			}
		}
		return false;
	}

}

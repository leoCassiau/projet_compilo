
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class GrammaireZero {

	private Map<String, Noeud> arbreDependance = new HashMap<String, Noeud>(); // Arbre de dépendance de la grammaire zéro
	private ScannerToken sc; // Scanner parcourant le fichier contenant la grammaire à analyseRecr
	private NoeudAtom token; // Token actuel, récupéré par le scanner
	private Stack<Noeud> actions = new Stack<Noeud>(); // Pile contenant les actions de la grammaire analysé
	private List<NoeudAtom> dicot = new ArrayList<NoeudAtom>(); // Tableau contenant les éléments terminaux de la grammaire analysé
	private List<NoeudAtom> dicont = new ArrayList<NoeudAtom>(); // Tableau contenant les éléments non terminaux de la grammaire analysé

	public GrammaireZero(String cheminGrammaire) throws FileNotFoundException {
		sc = new ScannerToken(cheminGrammaire);
		token = this.nextToken();
		arbreDependance.put("S", creerArbreS());
		arbreDependance.put("N", creerArbreN());
		arbreDependance.put("E", creerArbreE());
		arbreDependance.put("T", creerArbreT());
		arbreDependance.put("F", creerArbreF());
	}

	//--------------------------------getters & setters--------------------------------
	public Map<String, Noeud> getArbreDependance() {
		return arbreDependance;
	}

	public void setArbreDependance(Map<String, Noeud> arbreDependance) {
		this.arbreDependance = arbreDependance;
	}

	// --------------------------------Fonctions gen--------------------------------------
	public Noeud genConc(Noeud p1, Noeud p2) {
		return new Noeud(p1, p2, "conc");
	}

	public Noeud genStar(Noeud p) {
		return new Noeud(p, "star");
	}

	public Noeud genUnion(Noeud p1, Noeud p2) {
		return new Noeud(p1, p2, "union");
	}

	public Noeud genUn(Noeud p) {
		return new Noeud(p, "un");
	}

	public NoeudAtom genAtom(String code, int action, boolean terminal) {
		return new NoeudAtom(code, "", action, terminal);
	}

	// ------------------------------Fonctions creerArbre----------------------------------
	public Noeud creerArbreS() {
		return genConc(genStar(
				genConc(genConc(genConc(genAtom("N", 0, false), genAtom("->", 0, true)), genAtom("E", 0, false)),
						genAtom(",", 1, true))),
				genAtom(";", 0, true));
	}

	public Noeud creerArbreN() {
		return genAtom("IDNTER", 2, true);
	}

	public Noeud creerArbreE() {
		return genConc(genAtom("T", 0, false), genStar(genConc(genAtom("+", 0, true), genAtom("T", 3, false))));
	}

	public Noeud creerArbreT() {
		return genConc(genAtom("F", 0, false), genStar(genConc(genAtom(".", 0, true), genAtom("F", 4, false))));
	}

	public Noeud creerArbreF() {
		return genUnion(
				genAtom("IDNTER", 5, true), 
				genUnion(
						genAtom("ELTER", 5, true),
						genUnion(
								genConc(
										genAtom("(", 0, true), 
										genConc(
												genAtom("E", 0, false), 
												genAtom(")", 0, true))),
								genUnion(
										genConc(
												genAtom("[", 0, true),
												genConc(
														genAtom("E", 0, false), 
														genAtom("]", 6, true))),
										genConc(
												genAtom("(/", 0, true), 
												genConc(
														genAtom("E", 0, false), 
														genAtom("/)", 7, true)
														)
												)
										)
								)
						)
				);
	}

	// -------------------------------------imprimArbre----------------------------------------
	public void imprimArbre(Noeud p) {
		imprimArbreRec(p, 0);
	}

	private void imprimArbreRec(Noeud p, int profondeur) {
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

	// ----------------------------------------analyseRecur---------------------------------
	public NoeudAtom nextToken() {
		// Check
		if(!sc.hasNext()) {
			return null;
		} 

		NoeudAtom result = sc.nextToken();
		if(result.isTerminal()) {
			dicot.add(result);
		} else {
			dicont.add(result);
		}

		return result;
	}
	
	public boolean analyse() {
		return analyseRec(arbreDependance.get("S"));
	}
	
	private boolean analyseRec(Noeud p) {
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
				if (pAtom.getCode().equals(token.getCode())) {
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

	// ----------------------------------------Actions----------------------------------------
	public NoeudAtom recherche(List<NoeudAtom> dictionnaire) {
		return dictionnaire.get(dictionnaire.size()-1);
	}

	public void action(NoeudAtom pAtom){
		Noeud t1,t2;
		NoeudAtom t;
		int action = pAtom.getAction();
		boolean CAType = !pAtom.getCode().equals("IDNTER");

		switch(action){
		case 1:
			t1 = actions.pop();
			t2 = actions.pop();
			arbreDependance.put("Gpl" + t2.getCode(), t1);
			break;
		case 2 :
			t = this.recherche(dicont);
			actions.push(genAtom(t.getChaine(), t.getAction(), CAType));
			break;
		case 3 : 
			t1 = actions.pop();
			t2 = actions.pop();
			actions.push(genUnion(t2,t1));
			break;
		case 4 :
			t1 = actions.pop();
			t2 = actions.pop();
			actions.push(genConc(t2,t1));
			break;
		case 5 :
			if(CAType) {
				t = this.recherche(dicot);
				actions.push(genAtom(t.getChaine(), t.getAction(), true));
			} else {
				t = this.recherche(dicont);
				actions.push(genAtom(t.getChaine(), t.getAction(), false));
			}
			break;
		case 6 :
			t1 = actions.pop();
			actions.push(genStar(t1));
			break;
		case 7 :
			t1 = actions.pop();
			actions.push(genUn(t1));
			break;
		}
	}

	// ----------------------------------------Main----------------------------------------
	public static void main(String[] args) throws FileNotFoundException {
		//GrammaireZero g = new GrammaireZero("grammaireZero.txt");
		GrammaireZero g = new GrammaireZero("grammaireTest.txt");
		
		//g.imprimArbre(g.getArbreDependance().get("S"));
		//g.imprimArbre(g.getArbreDependance().get("N"));
		//g.imprimArbre(g.getArbreDependance().get("E"));
		//g.imprimArbre(g.getArbreDependance().get("T"));
		//g.imprimArbre(g.getArbreDependance().get("F"));

		System.out.println("Analyse = " + g.analyse());
		System.out.println("Clefs de l'arbre de dépendance : " + g.getArbreDependance().keySet());
		
		//g.imprimArbre(g.getArbreDependance().get("GplS"));
		//g.imprimArbre(g.getArbreDependance().get("GplN"));
		//g.imprimArbre(g.getArbreDependance().get("GplE"));
		//g.imprimArbre(g.getArbreDependance().get("GplT"));
		//g.imprimArbre(g.getArbreDependance().get("GplF"));
		
		g.imprimArbre(g.getArbreDependance().get("GplS0"));
	}
}

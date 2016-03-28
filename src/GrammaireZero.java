
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class GrammaireZero {

	private Map<String, Noeud> arbreDependance = new HashMap<String, Noeud>(); // Arbre de dépendance de la grammaire zéro
	private ScannerToken sc; // Scanner parcourant le fichier contenant la grammaire à analyser
	private NoeudAtom token; // Token actuel, récupéré par le scanner
	private Stack<Noeud> actions = new Stack<Noeud>(); // Pile contenant les actions de la grammaire analysé
	//private List<String> dicot = new ArrayList<String>(); // Tableau contenant les éléments terminaux de la grammaire analysé
	//private List<String> dicont = new ArrayList<String>(); // Tableau contenant les éléments non terminaux de la grammaire analysé

	public GrammaireZero(String cheminGrammaire) throws FileNotFoundException {
		sc = new ScannerToken(cheminGrammaire);
		token = sc.nextToken();
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
	
	public Stack<Noeud> getActions() {
		return actions;
	}

	public void setActions(Stack<Noeud> actions) {
		this.actions = actions;
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
						genAtom(",", 0, true))),
				genAtom(";", 0, true));
	}

	public Noeud creerArbreN() {
		return genAtom("IDNTER", 0, true);
	}

	public Noeud creerArbreE() {
		return genConc(genAtom("T", 0, false), genStar(genConc(genAtom("+", 0, true), genAtom("T", 0, false))));
	}

	public Noeud creerArbreT() {
		return genConc(genAtom("F", 0, false), genStar(genConc(genAtom(".", 0, true), genAtom("F", 0, false))));
	}

	public Noeud creerArbreF() {
		return genUnion(genAtom("IDNTER", 0, true), genUnion(genAtom("ELTER", 0, true),
				genUnion(genConc(genAtom("(", 0, true), genConc(genAtom("E", 0, false), genAtom(")", 0, true))),
						genUnion(genConc(genAtom("[", 0, true),
								genConc(genAtom("E", 0, false), genAtom("]", 0, true))),
								genConc(genAtom("(/", 0, true), genConc(genAtom("E", 0, false), genAtom("/)", 0, true))))

						)));
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

	// ----------------------------------------Analyseur---------------------------------
	public boolean analyse(Noeud p) {
		if (p.getCode().equals("conc")) {
			if (analyse(p.getGauche())) {
				return analyse(p.getDroit());
			}
		} else if (p.getCode().equals("union")) {
			if (analyse(p.getGauche())) {
				return true;
			} else {
				return analyse(p.getDroit());
			}
		} else if (p.getCode().equals("star")) {
			while (analyse(p.getGauche())) {
			}
			;
			return true;
		} else if (p.getCode().equals("un")) {
			analyse(p.getGauche());
			return true;
		} else if (p.getClass().equals(NoeudAtom.class)) {
			NoeudAtom pAtom = (NoeudAtom) p;
			if (pAtom.isTerminal()) {
				if (pAtom.getCode().equals(token.getCode())) {
					if (pAtom.getAction() != 0) {
						this.action(token);
					}
					if (sc.hasNext()) {
						token = sc.nextToken();
					}
					return true;
				}
			} else { // p n'est pas terminal
				if (analyse(arbreDependance.get(pAtom.getCode()))) {
					if ((pAtom.getAction() != 0)) {
						this.action(token);
					}
					return true;
				}
			}
		}
		return false;
	}

	// ----------------------------------------Actions----------------------------------------
	public void action(NoeudAtom pAction){
		System.out.println("Test");
		Noeud t1,t2;

		switch(pAction.getAction()){
		case 1:
			t1 = actions.pop();
			NoeudAtom t2atom = (NoeudAtom) actions.pop();
			arbreDependance.put("GPL" + t2atom.getChaine(), t1);
			break;
		case 2 :
			actions.push(genAtom(pAction.getChaine(), 0, pAction.isTerminal()));
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
			if(pAction.isTerminal()) {
				actions.push(genAtom(pAction.getChaine(), 0, true));
			} else {
				actions.push(genAtom(pAction.getChaine(), 0, false));
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
		GrammaireZero c = new GrammaireZero("grammaireZero.txt");
		// c.imprimArbre(c.getArbreDependance().get("S"));
		// c.imprimArbre(c.getArbreDependance().get("N"));
		// c.imprimArbre(c.getArbreDependance().get("E"));
		// c.imprimArbre(c.getArbreDependance().get("T"));
		// c.imprimArbre(c.getArbreDependance().get("F"));

		System.out.println(c.analyse(c.getArbreDependance().get("S")));
		System.out.println(c.getActions());
	}
}

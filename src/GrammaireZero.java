
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class GrammaireZero {

	private Map<String, Noeud> arbreDependances = new HashMap<String, Noeud>(); // Arbre de dépendance de la grammaire zéro
	private ScannerToken sc; // Scanner parcourant le fichier contenant la grammaire à analyseRecr
	private NoeudAtom token; // Token actuel, récupéré par le scanner
	private Stack<Noeud> actions = new Stack<Noeud>(); // Pile contenant les actions de la grammaire analysé
	private List<NoeudAtom> dicot = new ArrayList<NoeudAtom>(); // Tableau contenant les éléments terminaux de la grammaire analysé
	private List<NoeudAtom> dicont = new ArrayList<NoeudAtom>(); // Tableau contenant les éléments non terminaux de la grammaire analysé

	/**
	 * Constructeur de GrammaireZero
	 * @param cheminGrammaire Le chemin de la grammaire à analyser
	 * @throws FileNotFoundException Levé si le chemin indiqué ne contient pas de fichier
	 */
	public GrammaireZero(String cheminGrammaire) throws FileNotFoundException {
		// 
		sc = new ScannerToken(cheminGrammaire);
		token = this.nextToken();
		
		// GenForet() : génère les arbres de dépendances de la grammaire zéro
		arbreDependances.put("S", creerArbreS());
		arbreDependances.put("N", creerArbreN());
		arbreDependances.put("E", creerArbreE());
		arbreDependances.put("T", creerArbreT());
		arbreDependances.put("F", creerArbreF());
	}

	/**
	 * Accesseur de l'attribut arbreDependance
	 * @return L'arbre des dépendances de la grammaire analysé
	 */
	public Map<String, Noeud> getArbreDependance() {
		return arbreDependances;
	}

	// --------------------------------Fonctions gen--------------------------------------
	/**
	 * Génère un noeud de concaténation entre p1 et p2
	 * @param p1 Noeud à gauche
	 * @param p2 Noeud à droite
	 * @return Noeud ayant pour code "conc", pour fils gauche p1 et pour fils droit p2
	 */
	public Noeud genConc(Noeud p1, Noeud p2) {
		return new Noeud(p1, p2, "conc");
	}

	/**
	 * Génère un noeud étoile avec pour fils p
	 * @param p Noeud fils
	 * @return Noeud ayant pour code "star" et pour fils gauche p
	 */
	public Noeud genStar(Noeud p) {
		return new Noeud(p, "star");
	}

	/**
	 * Génère un noeud d'union entre p1 et p2
	 * @param p1 Noeud gauche
	 * @param p2 Noeud droit
	 * @return Noeud ayant pour code "union", pour fils gauche p1 et pour fils droit p2
	 */
	public Noeud genUnion(Noeud p1, Noeud p2) {
		return new Noeud(p1, p2, "union");
	}

	/**
	 * Génère un noeud d'unicité avec pour fils p
	 * @param p Noeud fils
	 * @return Noeud ayant pour code "un" et pour fils p
	 */
	public Noeud genUn(Noeud p) {
		return new Noeud(p, "un");
	}

	/**
	 * Génère un noeud atomique avec son code, son action et son type
	 * @param code Code du noeud généré
	 * @param action Action du noeud généré
	 * @param terminal Type du noeud généré
	 * @return Noeud atomique avec pour chaine "", et le code, l'action et le type indiqué en entrée
	 */
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
		return analyseRec(arbreDependances.get("S"));
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
				if (analyseRec(arbreDependances.get(pAtom.getCode()))) {
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
			arbreDependances.put("Gpl" + t2.getCode(), t1);
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
		GrammaireZero g = new GrammaireZero("grammaireZero.txt");
		//GrammaireZero g = new GrammaireZero("grammaireTest.txt");
		
		//g.imprimArbre(g.getArbreDependance().get("S"));
		//g.imprimArbre(g.getArbreDependance().get("N"));
		//g.imprimArbre(g.getArbreDependance().get("E"));
		//g.imprimArbre(g.getArbreDependance().get("T"));
		//g.imprimArbre(g.getArbreDependance().get("F"));

		System.out.println("Analyse = " + g.analyse());
		System.out.println("Clefs de l'arbre de dépendance : " + g.getArbreDependance().keySet());
		
		g.imprimArbre(g.getArbreDependance().get("GplS"));
		//g.imprimArbre(g.getArbreDependance().get("GplN"));
		//g.imprimArbre(g.getArbreDependance().get("GplE"));
		//g.imprimArbre(g.getArbreDependance().get("GplT"));
		//g.imprimArbre(g.getArbreDependance().get("GplF"));
		
		//g.imprimArbre(g.getArbreDependance().get("GplS0"));
	}
}

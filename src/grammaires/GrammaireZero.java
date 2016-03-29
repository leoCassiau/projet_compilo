package grammaires;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import scanners.Token;

public class GrammaireZero extends Grammaire {

	private Stack<Noeud> actions = new Stack<Noeud>(); // Pile contenant les actions de la grammaire analysé
	private List<Token> dicot = new ArrayList<Token>(); // Tableau contenant les éléments terminaux de la grammaire analysé
	private List<Token> dicont = new ArrayList<Token>(); // Tableau contenant les éléments non terminaux de la grammaire analysé

	/**
	 * Constructeur de GrammaireZero
	 * @param cheminGrammaire Le chemin de la grammaire à analyser
	 * @throws FileNotFoundException Levé si le chemin indiqué ne contient pas de fichier
	 */
	public GrammaireZero(String cheminGrammaire) throws FileNotFoundException {
		super(cheminGrammaire);
		arbreDependance.put("S", creerArbreS());
		arbreDependance.put("N", creerArbreN());
		arbreDependance.put("E", creerArbreE());
		arbreDependance.put("T", creerArbreT());
		arbreDependance.put("F", creerArbreF());
		token = this.nextToken();
	}
	
	public String nextToken() {
		// Check
		if(!sc.hasNext()) {
			return null;
		} 

		Token result = sc.nextToken();
		if(result.noeud.isTerminal()) {
			dicot.add(result);
		} else {
			dicont.add(result);
		}

		return result.noeud.getCode();
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
		return new NoeudAtom(code, action, terminal);
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

	// ----------------------------------------Actions----------------------------------------
	public Token recherche(List<Token> dictionnaire) {
		return dictionnaire.get(dictionnaire.size()-1);
	}

	public void action(NoeudAtom pAtom){
		Noeud t1,t2;
		Token t;
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
			actions.push(genAtom(t.chaine, t.noeud.getAction(), CAType));
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
				actions.push(genAtom(t.chaine, t.noeud.getAction(), true));
			} else {
				t = this.recherche(dicont);
				actions.push(genAtom(t.chaine, t.noeud.getAction(), false));
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
		Grammaire g = new GrammaireZero("grammaireZero.txt");
		//Grammaire g = new GrammaireZero("grammaireTest.txt");
		
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

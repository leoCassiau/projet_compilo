package scanners;

import grammaires.NoeudAtom;

public class Token {

	public String chaine;
	public NoeudAtom noeud;
	
	public Token(String chaine, NoeudAtom noeud) {
		this.chaine = chaine;
		this.noeud = noeud;
	}
	
	public String toString() {
		return "Chaine = " + chaine + " | Noeud = " + noeud.toString();
	}
}

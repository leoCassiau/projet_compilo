package grammaires;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import scanners.ScannerGPL;
import scanners.Token;

public class GrammaireGpl extends Grammaire {

	private Stack<Integer> p_code = new Stack<Integer>(); // Pile contenant les actions du programme analysé
	
	public GrammaireGpl(String cheminGrammaire, Map<String, Noeud> arbreDependance) throws FileNotFoundException {
		super(cheminGrammaire);
		this.arbreDependance = arbreDependance;
		token = this.nextToken();
	}
	
	public String nextToken() {
		// Check
		if(!sc.hasNext()) {
			return null;
		} 

		Token result = sc.nextToken();

		return result.noeud.getCode();
	}
	
	public void action(NoeudAtom p) {
		Noeud t1,t2;
		NoeudAtom t;
		String type = p.getCode();
		//String chaine = p.();

		switch(2){

		}
	}
}

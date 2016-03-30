package grammaireZero;

import java.io.FileNotFoundException;

import core.NoeudAtom;
import core.ScannerToken;
import core.Token;

public class ScannerGZero extends ScannerToken {

	public ScannerGZero(String cheminFichier) throws FileNotFoundException {
		super(cheminFichier);
	}

	public Token nextToken() {
		String chaineToken = sc.next();
		int action = 0;
				
		// Element terminal ?
		if (chaineToken.startsWith("'")) {
			// Supprime les ''
			chaineToken = chaineToken.substring(1, chaineToken.length() - 1 );		
			
			// Action ?
			int indexDebutAction;
			if ((indexDebutAction = chaineToken.indexOf("#")) >= 0) {
				String actionString = chaineToken.substring(indexDebutAction + 1 );
				action = Integer.parseInt(actionString);
				chaineToken = chaineToken.substring(0, indexDebutAction);
			}
			
			return new Token(chaineToken, new NoeudAtom("ELTER", action, true));
		}

		// Action ?
		int indexDebutAction;
		if ((indexDebutAction = chaineToken.indexOf("#")) >= 0) {
			String actionString = chaineToken.substring(indexDebutAction + 1 );
			action = Integer.parseInt(actionString);
			chaineToken = chaineToken.substring(0, indexDebutAction);
		}
		
		// Element de la grammaire zero ?
		if(chaineToken.equals(".") || chaineToken.equals("+") ||
				chaineToken.equals("(/") || chaineToken.equals("/)") ||
				chaineToken.equals("(") || chaineToken.equals(")") ||
				chaineToken.equals("[") || chaineToken.equals("]") ||
				chaineToken.equals(",") || chaineToken.equals(";") || 
				chaineToken.equals("->")) {
			return new Token(chaineToken, new NoeudAtom(chaineToken, action, true));
		}
		
		// Element non terminal
		return new Token(chaineToken, new NoeudAtom("IDNTER", action, false));
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		ScannerGZero sc = new ScannerGZero("grammaireZero.txt");
		while (sc.hasNext()) {
			System.out.println(sc.nextToken());
		}
	}
}

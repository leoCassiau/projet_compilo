package scanners;

import grammaires.NoeudAtom;

import java.io.FileNotFoundException;


public class ScannerGPL extends ScannerToken {

	public ScannerGPL(String cheminFichier) throws FileNotFoundException {
		super(cheminFichier);
	}

	public Token nextToken() {
		String token = sc.next();

		// Entier ?
		try {
			Integer.parseInt(token);
			return new Token(token, new NoeudAtom("ent", 0, true));
		}
		// Pas entier !
		catch(NumberFormatException e) {
			// Symbole ?
			if(token.equals(":=") || token.equals("==") ||
					token.equals("<=") || token.equals(">=)") ||
					token.equals("<") || token.equals(">") ||
					token.equals("(") || token.equals(")") ||
					token.equals(",") || token.equals(";") ||
					token.equals(".") || token.equals("+") ||
					token.equals("Program") || token.equals("var") ||
					token.equals("debut") || token.equals("fin") ||
					token.equals("func") || token.equals("read(") ||
					token.equals("writeln(") || token.equals("while") ||
					token.equals("do")) {
						return new Token(token, new NoeudAtom(token, 0, true));
					} else {
						return new Token(token, new NoeudAtom("ident", 0, true));
					}
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		ScannerToken sc = new ScannerGPL("prgmSom.txt");
		while (sc.hasNext()) {
			System.out.println(sc.nextToken());
		}
	}
}

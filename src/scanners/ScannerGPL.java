package scanners;

import grammaires.NoeudAtom;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class ScannerGPL {

	private Scanner sc;

	public ScannerGPL(String cheminFichier) throws FileNotFoundException {
		sc = new Scanner(new File(cheminFichier));
	}

	public Token nextToken() {
		String token = sc.next();
				
		// Entier ?
		try {
			Integer.parseInt(token);
			return new Token("Entier", new NoeudAtom(token, 0, true));
		}
		// Pas entier !
		catch(NumberFormatException e) {
			// Symbole ?
			if(token.equals(":=") || token.equals("==") ||
					token.equals("<=") || token.equals(">=)") ||
					token.equals("<") || token.equals(">") ||
					token.equals("(") || token.equals(")") ||
					token.equals(",") || token.equals(";") ||
					token.equals(".") || token.equals("+")) {
				return new Token("Symbole", new NoeudAtom(token, 0, true));
			} else {
				return new Token("Identificateur", new NoeudAtom( token, 0, true));
			}
		}
	}

	public boolean hasNext() {
		return sc.hasNext();
	}

	
	public static void main(String[] args) throws FileNotFoundException {
		ScannerGZero sc = new ScannerGZero("grammaireZero.txt");
		while (sc.hasNext()) {
			System.out.println(sc.nextToken());
		}
	}
}

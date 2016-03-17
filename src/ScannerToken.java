import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ScannerToken {

	private Scanner sc;

	public ScannerToken(String cheminFichier) throws FileNotFoundException {
		sc = new Scanner(new File(cheminFichier));
		sc.useDelimiter(" (\\");
		sc.useDelimiter("\\) ");
		sc.useDelimiter(" [");
		sc.useDelimiter("] ");
	}

	public Noeud nextToken() {
		String token = sc.next();
		return checkToken(token);

	}

	public Noeud checkToken2(String fichier) {
		int debut, fin;
		if((debut = fichier.indexOf("[")) == 1) {
			fin = fichier.lastIndexOf(']');
			fichier = fichier.substring(debut, fin);
			return new Noeud(checkToken2(fichier), "star");
		} 
		
		else if ((debut = fichier.indexOf("(\\")) == 1) {
			fin = fichier.lastIndexOf("\\)");
			fichier = fichier.substring(debut, fin);
			return new Noeud(checkToken2(fichier), "un");
		} 
		
		else if((debut = fichier.indexOf("."))>=0) {
			String gauche = fichier.substring(0, debut);
			String droit = fichier.substring(debut);
			return new Noeud(checkToken2(gauche), checkToken2(droit), "conc");
		}
		
		else if((debut = fichier.indexOf("+"))>=0) {
			String gauche = fichier.substring(0, debut);
			String droit = fichier.substring(debut);
			return new Noeud(checkToken2(gauche), checkToken2(droit), "union");
		}
	}
	
	public Noeud checkToken(String token) {
		int action = 0;
		// Action ?
		if ((action = token.indexOf("#")) > 0) {
			Scanner tmp = new Scanner(token.substring(action));
			action = tmp.nextInt();
			tmp.close();
		}

		// Elements de la grammaire ?
		// union ?
		if (sc.hasNext("+")) {
			// Supprime +
			token = token.substring(2, token.length() - 2);
			return new Noeud(checkToken(token), "un");
		}

		// conc ?
		else if (token.startsWith("conc")) {
			// Supprime *
			token = token.substring(2, token.length() - 2);
			return new Noeud(checkToken(token), "un");
		}
		
		// star ?
		else if (token.startsWith("[")) {
			// Supprime les []
			token = token.substring(1, token.length() - 1);
			return new Noeud(checkToken(token), "star");
		}

		// un ?
		else if (token.startsWith("(\\")) {
			// Supprime les (\\)
			token = token.substring(2, token.length() - 2);
			return new Noeud(checkToken(token), "un");
		}

		// Element terminal ?
		else if (token.startsWith("'")) {
			// Supprime les ''
			token = token.substring(1, token.length() - 1);
			return new NoeudAtom("ELTER", token, action, true);
		}

		// Element non terminal
		return new NoeudAtom("IDNTER", token, 0, false);
	}

	public boolean hasNext() {
		return sc.hasNext();
	}

	public static void main(String[] args) throws FileNotFoundException {
		ScannerToken sc = new ScannerToken("grammaireTest.txt");
		while (sc.hasNext()) {
			System.out.println(sc.nextToken());
		}
	}
}

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class ScannerToken {

	private Scanner sc;
	
	public ScannerToken(String cheminFichier) throws FileNotFoundException {
		sc = new Scanner(new File(cheminFichier));
	}
	
	@SuppressWarnings("resource")
	public Noeud nextToken() {
		String token = sc.next();
		int action = 0;
		// Action ?
		if((action = token.indexOf("#")) > 0) {
			action = new Scanner(token.substring(action)).nextInt();
		}
		// Element terminal
		if(token.startsWith("'")) {
			return new Noeud("Osef");
		}
		// Element de la grammaire
		else if (token.equals("[") ||
				token.equals("]") ||
				token.equals("(\\") ) {
			return new Noeud(token);
		}
		// Element non terminal
		return new NoeudAtom("IDNTER", token, 0, false);
	}
	
	public boolean hasNext() {
		return sc.hasNext();
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		ScannerToken sc = new ScannerToken("grammaireTest.txt");
		while(sc.hasNext()) {
			System.out.println(sc.nextToken());
		}
	}
}

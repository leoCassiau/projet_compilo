import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ScannerToken {

	private Scanner sc;

	public ScannerToken(String cheminFichier) throws FileNotFoundException {
		sc = new Scanner(new File(cheminFichier));
	}

	public NoeudAtom nextToken() {
		String token = sc.next();
		int action = 0;
				


		// Element terminal ?
		if (token.startsWith("'")) {
			// Supprime les ''
			token = token.substring(1, token.length() - 1 );		
			
			// Action ?
			int indexDebutAction;
			if ((indexDebutAction = token.indexOf("#")) >= 0) {
				String actionString = token.substring(indexDebutAction + 1 );
				action = Integer.parseInt(actionString);
				token = token.substring(0, indexDebutAction);
			}
			
			return new NoeudAtom("ELTER", token, action, true);
		}

		// Action ?
		int indexDebutAction;
		if ((indexDebutAction = token.indexOf("#")) >= 0) {
			String actionString = token.substring(indexDebutAction + 1 );
			action = Integer.parseInt(actionString);
			token = token.substring(0, indexDebutAction);
		}
		
		// Element de la grammaire zero ?
		if(token.equals(".") || token.equals("+") ||
				token.equals("(\\") || token.equals("\\)") ||
				token.equals("(") || token.equals(")") ||
				token.equals("[") || token.equals("]") ||
				token.equals(",") || token.equals(";") || 
				token.equals("->")) {
			return new NoeudAtom(token, "", action, true);
		}
		
		// Element non terminal
		return new NoeudAtom("IDNTER", token, action, false);

	}

	public boolean hasNext() {
		return sc.hasNext();
	}

	
	public static void main(String[] args) throws FileNotFoundException {
		ScannerToken sc = new ScannerToken("grammaireZero.txt");
		while (sc.hasNext()) {
			System.out.println(sc.nextToken());
		}
	}
}

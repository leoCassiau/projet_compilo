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
		
		// Action ?
		if ((action = token.indexOf("#")) > 0) {
			Scanner tmp = new Scanner(token.substring(action));
			action = tmp.nextInt();
			tmp.close();
			token = token.substring(0, action);
		}

		// Element terminal ?
		if (token.startsWith("'")) {
			// Supprime les ''
			token = token.substring(1, token.length() - 1);
			return new NoeudAtom("ELTER", token, action, true);
		}

		// Element non terminal
		return new NoeudAtom("IDNTER", token, action, false);

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

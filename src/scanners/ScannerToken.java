package scanners;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class ScannerToken {

	protected Scanner sc;

	public ScannerToken(String cheminFichier) throws FileNotFoundException {
		sc = new Scanner(new File(cheminFichier));
	}

	public abstract Token nextToken();

	public boolean hasNext() {
		return sc.hasNext();
	}
}



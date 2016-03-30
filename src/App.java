import java.io.FileNotFoundException;

import grammaires.Grammaire;
import grammaires.GrammaireGpl;
import grammaires.GrammaireZero;


public class App {
	
	public static void main(String[] args) throws FileNotFoundException {
		Grammaire gzero = new GrammaireZero("grammaireGpl.txt");
		System.out.println("Analyse grammaireGpl.txt = " + gzero.analyse());
		
		GrammaireGpl gpl = new GrammaireGpl("prgmSom.txt", gzero.getArbreDependance());
		System.out.println("Analyse prgm.txt = " + gpl.analyse());
		
		Interpreteur inter = new Interpreteur(gpl.getPcode(), gpl.getPilex());
		inter.exec();

	}
	
}

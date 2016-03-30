package core;
import interpreteur.Interpreteur;

import java.io.FileNotFoundException;

import gpl.GrammaireGpl;
import grammaireZero.GrammaireZero;


public class App {
	
	public static void main(String[] args) throws FileNotFoundException {
		//1ere etape : GrammaireZero
		Grammaire gzero = new GrammaireZero("grammaireGpl.txt");
		System.out.println("Analyse grammaireGpl.txt = " + gzero.analyse());
		
		//2eme etape : Gpl
		GrammaireGpl gpl = new GrammaireGpl("prgmSom.txt", gzero.getArbreDependance());
		System.out.println("Analyse prgm.txt = " + gpl.analyse());
		
		//3eme etape : Interpreteur de code
		Interpreteur interpreteur = new Interpreteur(gpl.getPcode(), gpl.getPilex());
		interpreteur.exec();

	}
	
}

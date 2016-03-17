public class Noeud {

	protected Noeud gauche;
	protected Noeud droit;
	protected String code;
	
	public Noeud(Noeud n, String c) {
		gauche = n;
		code = c;
	}
	
	public Noeud(Noeud g, Noeud d, String  c) {
		droit = d;
		gauche = g;
		code = c;
	}
	
	public Noeud(String c){
		code = c;
	}

	public Noeud getDroit() {
		return droit;
	}

	public void setDroit(Noeud droit) {
		this.droit = droit;
	}

	public Noeud getGauche() {
		return gauche;
	}

	public void setGauche(Noeud gauche) {
		this.gauche = gauche;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String toString() {
		return "Code : " + code + " ;  \n Fils gauche : " + gauche + " ; \n Fils droit : " + droit + " ; \n";
	}
}

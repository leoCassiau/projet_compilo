
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class GrammaireZero {

	private Map<String, Noeud> arbreDependance = new HashMap<String, Noeud>();
	private Scanner sc = new Scanner();
	private Stack<Noeud> actions = new Stack<Noeud>();
	private List<String> dicot = new ArrayList<String>();
	private List<String> dicont = new ArrayList<String>();
	
	public GrammaireZero() {
		arbreDependance.put("S", creerArbreS());
		arbreDependance.put("N", creerArbreN());
		arbreDependance.put("E", creerArbreE());
		arbreDependance.put("T", creerArbreT());
		arbreDependance.put("F", creerArbreF());
	}
	
	public Map<String, Noeud> getArbreDependance() {
		return arbreDependance;
	}

	public void setArbreDependance(Map<String, Noeud> arbreDependance) {
		this.arbreDependance = arbreDependance;
	}
	
	//--------------------------------Fonctions gen--------------------------------------
	public Noeud genConc(Noeud p1, Noeud p2) {
		return new Noeud(p1,p2, "conc");
	}
	
	public Noeud genStar(Noeud p) {
		return new Noeud(p, "star");
	}
	
	public Noeud genUnion(Noeud p1, Noeud p2){
		return new Noeud(p1, p2, "union");
	}
	
	public Noeud genUn(Noeud p){
		return new Noeud(p,  "un");
	}
	
	public NoeudAtom genAtom(String chaine, int action, boolean terminal){
		if(terminal) {
			return new NoeudAtom("ELTER",chaine, action, terminal); 
		}
		return new NoeudAtom("IDNTER",chaine, action, terminal); 
	}
	
	//------------------------------Fonctions creerArbre----------------------------------
	public Noeud creerArbreS() {
		return genConc(
				genStar(
						genConc(
								genConc(
										genConc(
												genAtom("N",0,false),
												genAtom("->",0,true)),
										genAtom("E", 0, false)),
								genAtom(",",0,true))),
				genAtom(";",0,true));
	}

	public Noeud creerArbreN() {
		return genAtom("IDNTER", 0, true);
	}
	
	public Noeud creerArbreE() {
		return genConc(
				genAtom("T", 0, false),
				genStar(
						genConc(
								genAtom("+",0, true),
								genAtom("T",0, false))
								)
				);
	}
	
	public Noeud creerArbreT() {
		return genConc(
				genAtom("F", 0, false),
				genStar(
						genConc(
								genAtom(".",0, true),
								genAtom("F",0, false))
								)
				);
	}
	
	public Noeud creerArbreF(){
		return genUnion(
				genAtom("IDNTER",0,true),
				genUnion(
						genAtom("ELTER",0, true),
						genUnion(
								genConc(
										genAtom("(",0,true),
										genConc(
												genAtom("E",0,false),
												genAtom(")",0,true)
												)
										),
								genUnion(
										genConc(
												genAtom("[",0,true),
												genConc(
														genAtom("E",0,false),
														genAtom("]",0,true)
														)
												),
										genConc(
												genAtom("(/",0,true),
												genConc(
														genAtom("E",0,false),
														genAtom("/)",0,true)
														)
												)
											)
											
										)));
	}
	
	//-------------------------------------imprimArbre----------------------------------------
	public void imprimArbre(Noeud p){
		imprimArbreRec(p, 0);
	}
	
	private void imprimArbreRec(Noeud p, int profondeur) {
		profondeur++;
		for(int k = 1; k <= profondeur ; k++) {
			System.out.print("---");
		}
		if(p.getCode().equals("conc")) {
			System.out.println(">conc");
			imprimArbreRec(p.getGauche(), profondeur);
			imprimArbreRec(p.getDroit(), profondeur);
		}
		else if(p.getCode().equals("star")) {
			System.out.println(">star");
			imprimArbreRec(p.getGauche(), profondeur);
		}
		else if(p.getCode().equals("un")) {
			System.out.println(">un");
			imprimArbreRec(p.getGauche(), profondeur);
		}
		else if(p.getCode().equals("union")) {
			System.out.println(">union");
			imprimArbreRec(p.getGauche(), profondeur);
			imprimArbreRec(p.getDroit(), profondeur);
		}
		else if(p.getClass().equals(NoeudAtom.class)){
			NoeudAtom atom = (NoeudAtom) p;
			System.out.println(">" + atom.getCode() +", " + atom.getChaine() + ", " + atom.getAction() +", "+ atom.isTerminal());
		}

	}
	
	//----------------------------------------Analyseur---------------------------------
	private String tutu = sc.nextToken();
	public boolean analyse(Noeud p){
		if(p.getCode().equals("conc")){
			if(analyse(p.getGauche())){
				return analyse(p.getDroit());
			}
		}
		else if(p.getCode().equals("union")){
			if(analyse(p.getGauche())){
				return true;
			}
			else{
				return analyse(p.getDroit());
			}
		}
		else if(p.getCode().equals("star")){
			while(analyse(p.getGauche())) {};
			return true;
		}
		else if(p.getCode().equals("un")){
			analyse(p.getGauche());
			return true;
		}
		else if(p.getClass().equals(NoeudAtom.class)){
			NoeudAtom pAtom = (NoeudAtom) p;
			if(pAtom.isTerminal()){
				if(pAtom.getChaine().equals(tutu)){
					if(pAtom.getAction() != 0){
						//TODO g0.action(pAtom.act);
					}
					tutu = sc.nextToken();
					return true;
				}
			}else { // !pAtom.isTerminal 
				if(analyse(arbreDependance.get(pAtom.getChaine()))){
					if((pAtom.getAction() != 0)){
						//TODO g0.action(pAtom.act);
						//return true;
					}
					return true;
				}
			}
		} 
		return false;
	}
	
	public int recherche(List<String> dico, String mot) {
		if(dico.contains(mot)) {
			return dico.indexOf(mot);
		} else {
			dico.add(mot);
			return dico.size()-1;
		}
	}
	
	public void action(int action){
		Noeud t1,t2;
		
		switch(action){
		case 1:
			t1 = actions.pop();
			NoeudAtom t2atom = (NoeudAtom) actions.pop();
			arbreDependance.replace("GPL" + t2atom.getChaine() , t1);
			break;
		case 2 :
			NoeudAtom n = (NoeudAtom) arbreDependance.get(recherche(dicont, ""));
			actions.push(genAtom(n.getChaine(), action, n.isTerminal()));
			break;
		case 3 : 
			t1 = actions.pop();
			t2 = actions.pop();
			actions.push(genUnion(t2,t1));
			break;
		case 4 :
			t1 = actions.pop();
			t2 = actions.pop();
			actions.push(genConc(t2,t1));
			break;
		case 5 :
			if(CAType) {
				actions.push(genAtom(recherche(dicot, ?), action, true));
			} else {
				actions.push(genAtom(recherche(dicont, ?), action, false));
			}
			break;
		case 6 :
			t1 = actions.pop();
			actions.push(genStar(t1));
			break;
		case 7 :
			t1 = actions.pop();
			actions.push(genUn(t1));
			break;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GrammaireZero c = new GrammaireZero();
		//c.imprimArbre(c.getArbreDependance().get("S"));
		//c.imprimArbre(c.getArbreDependance().get("N"));
		//c.imprimArbre(c.getArbreDependance().get("E"));
		//c.imprimArbre(c.getArbreDependance().get("T"));
		//c.imprimArbre(c.getArbreDependance().get("F"));
		
		System.out.println(c.analyse(c.getArbreDependance().get("S")));
	}

}


public class NoeudAtom extends Noeud{
	
	private int action;
	private boolean terminal;
	private String chaine;
	
	public NoeudAtom(String code, String chaine, int action, boolean terminal) {
		super(code);
		this.setChaine(chaine);
		this.action = 0;
		this.terminal = terminal;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public boolean isTerminal() {
		return terminal;
	}

	public void setTerminal(boolean terminal) {
		this.terminal = terminal;
	}

	public String getChaine() {
		return chaine;
	}

	public void setChaine(String chaine) {
		this.chaine = chaine;
	}


}

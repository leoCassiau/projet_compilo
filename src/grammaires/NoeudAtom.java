package grammaires;


public class NoeudAtom extends Noeud{
	
	private int action;
	private boolean terminal;
	
	public NoeudAtom(String code, int action, boolean terminal) {
		super(code);
		this.action = action;
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

	public String toString() {
		return "Action : " + action + " ; terminal : " + terminal + " ; " + super.toString() ;	
	}

}

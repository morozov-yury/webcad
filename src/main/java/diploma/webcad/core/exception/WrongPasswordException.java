package diploma.webcad.core.exception;

public class WrongPasswordException extends Exception {
	private static final long serialVersionUID = 8351881320836563442L;
	
	private int left;
	
	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}
}

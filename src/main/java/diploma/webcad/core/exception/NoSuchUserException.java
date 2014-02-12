package diploma.webcad.core.exception;

public class NoSuchUserException extends Exception {
	private static final long serialVersionUID = -5304304472188640961L;

	private int left;
	
	public NoSuchUserException() {
		super();
	}

	public NoSuchUserException(int left) {
		super();
		this.left = left;
	}

	public int getLeft() {
		return left;
	}
	
	public void setLeft(int left) {
		this.left = left;
	}
}

package diploma.webcad.core.exception;

public class RedirectException extends Exception {

	private final String uri;

	public RedirectException(String uri) {
		this.uri = uri;
	}

	public String getUri() {
		return uri;
	}
	
}

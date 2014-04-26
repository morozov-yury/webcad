package diploma.webcad.view.model;

public class SigninCredentials {

	private String login;
	
	private String password;
	
	public SigninCredentials() {
		login = "";
		password = "";
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
}

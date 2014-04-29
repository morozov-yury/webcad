package diploma.webcad.core.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.server.Page;
import com.vaadin.ui.UI;

import diploma.webcad.common.security.MD5Helper;
import diploma.webcad.core.exception.NoSuchUserException;
import diploma.webcad.core.exception.UserAlreadySignedInException;
import diploma.webcad.core.exception.UserRetrievingException;
import diploma.webcad.core.exception.WrongPasswordException;
import diploma.webcad.core.init.SpringContextHelper;
import diploma.webcad.core.model.Language;
import diploma.webcad.core.model.User;
import diploma.webcad.view.WebCadUI;

@Component("sessionState")
@Scope("session")
public class SessionState {

	private static final String USER = "com.communicator.quiz.session.state.user";

	private static final String LANGUAGE = "com.communicator.quiz.session.state.language";

	public static final String SPRING_HELPER = "com.communicator.quiz.session.state.spring.helper";

	private Map<String, Object> parameters = new HashMap<String, Object>();

	@Autowired
	private UserService userManager;
	
	@Autowired
	private RuntimeRegistrator runtimeRegistrator;

	private ContentService contentManager;


	@Autowired
	public SessionState(SystemService systemManager, ContentService contentManager) {
		this.contentManager = contentManager;
		putParameter(LANGUAGE, contentManager.getDefaultLanguage());
	}

	public Object putParameter(String name, Object value) {
		return parameters.put(name, value);
	}

	public Object getParameter(String name) {
		return parameters.get(name);
	}

	public Object takeParameter(String name) {
		return parameters.remove(name);
	}

	public void removeParameter(String name) {
		parameters.remove(name);
	}

	public void setUser(User user) {
		putParameter(USER, user);
	}

	public User getUser() {
		return (User) getParameter(USER);
	}

	public void signin(String login, String password)
			throws UserAlreadySignedInException, NoSuchUserException, WrongPasswordException {
		if (getUser() != null) {
			throw new UserAlreadySignedInException();
		}
		String passwordHash = MD5Helper.getHash(password);
		if (!userManager.isUserExist(login)) {
			throw new NoSuchUserException();
		}
		User user = null;
		try {
			user = userManager.getUser(login);
		} catch (UserRetrievingException e) {
			WebCadUI.getCurrent().processUri("503");
		}
		if (user == null || !user.getPassword().equals(passwordHash)) {
			throw new WrongPasswordException();
		}
		setUser(user);

		Language language = user.getLanguage();
		if (language == null) {
			language = contentManager.getDefaultLanguage();
		}
		putParameter(LANGUAGE, language);

		WebCadUI.getCurrent().processUri(Page.getCurrent().getUriFragment());
	}

	public void signout() {
		runtimeRegistrator.removeUser(getUser(), UI.getCurrent());
		setUser(null);
		Map<String, Object> keepedParameters = new HashMap<String, Object>();
		parameters.clear();
		parameters.putAll(keepedParameters);
		WebCadUI.getCurrent().processUri(Page.getCurrent().getUriFragment());
	}

	public Language getLanguage() {
		return (Language) getParameter(LANGUAGE);
	}

	public SpringContextHelper getHelper() {
		return (SpringContextHelper) getParameter(SPRING_HELPER);
	}


}
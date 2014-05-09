package diploma.webcad.view.components;

import org.apache.commons.lang.StringUtils;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.core.exception.NoSuchUserException;
import diploma.webcad.core.exception.UserAlreadySignedInException;
import diploma.webcad.core.exception.WrongPasswordException;
import diploma.webcad.core.init.SpringContextHelper;
import diploma.webcad.core.service.SessionState;
import diploma.webcad.view.WebCadUI;
import diploma.webcad.view.service.NotificationService;

public class LoginComponent extends HorizontalLayout {

	private static final long serialVersionUID = -3111419830649734520L;
	
	private TextField nameField;

	private PasswordField passwordField;

	private SessionState sessionState;

	private NotificationService notificationService;

	public LoginComponent() {
		setSpacing(true);
		
		nameField = new TextField();
		nameField.setInputPrompt("name");
		passwordField = new PasswordField();
		passwordField.setInputPrompt("password");
		
		addComponent(nameField);
		addComponent(passwordField);
		
		VerticalLayout imageLayout =  new VerticalLayout();
		imageLayout.addStyleName("login-image");
		imageLayout.addLayoutClickListener(new LayoutClickListener() {
			private static final long serialVersionUID = -5895951296756751765L;
			@Override
			public void layoutClick(LayoutClickEvent arg0) {
				processLogin();
			}
		});

		addComponent(imageLayout);
	}
	
	@Override
	public void attach() {
		sessionState = WebCadUI.getCurrent().getSessionState();
		SpringContextHelper springContextHelper = sessionState.getHelper();
		notificationService = springContextHelper.getBean(NotificationService.class);
		super.attach();
	}

	private void processLogin () {
		String name = nameField.getValue();
		String password = passwordField.getValue();
		if (!StringUtils.isBlank(name) && !StringUtils.isBlank(password)) {
			try {
				sessionState.signin(name, password);
			} catch (UserAlreadySignedInException e) {
				notificationService.showError("Ошибка", "Такой пользователь уже залогинен в системе");
			} catch (NoSuchUserException e) {
				notificationService.showError("Ошибка", "Такой пользователь не зарегистрирован");
			} catch (WrongPasswordException e) {
				notificationService.showError("Ошибка", "Неправильный логий или пароль");
			}
		}
		
	}

}

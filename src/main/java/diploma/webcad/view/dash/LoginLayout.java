package diploma.webcad.view.dash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.core.exception.NoSuchUserException;
import diploma.webcad.core.exception.UserAlreadySignedInException;
import diploma.webcad.core.exception.WrongPasswordException;
import diploma.webcad.core.service.SessionState;
import diploma.webcad.view.WebCadUI;
import diploma.webcad.view.layouts.MainLayout;

@org.springframework.stereotype.Component	
@Scope("prototype")
public class LoginLayout extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	
	final CssLayout loginPanel;
	
	final TextField username;
	
	@Autowired
	private MainLayout mainLayout;
	
	public LoginLayout () {
		setSizeFull();
        addStyleName("login-layout");
        
        loginPanel = new CssLayout();
        loginPanel.addStyleName("login-panel");
        
        
        HorizontalLayout labels = new HorizontalLayout();
        labels.setWidth("100%");
        labels.setMargin(true);
        labels.addStyleName("labels");
        loginPanel.addComponent(labels);

        Label welcome = new Label("Авторизация");
        welcome.setSizeUndefined();
        welcome.addStyleName("h4");
        labels.addComponent(welcome);
        labels.setComponentAlignment(welcome, Alignment.MIDDLE_LEFT);

        Label title = new Label("WebCad");
        title.setSizeUndefined();
        title.addStyleName("h2");
        title.addStyleName("light");
        labels.addComponent(title);
        labels.setComponentAlignment(title, Alignment.MIDDLE_RIGHT);

        HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.setMargin(true);
        fields.addStyleName("fields");

        username = new TextField("Логин");
        username.focus();
        fields.addComponent(username);

        final PasswordField password = new PasswordField("Пароль");
        fields.addComponent(password);

        final Button signin = new Button("Войти");
        signin.addStyleName("default");
        fields.addComponent(signin);
        fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

        final ShortcutListener enter = new ShortcutListener("Sign In", KeyCode.ENTER, null) {
			private static final long serialVersionUID = -4694589498659783888L;
			@Override
            public void handleAction(Object sender, Object target) {
                signin.click();
            }
        };

        signin.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 7072639218211492984L;
			@Override
            public void buttonClick(ClickEvent event) {
            	if (username.getValue().isEmpty() 
            			|| password.getValue().isEmpty()) {
            		showError("Укажите логин/пароль");
            		return;
            	}
            	
            	SessionState sessionState = WebCadUI.getCurrent().getSessionState();
            	try {
            		sessionState.signin(username.getValue(), password.getValue());
            		signin.removeShortcutListener(enter);
                    mainLayout.repaint();
            	} catch (UserAlreadySignedInException e) {
            		showError("Вы уже залогинены в системе");
            	} catch (NoSuchUserException e) {
            		showError("Такого пользователя не существует");
            	} catch (WrongPasswordException e) {
            		showError("Неправильный пароль");
            	}
            }
        });

        signin.addShortcutListener(enter);
        loginPanel.addComponent(fields);
        addComponent(loginPanel);
        setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
	}
	
	private void showError (String message) {
		if (loginPanel.getComponentCount() > 2) {
            // Remove the previous error message
            loginPanel.removeComponent(loginPanel.getComponent(2));
        }
        // Add new error message
        Label error = new Label(message, ContentMode.HTML);
        error.addStyleName("error");
        error.setSizeUndefined();
        error.addStyleName("light");
        // Add animation
        error.addStyleName("v-animate-reveal");
        loginPanel.addComponent(error);
        username.focus();
	}

}

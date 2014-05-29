package diploma.webcad.view.tile;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.core.exception.NoSuchUserException;
import diploma.webcad.core.exception.UserAlreadySignedInException;
import diploma.webcad.core.exception.WrongPasswordException;
import diploma.webcad.core.service.ContentService;
import diploma.webcad.core.service.SessionState;
import diploma.webcad.view.model.SigninCredentials;

@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class SignInTile extends SignInUpTile {

	@Autowired
	private SessionState sessionState;
	
	@Autowired
	private ContentService contentService;
	
	private FieldGroup fieldGroup;
	
	private Label errorLabel;
	
	@PostConstruct
	private void refresh() {
		setContent(generateView());
	}
	
	@Override
	public void attach() {
		super.attach();
		refresh();
	}

	public SignInTile() {
		// TODO Auto-generated constructor stub
	}

	private Component generateView() {
		VerticalLayout view = new VerticalLayout();
		
		Label registerLabel = new Label(contentService.getAppResource(this, "caption.signin"));
		registerLabel.setStyleName("signin-tile-register-label");
		
		HorizontalLayout modeLayout = new HorizontalLayout();
		
		setLoginField(new TextField());
		getLoginField().setInputPrompt(contentService.getAppResource(this, "caption.login"));
		getLoginField().setStyleName("signin-tile-textfield");
		getLoginField().setIcon(new ThemeResource("images/dog.png"));
		
		setPasswordField(new PasswordField());
		getPasswordField().setInputPrompt(contentService.getAppResource(this, "caption.password"));
		getPasswordField().setStyleName("signin-tile-textfield");
		getPasswordField().setIcon(new ThemeResource("images/lock.png"));
		
		SigninCredentials signinCredentialsBean = new SigninCredentials();
		final Item item = new BeanItem<SigninCredentials>(signinCredentialsBean);
        fieldGroup = new FieldGroup(item);
        fieldGroup.bindMemberFields(this);
        
        FormLayout formLayout = new FormLayout(getLoginField(), getPasswordField());
        formLayout.setStyleName("signin-tile-signin-form");
        SigninListener signinListener = new SigninListener("Ok", KeyCode.ENTER, null);
        formLayout.addShortcutListener(signinListener);
        
        Button btnSignup = new Button(contentService.getAppResource(this, "button.signin"));
        btnSignup.setStyleName("signin-tile-signup-button");
        btnSignup.setIcon(new ThemeResource("images/on.png"));
        btnSignup.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				performSignin();
			}
		});
        
		errorLabel = new Label("", ContentMode.HTML);
		errorLabel.setVisible(false);
		errorLabel.setStyleName("signin-tile-errorlabel");
		
		//view.addComponent(registerLabel);
		view.addComponent(modeLayout);
		view.addComponent(errorLabel);
		view.addComponents(formLayout, btnSignup);
		
		
		return view;
	}
	
	protected void performSignin() {
		try {
			sessionState.signin((String)fieldGroup.getField("login").getValue(), (String)fieldGroup.getField("password").getValue());
		} catch (UserAlreadySignedInException e) {
			errorLabel.setValue("<font color=\"red\"><b>" + contentService.getAppResource(e, "error") + "</b></font>");
			errorLabel.setVisible(true);
		} catch (NoSuchUserException e) {
			errorLabel.setValue("<font color=\"red\"><b>" + contentService.getAppResource(e, "error") + "</b></font>");
			errorLabel.setVisible(true);
		} catch (WrongPasswordException e) {
			errorLabel.setValue("<font color=\"red\"><b>" + contentService.getAppResource(e, "error") + "</b></font>");
			errorLabel.setVisible(true);
		}
	}
	
}

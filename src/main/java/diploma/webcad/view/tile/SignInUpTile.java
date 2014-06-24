package diploma.webcad.view.tile;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.core.exception.NoSuchUserException;
import diploma.webcad.core.exception.UserAlreadyExistException;
import diploma.webcad.core.exception.UserAlreadySignedInException;
import diploma.webcad.core.exception.WrongPasswordException;
import diploma.webcad.core.service.ContentService;
import diploma.webcad.core.service.SessionState;
import diploma.webcad.core.service.UserService;
import diploma.webcad.view.WebCadUI;
import diploma.webcad.view.model.SigninCredentials;

@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class SignInUpTile extends Panel {

	protected static final int MODE_MERCHANT = 0;
	
	protected static final int MODE_SOCIAL = 1;

	protected int mode = MODE_MERCHANT;

	@Autowired
	private SessionState sessionState;
	
	@Autowired
	private ContentService contentService;
	
	@Autowired
	private UserService userManager;
	
	@PropertyId("login")
	private TextField loginField;
	
	@PropertyId("password")
	private PasswordField passwordField;
	
	private FieldGroup fieldGroup;
	
	private Label errorLabel;

	public SignInUpTile() {}
	
	@PostConstruct
	private void refresh() {
		setContent(generateNotSignedView());
	}

	@Override
	public void attach() {
		super.attach();
		refresh();
	}
	
	private Component generateNotSignedView() {
		VerticalLayout view = new VerticalLayout();
		
		Label registerLabel = new Label(contentService.getAppResource(this, "caption.register"));
		registerLabel.setStyleName("signin-tile-register-label");
		
		HorizontalLayout modeLayout = new HorizontalLayout();
		
		final Button btnMerchant = new Button(contentService.getAppResource(this, "button.merchant"));
		btnMerchant.setIcon(new ThemeResource("images/factory_active.png"));
		btnMerchant.setStyleName("signin-tile-mode-company-button-selected");
		modeLayout.addComponent(btnMerchant);
		
		final Button btnSocial = new Button(contentService.getAppResource(this, "button.social"));
		btnSocial.setIcon(new ThemeResource("images/person.png"));
		btnSocial.setStyleName("signin-tile-mode-person-button-unselected");
		modeLayout.addComponent(btnSocial);
		
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
        
        Button btnSignup = new Button(contentService.getAppResource(this, "button.signup"));
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
		
		view.addComponent(registerLabel);
		view.addComponent(modeLayout);
		view.addComponent(errorLabel);
		view.addComponents(formLayout, btnSignup);
		
		btnSocial.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				mode = MODE_SOCIAL;
				
				btnMerchant.setStyleName("signin-tile-mode-company-button-unselected");
				btnSocial.setStyleName("signin-tile-mode-person-button-selected");
				
				btnMerchant.setIcon(new ThemeResource("images/factory.png"));
				btnSocial.setIcon(new ThemeResource("images/person_active.png"));
			}
		});
		
		btnMerchant.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				mode = MODE_MERCHANT;
				
				btnMerchant.setStyleName("signin-tile-mode-company-button-selected");
				btnSocial.setStyleName("signin-tile-mode-person-button-unselected");
				
				btnMerchant.setIcon(new ThemeResource("images/factory_active.png"));
				btnSocial.setIcon(new ThemeResource("images/person.png"));
			}
		});
		
		return view;
	}
	
	protected void performSignin() {
		try {
			sessionState.signin((String)fieldGroup.getField("login").getValue(), (String)fieldGroup.getField("password").getValue());
		} catch (UserAlreadySignedInException e) {
			errorLabel.setValue("<font color=\"red\"><b>" + contentService.getAppResource(e, "error") + "</b></font>");
			errorLabel.setVisible(true);
		} catch (NoSuchUserException e) {
			performSignup();
//			errorLabel.setValue("<font color=\"red\"><b>" + contentService.getAppRes(e, "error") + "</b></font>");
//			errorLabel.setVisible(true);
		} catch (WrongPasswordException e) {
			errorLabel.setValue("<font color=\"red\"><b>" + contentService.getAppResource(e, "error") + "</b></font>");
			errorLabel.setVisible(true);
		}
	}
	
	protected void performSignup() {
		try {
			String login = (String)fieldGroup.getField("login").getValue();
			String password = (String)fieldGroup.getField("password").getValue();
			userManager.createUser(login, password, sessionState.getLanguage());
			sessionState.signin(login, password);
			WebCadUI.getCurrent().navigateTo("");
		} catch (UserAlreadyExistException e) {
			errorLabel.setValue("<font color=\"red\"><b>" + contentService.getAppResource(e, "error") + "</b></font>");
			errorLabel.setVisible(true);
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
	
	public TextField getLoginField() {
		return loginField;
	}

	public void setLoginField(TextField loginField) {
		this.loginField = loginField;
	}

	public PasswordField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(PasswordField passwordField) {
		this.passwordField = passwordField;
	}

	protected class SigninListener extends ShortcutListener implements ClickListener {

		public SigninListener(String caption, int keyCode, int[] modifierKeys) {
			super(caption, keyCode, modifierKeys);
		}

		@Override
		public void buttonClick(ClickEvent event) {
			performSignin();
		}

		@Override
		public void handleAction(Object sender, Object target) {
			performSignin();
		}
		
	}

}

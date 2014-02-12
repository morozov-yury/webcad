package diploma.webcad.view.tiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import diploma.webcad.core.manager.UserManager;
import diploma.webcad.view.components.SessionHelper;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class HeaderTile extends AbstractHeaderTile {
	
	@Autowired
	public HeaderTile(final SessionHelper sessionHelper, UserManager userManager, final SignInTile signInTile) {
		VerticalLayout spacer;
		
		HorizontalLayout content = new HorizontalLayout();
		content.setSizeFull();

		// LEFT
		
		HorizontalLayout hLeft = new HorizontalLayout(); 
		
		spacer = new VerticalLayout();
		spacer.setStyleName("header-tile-sides-indent");
		hLeft.addComponent(spacer);
		
		Image logoImage = new Image("", new ThemeResource("images/logo.png"));
		logoImage.setStyleName("header-tile-logo");
		hLeft.addComponent(logoImage);
		
		spacer = new VerticalLayout();
		spacer.setStyleName("header-tile-logo-slogan-delim");
		hLeft.addComponent(spacer);
		
		Label sloganLabel = new Label(sessionHelper.getAppRes(this, "slogan"));
		sloganLabel.setStyleName("header-tile-slogan");
		hLeft.addComponent(sloganLabel);
		hLeft.setComponentAlignment(sloganLabel, Alignment.MIDDLE_LEFT);
		
		//-----------------------------------------
		
		// RIGHT
		
		HorizontalLayout hRight = new HorizontalLayout();
		Image usercountImage = new Image();
		usercountImage.setSource(new ThemeResource("images/usercount.png"));
		hRight.addComponent(usercountImage);
		hRight.setComponentAlignment(usercountImage, Alignment.TOP_RIGHT);
		usercountImage.setStyleName("header-tile-user-count-image");
		
		VerticalLayout vLayout = new VerticalLayout();
		hRight.addComponent(vLayout);
		hRight.setComponentAlignment(vLayout, Alignment.MIDDLE_CENTER);
		
		Label countLabel = new Label(userManager.getUserCount().toString(), ContentMode.HTML);
		countLabel.addStyleName("header-tile-count-label");
		vLayout.addComponent(countLabel);
		
		Label countLabelDescription = new Label(sessionHelper.getAppRes(this, "usercount"), ContentMode.HTML);
		vLayout.addComponent(countLabelDescription);
		
		Button btnSignin = new Button(sessionHelper.getAppRes(this, "button.signin"), new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				Window signWindow = new Window(sessionHelper.getAppRes(HeaderTile.this, "button.signin"));
				signWindow.setModal(true);
				signWindow.center();
				signWindow.setResizable(false);
				signWindow.setContent(signInTile);
				UI.getCurrent().addWindow(signWindow);
			}
			
		});
		btnSignin.setStyleName("header-tile-button-signin");
		hRight.addComponent(btnSignin);
		
		spacer = new VerticalLayout();
		spacer.setStyleName("header-tile-sides-indent");
		hRight.addComponent(spacer);
		
		//-----------------------------------------
		
		content.addComponent(hLeft);
		content.setComponentAlignment(hLeft, Alignment.MIDDLE_LEFT);
		
		content.addComponent(hRight);
		content.setComponentAlignment(hRight, Alignment.MIDDLE_RIGHT);
		
		setContent(content);
		
	}
}

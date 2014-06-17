package diploma.webcad.view.dash;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.core.model.User;
import diploma.webcad.view.WebCadUI;

public class UserMenu extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	public UserMenu () {
		
	}

	@Override
	public void attach() {
		removeAllComponents();
		
		User user = WebCadUI.getCurrent().getSessionState().getUser();
		
		setSizeUndefined();
        addStyleName("user");
        
        ThemeResource userImageResource = new ThemeResource("img/profile-pic.png");
        Image profilePic = new Image(null, userImageResource);
        profilePic.setWidth("34px");
        addComponent(profilePic);
        
        Label userName = new Label(user.getName());
        userName.setSizeUndefined();
        addComponent(userName);

        Command cmd = new Command() {
			private static final long serialVersionUID = 9183864438835101448L;
			@Override
            public void menuSelected(MenuItem selectedItem) {
                Notification.show("Not implemented yet");
            }
        };
        
        MenuBar settings = new MenuBar();
        MenuItem settingsMenu = settings.addItem("", null);
        settingsMenu.setStyleName("icon-cog");
        settingsMenu.addItem("Settings", cmd);
        settingsMenu.addItem("Preferences", cmd);
        settingsMenu.addSeparator();
        settingsMenu.addItem("My Account", cmd);
        addComponent(settings);

        Button exit = new NativeButton("Exit");
        exit.addStyleName("icon-cancel");
        exit.setDescription("Sign Out");
        addComponent(exit);
        exit.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -4329567266941978800L;
			@Override
            public void buttonClick(ClickEvent event) {
                WebCadUI.getCurrent().getSessionState().signout();
            }
        });
		super.attach();
	}
	
	
	
}

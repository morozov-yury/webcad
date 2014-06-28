package diploma.webcad.view.client.component;

import org.vaadin.alump.fancylayouts.FancyNotifications;
import org.vaadin.alump.fancylayouts.gwt.client.shared.FancyNotificationsState.Position;

import com.vaadin.server.ThemeResource;

public class Notificator extends FancyNotifications {

	private static final long serialVersionUID = -3969180472015453582L;
	
	private ThemeResource errorIconResource;
	
	public Notificator () {
		setCloseTimeout(5000);
		setPosition(Position.BOTTOM_LEFT);
		setClickClose(true);
		setImmediate(true);
		
		errorIconResource = new ThemeResource("img/error64x64.png");
	}
	
	public void showError (String caption, String message) {
		showNotification(this, caption, message, errorIconResource, "notification-error");
	}
	
	public void showInfo (String message) {
		showNotification(this, message);
	}

}

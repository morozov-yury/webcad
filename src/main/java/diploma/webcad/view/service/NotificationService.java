package diploma.webcad.view.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.alump.fancylayouts.FancyNotifications;
import org.vaadin.alump.fancylayouts.gwt.client.shared.FancyNotificationsState.Position;

import com.vaadin.server.ThemeResource;

@Component
@Scope("singleton")
public class NotificationService {
	
	private FancyNotifications fancyNotifications;
	
	private ThemeResource errorIconResource;
	
	public NotificationService () {
		fancyNotifications = new FancyNotifications();
		fancyNotifications.setCloseTimeout(5000);
		fancyNotifications.setPosition(Position.BOTTOM_RIGHT);
		fancyNotifications.setClickClose(true);
		fancyNotifications.setImmediate(true);
		
		errorIconResource = new ThemeResource("img/error64x64.png");
	}
	
	public FancyNotifications getFancyNotifications() {
		return fancyNotifications;
	}
	
	public void showError (String caption, String message) {
		fancyNotifications.showNotification(this, caption, message, errorIconResource, "notification-error");
	}
	
	public void showInfo (String message) {
		fancyNotifications.showNotification(this, message);
	}

}

package diploma.webcad.view.pages;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import diploma.webcad.core.exception.RedirectException;
import diploma.webcad.view.tiles.AbstractContentTile;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
@VaadinView(Main.NAME)
public class Main extends AbstractContentTile {

	public static final String NAME = "";

	private VerticalLayout mainVertLayout;

	public Main() {
		super();
		this.mainVertLayout = new VerticalLayout();
		this.mainVertLayout.setSpacing(true);
		setContent(this.mainVertLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		this.mainVertLayout.removeAllComponents();
		this.mainVertLayout.addComponent(new Label("Welcome to WebCad"));
		Button testButton = new Button("Test buttom");
		testButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent arg0) {
				Notification.show("Test", Type.TRAY_NOTIFICATION);
			}
		});
		mainVertLayout.addComponent(testButton);
		//vl.addComponent(new Button("Referral test page", new RedirectListener(ReferralTestPage.NAME)));
	}

	@Override
	public boolean validate(ViewChangeEvent event) throws RedirectException {
		return true;
	}

}

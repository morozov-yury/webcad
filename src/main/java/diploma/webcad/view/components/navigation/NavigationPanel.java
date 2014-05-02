package diploma.webcad.view.components.navigation;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.view.WebCadUI;
import diploma.webcad.view.pages.MainPage;
import diploma.webcad.view.pages.test.TestPage;

public class NavigationPanel extends VerticalLayout {

	private static final long serialVersionUID = 3029786735525086839L;

	public NavigationPanel() {
		addStyleName("navigation-panel");
		
		NavigateButton homeButton = new NavigateButton();
		homeButton.addLayoutClickListener(new LayoutClickListener() {
			private static final long serialVersionUID = -2879532922301981802L;
			@Override
			public void layoutClick(LayoutClickEvent arg0) {
				WebCadUI.getCurrent().processUri(MainPage.NAME);
			}
		});
		homeButton.addStyleName("navigation-panel-button-home");
		addComponent(homeButton);
		
		NavigateButton settingsButton = new NavigateButton();
		settingsButton.addLayoutClickListener(new LayoutClickListener() {
			private static final long serialVersionUID = 751472024434686956L;
			@Override
			public void layoutClick(LayoutClickEvent arg0) {
				WebCadUI.getCurrent().processUri(TestPage.NAME);
			}
		});
		settingsButton.addStyleName("navigation-panel-button-settings");
		addComponent(settingsButton);
	}
	
	private class NavigateButton extends VerticalLayout {

		private static final long serialVersionUID = -4442988418744085933L;
		
		public NavigateButton () {
			addStyleName("navigation-panel-button");
		}
		
	}

}

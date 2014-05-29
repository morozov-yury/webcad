package diploma.webcad.view.layouts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.view.Layout;
import diploma.webcad.view.components.navigation.NavigationPanel;
import diploma.webcad.view.service.NotificationService;
import diploma.webcad.view.tile.TopTile;

@SuppressWarnings("serial")
@org.springframework.stereotype.Component	
@Scope("prototype")
public class MainLayout extends VerticalLayout implements Layout {
	
	private TopTile topTile;
	
	private VerticalLayout mainContentVL;

	private GridLayout mainGridLayout;

	private View currentView = null;

	@Autowired
	public MainLayout(NotificationService notificationService) {
		addStyleName("main-layout");
		setSizeFull();
		
		mainGridLayout = new GridLayout(2, 2);
		mainGridLayout.addStyleName("main-layout-grid");
		
		topTile = new TopTile();
		mainContentVL = new VerticalLayout();
		mainContentVL.addStyleName("full-width");
		
		
		NavigationPanel navigationPanel = new NavigationPanel();
		mainGridLayout.addComponent(navigationPanel, 0, 1);
		
		
		mainGridLayout.addComponent(topTile, 1, 0);
		mainGridLayout.addComponent(mainContentVL, 1, 1);
		addComponent(mainGridLayout);
		setComponentAlignment(mainGridLayout, Alignment.TOP_CENTER);
		
		addComponent(notificationService.getFancyNotifications());
	}

	@Override
	public void repaint() {
		mainContentVL.removeAllComponents();
		mainContentVL.addComponent((Component) currentView);
	}

	@Override
	public void setContent(View view) {
		currentView = view;
		repaint();
	}

	@Override
	public View getContent() {
		return currentView;
	}

}

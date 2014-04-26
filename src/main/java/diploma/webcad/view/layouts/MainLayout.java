package diploma.webcad.view.layouts;

import org.springframework.context.annotation.Scope;

import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.core.view.Layout;
import diploma.webcad.view.tile.TopTile;

@SuppressWarnings("serial")
@org.springframework.stereotype.Component	
@Scope("prototype")
public class MainLayout extends VerticalLayout implements Layout {
	
	private TopTile topTile;
	
	private VerticalLayout mainContentVL;

	private GridLayout mainGridLayout;

	private View currentView = null;

	public MainLayout() {
		addStyleName("main-layout");
		setSizeFull();
		
		mainGridLayout = new GridLayout(1, 2);
		mainGridLayout.addStyleName("main-layout-grid");
		
		topTile = new TopTile();
		mainContentVL = new VerticalLayout();
		mainContentVL.addStyleName("full-width");
		
		mainGridLayout.addComponent(topTile, 0, 0);
		mainGridLayout.addComponent(mainContentVL, 0, 1);
		addComponent(mainGridLayout);
		setComponentAlignment(mainGridLayout, Alignment.TOP_CENTER);
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

package diploma.webcad.view.layouts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.core.service.SessionState;
import diploma.webcad.core.web.filter.Layout;
import diploma.webcad.view.components.SessionHelper;
import diploma.webcad.view.pages.AbstractPage;

@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class MainLayout extends VerticalLayout implements Layout {
	
	@Autowired(required=true)
	private SessionState sessionState;
	
	private VerticalLayout mainContentVL;
	
	private VerticalLayout topContentVL;
	
	private GridLayout gridLayout;

	private View currentView = null;

	@Autowired
	public MainLayout(SessionHelper sessionHelper) {
		setSizeFull();
		
		mainContentVL = new VerticalLayout();
		mainContentVL.setSizeFull();
		
		gridLayout = new GridLayout(1, 1);
		gridLayout.setSpacing(true);
		gridLayout.addStyleName("landing-layout-grid");
	}

	@Override
	public void repaint() {
		removeAllComponents();
		addComponent(mainContentVL);
		
		mainContentVL.removeAllComponents();
		mainContentVL.addComponent(gridLayout);
		mainContentVL.setExpandRatio(gridLayout, 1.0f);
		
		mainContentVL.setComponentAlignment(gridLayout, Alignment.TOP_CENTER);

		gridLayout.removeAllComponents();
		gridLayout.addComponent((Component) currentView, 0, 0, 0, 0);

		gridLayout.setComponentAlignment((Component) currentView, Alignment.TOP_CENTER);
		
		((Component)currentView).setWidth(1000, Unit.PIXELS);
		//((Component)view).setHeight(1000, Unit.PIXELS);
	}

	@Override
	public void setContent(View view) {
		if(!(view instanceof AbstractPage)) {
			throw new IllegalArgumentException("Only " + AbstractPage.class.getName() + 
					" allowed, but " + view.getClass().getName() + " provided.");
		}
		this.currentView = view;
		((Component)view).addStyleName("main-view-component");
		repaint();
	}

	@Override
	public View getContent() {
		return currentView;
	}

}

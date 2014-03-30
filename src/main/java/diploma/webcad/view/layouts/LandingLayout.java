package diploma.webcad.view.layouts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;

import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.core.manager.SessionState;
import diploma.webcad.core.model.User;
import diploma.webcad.core.view.Layout;
import diploma.webcad.view.components.SessionHelper;
import diploma.webcad.view.tiles.AbstractContentTile;
import diploma.webcad.view.tiles.HeaderTile;
import diploma.webcad.view.tiles.InfoTile;
import diploma.webcad.view.tiles.NewsTile;
import diploma.webcad.view.tiles.SignInUpTile;

@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
@DependsOn({"dummyTile"})
public class LandingLayout extends VerticalLayout implements Layout {
	
	private VerticalLayout contentVartLayout;
	
	private GridLayout gridLayout;
	
	@Autowired(required=true)
	private SessionState sessionState;
	
	private View view = null;

	@Autowired
	public LandingLayout(SessionHelper sessionHelper) {
		setSizeFull();
		
		contentVartLayout = new VerticalLayout();
		contentVartLayout.setStyleName("landing-layout-content");
		contentVartLayout.setSizeFull();
		
		gridLayout = new GridLayout(1, 1);
		gridLayout.setSpacing(true);
		gridLayout.addStyleName("landing-layout-grid");
	}

	@Override
	public void repaint() {
		removeAllComponents();
		addComponent(contentVartLayout);
		
		contentVartLayout.removeAllComponents();
		contentVartLayout.addComponent(gridLayout);
		contentVartLayout.setExpandRatio(gridLayout, 1.0f);
		
		contentVartLayout.setComponentAlignment(gridLayout, Alignment.TOP_CENTER);

		gridLayout.removeAllComponents();
		gridLayout.addComponent((Component) view, 0, 0, 0, 0);

		gridLayout.setComponentAlignment((Component) view, Alignment.TOP_CENTER);
		
		((Component)view).setWidth(1000, Unit.PIXELS);
		//((Component)view).setHeight(1000, Unit.PIXELS);
	}

	@Override
	public void setContent(View view) {
		if(!(view instanceof AbstractContentTile)) {
			throw new IllegalArgumentException("Only " + AbstractContentTile.class.getName() + 
					" allowed, but " + view.getClass().getName() + " provided.");
		}
		this.view = view;
		((Component)view).addStyleName("main-view-component");
		repaint();
	}

	@Override
	public View getContent() {
		return view;
	}

}

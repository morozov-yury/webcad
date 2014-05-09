package diploma.webcad.view.tile;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;

import diploma.webcad.view.components.LoginComponent;
import diploma.webcad.view.components.neclus.NeclusStatusIndicator;

public class TopTile extends Panel {

	private static final long serialVersionUID = -8667328501365141023L;
	
	public TopTile () {
		addStyleName("top-tile");
		HorizontalLayout mainLayout = new HorizontalLayout();
		mainLayout.setWidth("1004px");
		
		NeclusStatusIndicator indicator = new NeclusStatusIndicator();
		LoginComponent loginComponent = new LoginComponent();
		
		mainLayout.addComponent(indicator);
		mainLayout.addComponent(loginComponent);
		
		mainLayout.setComponentAlignment(indicator, Alignment.MIDDLE_LEFT);
		mainLayout.setComponentAlignment(loginComponent, Alignment.MIDDLE_RIGHT);

		setContent(mainLayout);
	}

}

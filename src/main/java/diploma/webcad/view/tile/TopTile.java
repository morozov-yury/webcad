package diploma.webcad.view.tile;

import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.view.components.NeclusStatusIndicator;

public class TopTile extends Panel {

	private static final long serialVersionUID = -8667328501365141023L;
	
	public TopTile () {
		addStyleName("top-tile");
		VerticalLayout mainContent = new VerticalLayout();
		setContent(mainContent);
		
		NeclusStatusIndicator indicator = new NeclusStatusIndicator();
		mainContent.addComponent(indicator);
	}

}

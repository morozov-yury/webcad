package diploma.webcad.view.tiles;

import com.vaadin.ui.Panel;

import diploma.webcad.core.view.Tile;

@SuppressWarnings("serial")
public abstract class AbstractUserPanelTile extends Panel implements Tile {
	
	public AbstractUserPanelTile() {
//		setWidth(400, Unit.PIXELS);
//		setStyleName("user-panel-tile");
		setStyleName("signin-tile");
	}

	@Override
	public String getCss() {
		return "";
	}

}
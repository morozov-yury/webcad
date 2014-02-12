package diploma.webcad.view.tiles;

import com.vaadin.ui.Panel;

import diploma.webcad.core.view.Tile;

@SuppressWarnings("serial")
public class AbstractInfoTile extends Panel implements Tile {

	public AbstractInfoTile() {
		setWidth(100, Unit.PERCENTAGE);
		setStyleName("info-tile");
	}
	
	@Override
	public String getCss() {
		return "";
	}
	
}

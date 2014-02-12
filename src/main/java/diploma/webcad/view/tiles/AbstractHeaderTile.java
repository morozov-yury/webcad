package diploma.webcad.view.tiles;

import com.vaadin.ui.Panel;

import diploma.webcad.core.view.Tile;

@SuppressWarnings("serial")
public abstract class AbstractHeaderTile extends Panel implements Tile {
	
	public AbstractHeaderTile() {
		setWidth(100, Unit.PERCENTAGE);
		setHeight(100, Unit.PIXELS);
		setStyleName("header-tile");
	}
	
	@Override
	public String getCss() {
		return "";//"top: 0px; left: -13px; position: fixed; padding-left: 13px;";
	}

}

package diploma.webcad.view.tiles;

import com.vaadin.ui.Panel;

import diploma.webcad.core.view.Tile;

@SuppressWarnings("serial")
public abstract class AbstractFooterTile extends Panel implements Tile {
	
	public AbstractFooterTile() {
		setWidth(100, Unit.PERCENTAGE);
		setHeight(50, Unit.PIXELS);
		//setStyleName("light ml-not-transparent");
	}
	
	@Override
	public String getCss() {
		return "bottom: 0px; left: -13px; position: fixed; padding-left: 13px;";
	}

}

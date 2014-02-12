package diploma.webcad.view.tiles;

import com.vaadin.ui.Panel;

import diploma.webcad.core.view.Tile;

@SuppressWarnings("serial")
public abstract class AbstractNewsTile extends Panel implements Tile {

	public AbstractNewsTile() {
		setWidth(400, Unit.PIXELS);
		setStyleName("news-tile");
	}
	
	@Override
	public String getCss() {
		return "";
	}
	
}

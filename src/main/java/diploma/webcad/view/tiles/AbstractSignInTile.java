package diploma.webcad.view.tiles;

import com.vaadin.ui.Panel;

import diploma.webcad.core.view.Tile;

@SuppressWarnings("serial")
public abstract class AbstractSignInTile extends Panel implements Tile {

	public AbstractSignInTile() {
		setStyleName("signin-tile");
	}
	
	@Override
	public String getCss() {
		return "";
	}

}

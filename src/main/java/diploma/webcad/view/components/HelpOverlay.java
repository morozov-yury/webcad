package diploma.webcad.view.components;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class HelpOverlay extends Window {

	private static final long serialVersionUID = 7385496291372142341L;

	public HelpOverlay (String caption, String text, String style) {
		CssLayout cssLayout = new CssLayout();
		setContent(cssLayout);
        setPrimaryStyleName("help-overlay");
        setDraggable(false);
        setResizable(false);
        setCaption(caption);
        cssLayout.addComponent(new Label(text, ContentMode.HTML));
        setStyleName(style);
	}

}

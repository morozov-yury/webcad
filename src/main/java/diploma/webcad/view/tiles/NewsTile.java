package diploma.webcad.view.tiles;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class NewsTile extends AbstractNewsTile {

	private VerticalLayout content;

	public NewsTile() {
		content = new VerticalLayout();
		setContent(content);
		content.addComponent(new Label("News tile"));
		addStyleName("news-tile-background");
	}
	
}

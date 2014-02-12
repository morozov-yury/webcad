package diploma.webcad.view.tiles;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class FooterTile extends AbstractFooterTile {
	public FooterTile() {
//		setContent(new Label("footer"));
		Button btn = new Button("go Landing");
		setContent(btn);
		btn.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				System.out.println("FooterTile buttonClick");
			}
		});
	}
}

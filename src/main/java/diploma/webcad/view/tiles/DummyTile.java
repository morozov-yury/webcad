package diploma.webcad.view.tiles;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
@Component("dummyTile")
@Scope("prototype")
public class DummyTile extends HorizontalLayout implements View {
	
	private static int counter = 0;
	private static Label label = new Label("Header | "+counter);
	
	public DummyTile() {
		setWidth(100, Unit.PERCENTAGE);
		setHeight(50, Unit.PIXELS);
		addComponent(label);
		Button button = new Button("+");
		addComponent(button);
		button.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				counter++;
				label.setValue("Header | "+counter);
			}
		});
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
	}

}

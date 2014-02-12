package diploma.webcad.view.tiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.view.components.SessionHelper;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class InfoTile extends AbstractInfoTile {

	private Label infoLabel;
	
	@Autowired
	public InfoTile(SessionHelper sessionHelper) {
		VerticalLayout content = new VerticalLayout();
		content.setHeight(50, Unit.PIXELS);
		content.setWidth(100, Unit.PERCENTAGE);
		
		infoLabel = new Label(sessionHelper.getAppRes(this, "generalinfo"), ContentMode.HTML);
		infoLabel.setSizeUndefined();
		content.addComponent(infoLabel);
		content.setComponentAlignment(infoLabel, Alignment.MIDDLE_CENTER);
		
		setContent(content);
		
	}
	
}

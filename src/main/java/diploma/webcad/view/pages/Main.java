package diploma.webcad.view.pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.core.manager.NeclusManager;
import diploma.webcad.view.components.NeclusStatusIndicator;
import diploma.webcad.view.tiles.AbstractContentTile;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
@VaadinView(Main.NAME)
public class Main extends AbstractContentTile {

	public static final String NAME = "";

	private static Logger log = LoggerFactory.getLogger(Main.class);
	
	@Autowired
	private NeclusManager sshManager;

	private VerticalLayout mainLayout;

	public Main() {
		super();
		this.mainLayout = new VerticalLayout();
		this.mainLayout.setSpacing(true);
		setContent(this.mainLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		this.mainLayout.removeAllComponents();
		this.mainLayout.addComponent(new Label("Welcome to WebCad"));

		HorizontalLayout topLayout = new HorizontalLayout();
		this.mainLayout.addComponent(topLayout);

		topLayout.addComponent(new NeclusStatusIndicator());
	}

}

package diploma.webcad.view.pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.core.service.NeclusService;
import diploma.webcad.view.components.NeclusStatusIndicator;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
@VaadinView(MainPage.NAME)
public class MainPage extends AbstractPage {

	public static final String NAME = "";

	private static Logger log = LoggerFactory.getLogger(MainPage.class);
	
	@Autowired
	private NeclusService sshManager;

	private VerticalLayout mainLayout;

	public MainPage() {
		this.mainLayout = new VerticalLayout();
		this.mainLayout.setSpacing(true);
		setContent(this.mainLayout);
	}

	@Override
	public void enter() {
		this.mainLayout.removeAllComponents();
		this.mainLayout.addComponent(new Label("Welcome to WebCad"));

		HorizontalLayout topLayout = new HorizontalLayout();
		this.mainLayout.addComponent(topLayout);

		topLayout.addComponent(new NeclusStatusIndicator());
	}

}

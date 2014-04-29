package diploma.webcad.view.pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.core.service.NeclusService;
import diploma.webcad.listener.RedirectListener;
import diploma.webcad.view.pages.test.TestPage;

@Component
@Scope("prototype")
@VaadinView(MainPage.NAME)
public class MainPage extends AbstractPage {

	private static final long serialVersionUID = 1762911330007195607L;

	public static final String NAME = "";

	private static Logger log = LoggerFactory.getLogger(MainPage.class);
	
	@Autowired
	private NeclusService sshManager;

	private VerticalLayout mainLayout;
	
	public MainPage () {
		this.mainLayout = new VerticalLayout();
		this.mainLayout.setSpacing(true);
		setContent(this.mainLayout);
	}

	@Override
	public void enter() {
		this.mainLayout.removeAllComponents();
		this.mainLayout.addComponent(new Label("Welcome to WebCad"));
		
		mainLayout.addComponent(new Button("Go to testpage", new RedirectListener(TestPage.NAME)));	
	}

}
 
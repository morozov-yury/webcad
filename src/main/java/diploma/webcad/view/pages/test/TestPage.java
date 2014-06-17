package diploma.webcad.view.pages.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.listener.RedirectListener;
import diploma.webcad.view.pages.AbstractPage;

@Component
@Scope("prototype")
@VaadinView(TestPage.NAME)
public class TestPage extends AbstractPage {

	private static final long serialVersionUID = -3248633686792662696L;

	public static final String NAME = "testpage";

	private static Logger log = LoggerFactory.getLogger(TestPage.class);
	
	private VerticalLayout mainLayout;
	
	public TestPage () {
		super("Test page");
		this.mainLayout = new VerticalLayout();
		this.mainLayout.setSpacing(true);
	}
	
	@Override
	public void enter() {
		this.mainLayout.removeAllComponents();		
		Button genaButton = new Button("Gena", new RedirectListener("genarun"));
		mainLayout.addComponent(genaButton);
	}

}

package diploma.webcad.view.pages.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

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
		this.mainLayout = new VerticalLayout();
		this.mainLayout.setSpacing(true);
		setContent(this.mainLayout);
	}
	
	@Override
	public void enter() {
		this.mainLayout.removeAllComponents();
		this.mainLayout.addComponent(new Label("Test page"));
	}

}

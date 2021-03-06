package diploma.webcad.view.pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.ui.HorizontalLayout;

@org.springframework.stereotype.Component
@Scope("prototype")
@VaadinView(MainPage.NAME)
public class MainPage extends AbstractPage {

	private static final long serialVersionUID = 1762911330007195607L;

	public static final String NAME = "";

	private static Logger log = LoggerFactory.getLogger(MainPage.class);

	public MainPage () {
		super("Welcome to WebCad");
	}

	@Override
	public void enter() {
		HorizontalLayout content = new HorizontalLayout();
		content.setSizeFull();

		setContent(content);
	}
	
}
 
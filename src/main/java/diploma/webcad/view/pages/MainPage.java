package diploma.webcad.view.pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@Component
@Scope("prototype")
@VaadinView(MainPage.NAME)
public class MainPage extends AbstractPage {

	private static final long serialVersionUID = 1762911330007195607L;

	public static final String NAME = "";

	private static Logger log = LoggerFactory.getLogger(MainPage.class);

	private VerticalLayout mainLayout;
	
	private ThemeResource pageIconResource;
	
	public MainPage () {
		super("Welcome to WebCad");
		this.mainLayout = new VerticalLayout();
		this.mainLayout.setSpacing(true);
		setContent(this.mainLayout);
		setPageIcon("img/page/home-caption.png");
	}

	@Override
	public void enter() {
		this.mainLayout.removeAllComponents();
		this.mainLayout.addComponent(new Label("1"));
		this.mainLayout.addComponent(new Label("2"));
		this.mainLayout.addComponent(new Label("3"));
		this.mainLayout.addComponent(new Label("4"));
		this.mainLayout.addComponent(new Label("5"));
	}

}
 
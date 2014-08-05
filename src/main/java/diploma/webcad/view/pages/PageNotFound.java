package diploma.webcad.view.pages;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.ui.Label;

@Component
@Scope("prototype")
@VaadinView(PageNotFound.NAME)
public class PageNotFound extends AbstractPage {

	private static final long serialVersionUID = 4427966618312545046L;
	
	public static final String NAME = "404";
	
	public PageNotFound () {
		super("Page not found");
	}

	@Override
	public void enter() {
		Object object = getPageProperties().get("page");
		Label label = new Label("Page not found: " + object);
		setContent(label);
	}

}

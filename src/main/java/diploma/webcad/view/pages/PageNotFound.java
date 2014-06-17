package diploma.webcad.view.pages;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.ui.Label;

import ru.xpoft.vaadin.VaadinView;

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
		Object object = getPageParameters().get("page");
		Label label = new Label("Page not found: " + object);
		setContent(label);
	}

}

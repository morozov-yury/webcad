package diploma.webcad.view.pages.gena;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.ui.VerticalLayout;

import ru.xpoft.vaadin.VaadinView;
import diploma.webcad.view.pages.AbstractPage;

@Component
@Scope("prototype")
@VaadinView(GenaRun.NAME)
public class GenaRun extends AbstractPage {

	private static final long serialVersionUID = 853654161895648186L;
	
	public static final String NAME = "genarun";
	
	private VerticalLayout mainLayout;
	
	public GenaRun () {
		super("Gena");
		this.mainLayout = new VerticalLayout();
		this.mainLayout.setSpacing(true);
		setContent(this.mainLayout);
	}

	@Override
	public void enter() {
		
	}

}

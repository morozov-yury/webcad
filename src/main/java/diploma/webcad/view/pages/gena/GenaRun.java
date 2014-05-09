package diploma.webcad.view.pages.gena;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.core.service.GenaService;
import diploma.webcad.view.components.gena.GenaParametersSelector;
import diploma.webcad.view.pages.AbstractPage;
import diploma.webcad.view.service.NotificationService;

@Component
@Scope("prototype")
@VaadinView(GenaRun.NAME)
public class GenaRun extends AbstractPage {

	private static final long serialVersionUID = 853654161895648186L;
	
	public static final String NAME = "genarun";
	
	@Autowired
	private GenaService genaService;
	
	@Autowired 
	private NotificationService notificationService;
	
	private VerticalLayout mainLayout;
	
	public GenaRun () {
		super("Gena");
		this.mainLayout = new VerticalLayout();
		this.mainLayout.setSpacing(true);
		setContent(this.mainLayout);
	}

	@Override
	public void enter() {
		final GenaParametersSelector parametersSelector = new GenaParametersSelector();
		mainLayout.addComponent(parametersSelector);
		
		Button runButton = new Button("Run", new Button.ClickListener() {
			private static final long serialVersionUID = -1742466051463419737L;
			@Override
			public void buttonClick(ClickEvent arg0) {
				genaService.run(parametersSelector.getParameters());
			}
		});
		runButton.setIcon(new ThemeResource("img/buttons/play12x12.png"));
		
//		Button getParamsButton = new Button("Parameters", new Button.ClickListener() {
//			private static final long serialVersionUID = -1742466051463419737L;
//			@Override
//			public void buttonClick(ClickEvent arg0) {
//				
//			}
//		});
		
		HorizontalLayout buttomsHorizontalLayout =  new HorizontalLayout(runButton);
		buttomsHorizontalLayout.setSpacing(true);
		mainLayout.addComponent(buttomsHorizontalLayout);
		mainLayout.setComponentAlignment(buttomsHorizontalLayout, Alignment.MIDDLE_RIGHT);
	}

}
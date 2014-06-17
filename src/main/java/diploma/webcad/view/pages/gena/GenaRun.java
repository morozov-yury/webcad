package diploma.webcad.view.pages.gena;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.core.service.GenaService;
import diploma.webcad.view.components.gena.GenaParametersSelector;
import diploma.webcad.view.pages.AbstractPage;
import diploma.webcad.view.service.NotificationService;
import diploma.webcad.view.service.ViewFactory;

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
	
	@Autowired
	private ViewFactory viewFactory;
	
	private VerticalLayout mainLayout;
	
	private TextArea textArea;
	
	public GenaRun () {
		super("Gena");
		this.mainLayout = new VerticalLayout();
		this.mainLayout.setSpacing(true);
	}

	@Override
	public void enter() {
		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();
		
        HorizontalLayout row = new HorizontalLayout();
        row.setSizeFull();
        row.setMargin(new MarginInfo(true, true, false, true));
        row.setSpacing(true);
        content.addComponent(row);
        content.setExpandRatio(row, 1.5f);    
        
        final GenaParametersSelector parametersSelector = new GenaParametersSelector();
        row.addComponent(viewFactory.wrapComponent(parametersSelector));
        
        textArea = new TextArea("Input your xml data here");
        row.addComponent(viewFactory.wrapTextArea(textArea));
		
//		final GenaParametersSelector parametersSelector = new GenaParametersSelector();
//		mainLayout.addComponent(parametersSelector);
//		
//		Button runButton = new Button("Run", new Button.ClickListener() {
//			private static final long serialVersionUID = -1742466051463419737L;
//			@Override
//			public void buttonClick(ClickEvent arg0) {
//				genaService.run(parametersSelector.getParameters());
//			}
//		});
//		runButton.setIcon(new ThemeResource("img/buttons/play12x12.png"));
//		
//		HorizontalLayout buttomsHorizontalLayout =  new HorizontalLayout(runButton);
//		buttomsHorizontalLayout.setSpacing(true);
//		mainLayout.addComponent(buttomsHorizontalLayout);
//		mainLayout.setComponentAlignment(buttomsHorizontalLayout, Alignment.MIDDLE_RIGHT);
        
        setContent(content);
	}

}

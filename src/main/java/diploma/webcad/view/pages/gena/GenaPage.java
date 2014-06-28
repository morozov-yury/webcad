package diploma.webcad.view.pages.gena;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.core.model.User;
import diploma.webcad.core.service.GenaService;
import diploma.webcad.view.WebCadUI;
import diploma.webcad.view.client.component.UpdatableLabel;
import diploma.webcad.view.components.gena.GenaParamSelector;
import diploma.webcad.view.model.gena.GenaParam;
import diploma.webcad.view.model.gena.mm.MealyGenaParam;
import diploma.webcad.view.pages.AbstractPage;
import diploma.webcad.view.service.NotificationService;
import diploma.webcad.view.service.ViewFactory;

@Scope("prototype")
@VaadinView(GenaPage.NAME)
@org.springframework.stereotype.Component
public class GenaPage extends AbstractPage {

	private static final long serialVersionUID = 853654161895648186L;
	
	public static final String NAME = "gena";
	
	@Autowired
	private GenaService genaService;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private ViewFactory viewFactory;

	private VerticalLayout mainLayout;
	
	private TextArea textArea;
	
	public GenaPage () {
		super("Gena");
		this.mainLayout = new VerticalLayout();
		this.mainLayout.setSpacing(true);
	}
	
	@Override
	public void attach() {
		super.attach();
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
        
        final GenaParamSelector parametersSelector = new GenaParamSelector(new MealyGenaParam());
        
        VerticalLayout leftLayout = new VerticalLayout();
        leftLayout.setSizeFull();
        leftLayout.setSpacing(true);
        leftLayout.addComponent(viewFactory.wrapComponent(parametersSelector));
        
        Table table = new Table("Последние запуски");
        
        leftLayout.addComponent(viewFactory.wrapComponent(table));
        row.addComponent(leftLayout);
        
        //row.addComponent(viewFactory.wrapComponent(parametersSelector));
        
        textArea = new TextArea("Input your xml data here");
        row.addComponent(viewFactory.wrapTextArea(textArea));
        
        setContent(content);
        
        UpdatableLabel label = new UpdatableLabel("", 1000) {
			private static final long serialVersionUID = 1L;

			@Override
			public String getUpdatedText() {
				return parametersSelector.getParameters().toString();
			}
		};
		label.addStyleName("info-label");
        
        Button startButton = new Button("Start", new Button.ClickListener() {
			private static final long serialVersionUID = 4434872155184459414L;
			@Override
			public void buttonClick(ClickEvent event) {
				if (textArea.getValue().isEmpty()) {
					notificationService.showError("Error", "Укажите описание алгоритма");
					return;
				}
				GenaParam parameters = parametersSelector.getParameters();
				if (parameters == null) {
					notificationService.showError("Error", "Некорректно указаны параметры запуска");
					return;
				}
				User user = WebCadUI.getCurrent().getSessionState().getUser();
				if (user == null) {
					notificationService.showError("Error", "Вы должны быть залогинены в системе");
					return;
				}
				String xmlDescription = textArea.getValue();
				genaService.createGenaLaunch(user, parameters.toString(), xmlDescription);
			}
		});
        startButton.addStyleName("default");

        addComponentToTop(label);
        addComponentToTop(startButton);
	}

}

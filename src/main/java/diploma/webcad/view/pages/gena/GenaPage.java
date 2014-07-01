package diploma.webcad.view.pages.gena;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.tepi.filtertable.FilterTable;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.core.model.User;
import diploma.webcad.core.model.gena.GenaLaunch;
import diploma.webcad.core.model.gena.GenaResult;
import diploma.webcad.core.service.GenaService;
import diploma.webcad.view.WebCadUI;
import diploma.webcad.view.client.component.Notificator;
import diploma.webcad.view.client.component.UpdatableLabel;
import diploma.webcad.view.components.gena.GenaParamSelector;
import diploma.webcad.view.components.gena.MachineParamsFactory;
import diploma.webcad.view.model.gena.GenaParam;
import diploma.webcad.view.model.gena.mm.MealyGenaParam;
import diploma.webcad.view.pages.AbstractPage;
import diploma.webcad.view.service.ViewFactory;

@Scope("prototype")
@VaadinView(GenaPage.NAME)
@org.springframework.stereotype.Component
public class GenaPage extends AbstractPage {

	private static final long serialVersionUID = 853654161895648186L;
	
	public static final String NAME = "gena";
	
	private static Logger log = LoggerFactory.getLogger(GenaPage.class);
	
	@Autowired
	private GenaService genaService;
	
	@Autowired
	private ViewFactory viewFactory;

	private VerticalLayout mainLayout;
	
	private TextArea textArea;

	private User user;
	
	public GenaPage () {
		super("Gena");
		this.mainLayout = new VerticalLayout();
		this.mainLayout.setSpacing(true);
	}
	
	@Override
	public void attach() {
		user = WebCadUI.getCurrent().getSessionState().getUser();
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
        
        final VerticalLayout leftLayout = new VerticalLayout();
        leftLayout.setSizeFull();
        leftLayout.setSpacing(true);
        Component wrapperParamelector = viewFactory.wrapComponent(parametersSelector);
        leftLayout.addComponent(wrapperParamelector);
        
        List<GenaLaunch> allLaunches = genaService.listAllLaunches(user);
        FilterTable launchesTable = viewFactory.getGenaLaunchesTable(allLaunches);
        launchesTable.setCaption("Last launches");
        launchesTable.addItemClickListener(new ItemClickListener() {
			private static final long serialVersionUID = 6592228933748021923L;
			@Override
			public void itemClick(ItemClickEvent event) {
				GenaLaunch genaLaunch = (GenaLaunch) event.getItemId();
				log.info("Item id = {}", genaLaunch.getId());
				GenaParam genaParamByToken = MachineParamsFactory.getGenaParamByToken(
						genaLaunch.getGenaParams());
				
				GenaParamSelector genaParamSelector = new GenaParamSelector(genaParamByToken);
				Component wrappedComponent = viewFactory.wrapComponent(genaParamSelector);
				leftLayout.removeComponent(leftLayout.getComponent(0));
				leftLayout.addComponent(wrappedComponent, 0);
			}
		});
        leftLayout.addComponent(viewFactory.wrapComponent(launchesTable));
        row.addComponent(leftLayout);
        
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
				Notificator notificator = WebCadUI.getCurrent().getNotificator();
				
				if (textArea.getValue().isEmpty()) {
					notificator.showError("Error", "Укажите описание алгоритма");
					return;
				}
				GenaParam parameters = parametersSelector.getParameters();
				if (parameters == null) {
					notificator.showError("Error", "Некорректно указаны параметры запуска");
					return;
				}
				User user = WebCadUI.getCurrent().getSessionState().getUser();
				if (user == null) {
					notificator.showError("Error", "Вы должны быть залогинены в системе");
					return;
				}
				String xmlDescription = textArea.getValue();
				GenaLaunch genaLaunch = genaService.createGenaLaunch(user, parameters.toString(), 
						xmlDescription);
				GenaResult genaResult = genaLaunch.getGenaResult();
				notificator.showInfo("Launch result: " + genaResult.getGenaResultStatus());
			}
		});
        startButton.addStyleName("default");

        addComponentToTop(label);
        addComponentToTop(startButton);
	}

}

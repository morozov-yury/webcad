package diploma.webcad.view.pages.gena;

import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.tepi.filtertable.FilterTable;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.data.Container;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.core.model.User;
import diploma.webcad.core.model.gena.GenaLaunch;
import diploma.webcad.core.model.gena.GenaResultStatus;
import diploma.webcad.core.model.resource.FSResource;
import diploma.webcad.core.service.FSResourceService;
import diploma.webcad.core.service.GenaService;
import diploma.webcad.core.util.date.DateUtils;
import diploma.webcad.view.WebCadUI;
import diploma.webcad.view.client.component.Notificator;
import diploma.webcad.view.client.component.UpdatableLabel;
import diploma.webcad.view.components.gena.GenaParamSelector;
import diploma.webcad.view.components.gena.MachineParamsFactory;
import diploma.webcad.view.model.gena.GenaParam;
import diploma.webcad.view.model.gena.mm.MealyGenaParam;
import diploma.webcad.view.pages.AbstractPage;
import diploma.webcad.view.service.UIUtils;
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
	
	@Autowired
	private FSResourceService fsrService;

	private VerticalLayout mainLayout;
	
	private TextArea textArea;

	private User user;

	private GenaParamSelector parametersSelector;
	
	private GenaLaunch genaLaunch;
	
	private FileDownloader fileDownloader;

	private FilterTable launchesTable;
	
	private Button downloadButton;
	
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
        
        parametersSelector = new GenaParamSelector(new MealyGenaParam());
        
        final VerticalLayout leftLayout = new VerticalLayout();
        leftLayout.setSizeFull();
        leftLayout.setSpacing(true);
        Component wrapperParamelector = viewFactory.wrapComponent(parametersSelector);
        leftLayout.addComponent(wrapperParamelector);
        
        List<GenaLaunch> allLaunches = genaService.listLastUserLauches(user, 15);
        launchesTable = viewFactory.getGenaLaunchesTable(allLaunches);
        launchesTable.setCaption("Last launches");
        launchesTable.addItemClickListener(new ItemClickListener() {
			private static final long serialVersionUID = 6592228933748021923L;
			@Override
			public void itemClick(ItemClickEvent event) {
				genaLaunch = (GenaLaunch) event.getItemId();
				GenaParam genaParamByToken = MachineParamsFactory.getGenaParamByToken(
						genaLaunch.getGenaParams());
				parametersSelector = new GenaParamSelector(genaParamByToken);
				parametersSelector.setGenaParam(genaParamByToken);
				Component wrappedComponent = viewFactory.wrapComponent(parametersSelector);
				leftLayout.removeComponent(leftLayout.getComponent(0));
				leftLayout.addComponent(wrappedComponent, 0);
				
				FSResource inputResource = genaLaunch.getInputResource();
				byte[] data = fsrService.getData(inputResource);
				if (data != null) {
					String xmlDescription = new String(data);
					textArea.setValue(xmlDescription);
				}
				
				if (genaLaunch.getGenaResultStatus() == GenaResultStatus.SUCCESSFUL) {
					downloadButton.setVisible(true);
					String fileName = DateUtils.formatDateTime(genaLaunch.getCreationDate());
					fileName = genaLaunch.getId() + "_" + fileName + ".zip";
					InputStream zipFSResource = fsrService.zipFSResource(genaLaunch.getResultResource());
					StreamResource sResource = UIUtils.createResource(zipFSResource, fileName);
					if (fileDownloader == null) {
						fileDownloader = new FileDownloader(sResource);
						fileDownloader.extend(downloadButton);
					} else {
						fileDownloader.setFileDownloadResource(sResource);
					}
				} else {
					downloadButton.setVisible(false);
				}
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
				if (getGenaParam() == null) {
					return "null";
				}
				return getGenaParam().toString();
			}
		};
		label.addStyleName("info-label");
		
		downloadButton = new Button("Download", new Button.ClickListener() {
			private static final long serialVersionUID = 4972579964941549591L;
			@Override
			public void buttonClick(ClickEvent event) {
				String fileName = DateUtils.formatDateTime(genaLaunch.getCreationDate());
				fileName = genaLaunch.getId() + "_" + fileName + ".zip";
				InputStream zipFSResource = fsrService.zipFSResource(genaLaunch.getResultResource());
				StreamResource sResource = UIUtils.createResource(zipFSResource, fileName);
				if (fileDownloader == null) {
					fileDownloader = new FileDownloader(sResource);
					fileDownloader.extend(downloadButton);
				} else {
					fileDownloader.setFileDownloadResource(sResource);
				}
			}
		});
		downloadButton.setVisible(false);

        Button startButton = new Button("Start", new Button.ClickListener() {
			private static final long serialVersionUID = 4434872155184459414L;
			@Override
			public void buttonClick(ClickEvent event) {
				Notificator notificator = WebCadUI.getCurrent().getNotificator();
				
				if (textArea.getValue().isEmpty()) {
					notificator.showError("Error", "Укажите описание алгоритма");
					return;
				}
				GenaParam parameters = getGenaParam();
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
				genaLaunch = genaService.createGenaLaunch(user, parameters.toString(), 
						xmlDescription);
				
				GenaResultStatus genaResultStatus = genaLaunch.getGenaResultStatus();
				notificator.showInfo("Launch result: " + genaResultStatus);
				
				if (genaResultStatus == GenaResultStatus.SUCCESSFUL) {
					downloadButton.setVisible(true);
					String fileName = DateUtils.formatDateTime(genaLaunch.getCreationDate());
					fileName = genaLaunch.getId() + "_" + fileName + ".zip";
					
					InputStream zipFSResource = fsrService.zipFSResource(genaLaunch.getResultResource());
					StreamResource sResource = UIUtils.createResource(zipFSResource, fileName);
					if (fileDownloader == null) {
						fileDownloader = new FileDownloader(sResource);
						fileDownloader.extend(downloadButton);
					} else {
						fileDownloader.setFileDownloadResource(sResource);
					}
				} else {
					downloadButton.setVisible(false);
				}
				//launchesTable.removeAllItems();
				List<GenaLaunch> userLauches = genaService.listLastUserLauches(user, 15);
				Container container = viewFactory.getTableContainer(userLauches);
				launchesTable.setContainerDataSource(container);
			}
		});
        startButton.addStyleName("default");

        addComponentToTop(label);
        addComponentToTop(downloadButton);
        addComponentToTop(startButton);
	}
	
	private GenaParam getGenaParam () {
		return parametersSelector.getParameters();
	}

}

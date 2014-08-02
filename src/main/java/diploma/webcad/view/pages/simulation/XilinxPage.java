package diploma.webcad.view.pages.simulation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.tepi.filtertable.FilterTable;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Container.ItemSetChangeListener;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.core.model.User;
import diploma.webcad.core.model.simulation.Device;
import diploma.webcad.core.model.simulation.GenaLaunch;
import diploma.webcad.core.service.GenaService;
import diploma.webcad.core.service.XilinxService;
import diploma.webcad.view.WebCadUI;
import diploma.webcad.view.components.DeviceSelector;
import diploma.webcad.view.pages.AbstractPage;
import diploma.webcad.view.service.ViewFactory;

@org.springframework.stereotype.Component
@Scope("prototype")
@VaadinView(XilinxPage.NAME)
public class XilinxPage extends AbstractPage {

	private static final long serialVersionUID = -3515330767966739841L;

	public static final String NAME = "xilinx";
	
	private static Logger log = LoggerFactory.getLogger(XilinxPage.class);

	@Autowired
	private ViewFactory viewFactory;

	@Autowired
	private XilinxService xilinxService;
	
	@Autowired
	private GenaService genaService;

	private User user;
	
	private FilterTable launchesTable;

	private DeviceSelector deviceSelector;

	private Button startButton;
	
	public XilinxPage() {
		super("Xilinx");
	}

	@Override
	public void attach() {
		user = WebCadUI.getCurrent().getSessionState().getUser();
		super.attach();
	}
	
	@Override
	@SuppressWarnings("serial")
	public void enter() {
		HorizontalLayout content = new HorizontalLayout();
		content.setSizeFull();
		content.setSpacing(true);
		
		List<Device> devices = xilinxService.listDevices();
		deviceSelector = new DeviceSelector(viewFactory.getDevicesContainer(devices));
		deviceSelector.setCaption("Drag devices for modelling");
		Component deviceSelectorWrapper = viewFactory.wrapComponent(deviceSelector);
		deviceSelectorWrapper.setWidth("100%");
		deviceSelectorWrapper.setHeight("100%");
		content.addComponent(deviceSelectorWrapper);
		
		final List<GenaLaunch> allLaunches = genaService.listLastUserLauches(user, 15);
		
		launchesTable = viewFactory.getGenaLaunchesTable(allLaunches);
		launchesTable.setCaption("Last launches");
		launchesTable.setMultiSelect(false);
		final Component launchesTableWrapper = viewFactory.wrapComponent(launchesTable);
		final VerticalLayout actionLayout = new VerticalLayout();
		final Component actionLayoutWrapper = viewFactory.wrapComponent(actionLayout);
		content.addComponent(new VerticalLayout() {{
			setSpacing(true);
			setSizeFull();
			addComponent(launchesTableWrapper);
			actionLayout.setCaption("Properties");
			actionLayout.setSizeFull();
			addComponent(actionLayoutWrapper);
			setExpandRatio(launchesTableWrapper, 1);
			setExpandRatio(actionLayoutWrapper, 2);
		}});
		
		deviceSelector.addItemSetChangeListener(new ItemSetChangeListener() {
			@Override
			public void containerItemSetChange(ItemSetChangeEvent event) {
				handleSelection();
			}
		});
		
		launchesTable.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				handleSelection();
			}
		});
		
		startButton = new Button("Start", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				List<String> selectedDevicesNames = deviceSelector.listSelectedDevices();
				List<Device> devices = xilinxService.listDevises(selectedDevicesNames);
				GenaLaunch genaLaunch = (GenaLaunch) launchesTable.getValue();
				
				xilinxService.createBatchSimulation(genaLaunch, devices, user);
			}
		});
		startButton.addStyleName("default");
		startButton.setVisible(false);
		
		addComponentToTop(startButton);

		setContent(content);
	}
	
	private void handleSelection () {
		startButton.setVisible(deviceSelector.listSelectedDevices().size() != 0 
				&& launchesTable.getValue() != null);
	}

}

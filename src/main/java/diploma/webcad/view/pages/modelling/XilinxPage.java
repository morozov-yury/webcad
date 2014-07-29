package diploma.webcad.view.pages.modelling;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.tepi.filtertable.FilterTable;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.core.model.User;
import diploma.webcad.core.model.modelling.Device;
import diploma.webcad.core.model.modelling.GenaLaunch;
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
		DeviceSelector deviceSelector = new DeviceSelector(viewFactory.getDevicesContainer(devices));
		deviceSelector.setCaption("Drag devices for modelling");
		Component deviceSelectorWrapper = viewFactory.wrapComponent(deviceSelector);
		deviceSelectorWrapper.setWidth("100%");
		deviceSelectorWrapper.setHeight("100%");
		content.addComponent(deviceSelectorWrapper);
		
		final List<GenaLaunch> allLaunches = genaService.listLastUserLauches(user, 15);
		
		content.addComponent(new VerticalLayout() {{
			setSpacing(true);
			setSizeFull();
			
			FilterTable launchesTable = viewFactory.getGenaLaunchesTable(allLaunches);
			launchesTable.setCaption("Last launches");
			Component launchesTableWrapper = viewFactory.wrapComponent(launchesTable);
			addComponent(launchesTableWrapper);
			
			VerticalLayout actionLayout = new VerticalLayout();
			actionLayout.setCaption("Properties");
			actionLayout.setSizeFull();
			Component actionLayoutWrapper = viewFactory.wrapComponent(actionLayout);
			addComponent(actionLayoutWrapper);
			
			setExpandRatio(launchesTableWrapper, 1);
			setExpandRatio(actionLayoutWrapper, 2);
			
			
			
		}});
		
		setContent(content);
	}

}

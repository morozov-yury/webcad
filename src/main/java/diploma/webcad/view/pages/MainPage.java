package diploma.webcad.view.pages;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.core.dao.FileResourceDao;
import diploma.webcad.core.model.modelling.Device;
import diploma.webcad.core.service.SshService;
import diploma.webcad.core.service.XilinxService;
import diploma.webcad.view.components.DeviceSelector;
import diploma.webcad.view.service.ViewFactory;

@org.springframework.stereotype.Component
@Scope("prototype")
@VaadinView(MainPage.NAME)
public class MainPage extends AbstractPage {

	private static final long serialVersionUID = 1762911330007195607L;

	public static final String NAME = "";

	private static Logger log = LoggerFactory.getLogger(MainPage.class);
	
	@Autowired
	private ViewFactory viewFactory;
	
	@Autowired
	private SshService sshService;
	
	@Autowired
	private FileResourceDao fileResourceDao;
	
	@Autowired
	private XilinxService xilinxService;

	public MainPage () {
		super("Welcome to WebCad");
	}


	@Override
	public void enter() {
		HorizontalLayout content = new HorizontalLayout();
		content.setSizeFull();
		
		List<Device> devices = xilinxService.listDevices();
		DeviceSelector deviceSelector = new DeviceSelector(viewFactory.getDevicesContainer(devices));
		deviceSelector.setCaption("Drag devices for modelling");
		Component deviceSelectorWrapper = viewFactory.wrapComponent(deviceSelector);
		deviceSelectorWrapper.setWidth("50%");
		deviceSelectorWrapper.setHeight("100%");
		content.addComponent(deviceSelectorWrapper);
		
		setContent(content);
	}
	
}
 
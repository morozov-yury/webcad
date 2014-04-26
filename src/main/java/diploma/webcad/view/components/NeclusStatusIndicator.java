package diploma.webcad.view.components;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Image;

import diploma.webcad.core.init.SpringContextHelper;
import diploma.webcad.core.service.NeclusService;
import diploma.webcad.view.WebCadUI;

public class NeclusStatusIndicator extends FormLayout {

	private static final long serialVersionUID = -8068972960887272748L;
	
	private static Logger log = LoggerFactory.getLogger(NeclusStatusIndicator.class);
	
	private SpringContextHelper contextHelper;
	
	private NeclusService neclusManager;
	
	Image statusImage = null;
	
	ThemeResource statusImageResourceOn = null;
	
	ThemeResource statusImageResourceOff = null;
	
	public NeclusStatusIndicator () {
		
	}
	
	@Override
	public void attach() {
		removeAllComponents();
		
		contextHelper = WebCadUI.getCurrent().getSessionState().getHelper();
		neclusManager = contextHelper.getBean(NeclusService.class);
		
		statusImageResourceOn = new ThemeResource("img/webcad/neclus_on.png");
		statusImageResourceOff = new ThemeResource("img/webcad/neclus_off.png");
		
		statusImage = new Image("Neclus status");
		statusImage.addStyleName("neclus-status-image");
		addComponent(statusImage);

		updateStatus();
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				updateStatus();
		    }
		};
		timer.schedule(task, 1000, 1000);
	}



	private void updateStatus () {
		statusImage.setSource((neclusManager.isNeclusOnline()) ? statusImageResourceOn : statusImageResourceOff);
		markAsDirtyRecursive();
		WebCadUI.getCurrent().push();
	}

}

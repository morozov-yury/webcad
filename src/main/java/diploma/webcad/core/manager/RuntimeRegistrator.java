package diploma.webcad.core.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.vaadin.ui.UI;

import diploma.webcad.core.model.User;
import diploma.webcad.view.WebCadUI;

@Service
@Scope("singleton")
public class RuntimeRegistrator {
	private Logger log = Logger.getLogger(RuntimeRegistrator.class);
	
	private Map<Long, Set<WebCadUI>> online;
	
	@Autowired
	private UserManager userManager;

	private Map<Long, Set<WebCadUI>> getOnline() {
		if (online == null) {
			online = new HashMap<Long, Set<WebCadUI>>();
		}
		return online;
	}
	
	public void registerUser(User user, UI ui) {
		log.debug("User registration: " + user);
		
	}


	public void removeUser(User user, UI ui) {
		log.debug("User unregistration: "+user);
		
	}


}

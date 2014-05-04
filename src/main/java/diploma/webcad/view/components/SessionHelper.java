package diploma.webcad.view.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import diploma.webcad.core.model.resource.ApplicationResource;
import diploma.webcad.core.service.ContentService;
import diploma.webcad.core.service.RuntimeRegistrator;
import diploma.webcad.core.service.SessionState;
import diploma.webcad.core.service.TemplateService;
import diploma.webcad.core.service.UserService;

@Component
@Scope("session")
public class SessionHelper {

	@Autowired
	private SessionState sessionState;
	
	@Autowired
	private ContentService contentManager;
	
	@Autowired
	private UserService userManager;
	
	@Autowired
	private RuntimeRegistrator runtimeRegistrator;

	@Autowired
	private TemplateService templateManager;

	public String getAppRes(String key) {
		return getContentManager().resolveApplicationResource(key, sessionState.getLanguage());
	}

	public String getAppRes(Object source, String keySuffix) {
		if(!(source instanceof Class))
			return getAppRes(source.getClass(), keySuffix);
		else return getContentManager().resolveApplicationResource((Class<?>) source, keySuffix, sessionState.getLanguage());
	}

	@SuppressWarnings("rawtypes")
	public String getAppRes(Enum value) {
		return getAppRes(value.getClass(), value.name().toLowerCase());
	}

	public String resolveLocalized(ApplicationResource appRes) {
		return getContentManager().resolveApplicationResource(appRes, sessionState.getLanguage());
	}
	
	public SessionState getSessionState() {
		return sessionState;
	}

	public UserService getUserManager() {
		return userManager;
	}

	public ContentService getContentManager() {
		return contentManager;
	}

	public RuntimeRegistrator getRuntimeRegistrator() {
		return runtimeRegistrator;
	}

}

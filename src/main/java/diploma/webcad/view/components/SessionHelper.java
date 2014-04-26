package diploma.webcad.view.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import diploma.webcad.core.model.ApplicationResource;
import diploma.webcad.core.model.Language;
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

	
	/**
	 * Resolves {@link ApplicationResource} by current session {@link Language}
	 * @param key key of {@link ApplicationResource} to resolve
	 * @return value of {@link ApplicationResource} by current session {@link Language}
	 */
	public String getAppRes(String key) {
		return getContentManager().resolveApplicationResource(key, sessionState.getLanguage());
	}

	/**
	 * Resolves {@link ApplicationResource} by current session {@link Language},
	 * using <b>source</b> class name in lower case as a prefix for {@link ApplicationResource} key<br/>
	 * For example getAppRes("abc", "some.suffix") resolves {@link ApplicationResource} with key 
	 * <b>java.lang.string.some.suffix</b>
	 * @param source object to get class name for prefix
	 * @param keySuffix ApplicationResource key suffix
	 * @return value of {@link ApplicationResource} by current session {@link Language}
	 * with key = <b>object_classname.suffix</b>
	 */
	public String getAppRes(Object source, String keySuffix) {
		if(!(source instanceof Class))
			return getAppRes(source.getClass(), keySuffix);
		else return getContentManager().resolveApplicationResource((Class<?>) source, keySuffix, sessionState.getLanguage());
	}
	
	/**
	 * Resolves {@link ApplicationResource} by current session {@link Language},
	 * using <b>value</b> class name in lower case as a prefix
	 * and <b>value</b> enum name in lower case as a suffix for {@link ApplicationResource} key<br/>
	 * For example getAppRes(org.weather.Season.SUMMER) resolves {@link ApplicationResource} with key 
	 * <b>org.weather.season.summer</b>
	 * @param value enum value get class name for prefix and enum name for suffix
	 * @return value of {@link ApplicationResource} by current session {@link Language}
	 * with key = <b>value_classname.value.name()</b>
	 */
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

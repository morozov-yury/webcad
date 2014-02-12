package diploma.webcad.core.manager;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * This class provides an application-wide access to the Spring
 * ApplicationContext!
 */
public class ApplicationContextProvider implements ApplicationContextAware {

	private static ApplicationContext context;

	public static ApplicationContext getApplicationContext() {
		return context;
	}

	@Autowired
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		context = ctx;
	}

}

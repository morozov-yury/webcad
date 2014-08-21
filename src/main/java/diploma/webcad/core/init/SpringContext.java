package diploma.webcad.core.init;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vaadin.server.WrappedHttpSession;
import com.vaadin.ui.UI;

public class SpringContext {
	
	private WebApplicationContext context;
	
	private UI application;
	
	public static SpringContext getInstance(UI application) {
		return new SpringContext(application);
	}

	public SpringContext(UI application) {
		this(((WrappedHttpSession)application.getSession().getSession()).getHttpSession().getServletContext());
	}

	public SpringContext(ServletContext servletContext) {
		context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
	}

	public Object getBean(final String beanRef) {
		try {
			context.getBeanDefinitionNames();
			return context.getBean(beanRef);
		} catch (NoSuchBeanDefinitionException e) {
		}
		return null;
	}
	
	public <T> T getBean(Class<T> clazz) {
		return context.getBean(clazz);
	}

	public Class<?> getType(String name){
		return context.getType(name);
	}
	
	public String[] getBeanDefinitionNames() {
		return context.getBeanDefinitionNames();
	}
	
	public UI getApplicationService() {
		final UI res = UI.getCurrent();
		return res == null ? application : res;
	}

	public void setApplication(UI application) {
		this.application = application;
	}
	
}

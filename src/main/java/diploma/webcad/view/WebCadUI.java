package diploma.webcad.view;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.artur.icepush.ICEPush;

import ru.xpoft.vaadin.DiscoveryNavigator;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.RequestHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.ui.UI;

import diploma.webcad.core.init.SpringContextHelper;
import diploma.webcad.core.manager.RuntimeRegistrator;
import diploma.webcad.core.manager.SessionState;
import diploma.webcad.core.manager.WebCadPropertyManager;
import diploma.webcad.core.model.User;
import diploma.webcad.manager.MappingProcessor;
import diploma.webcad.view.components.SessionHelper;
import diploma.webcad.view.layouts.LandingLayout;
import diploma.webcad.view.tiles.AbstractContentTile;

/**
 * The Application's "main" class
 */
@Component("quizUI")
@Scope("prototype")
@Theme("dashboard")
@SuppressWarnings("serial")
@Push
public class WebCadUI extends UI {

	@Autowired
	private LandingLayout landingLayout;

	@Autowired
	private MappingProcessor mappingProcessor;

	@Autowired
	private SessionState sessionState;
	
	@Autowired
	private WebCadPropertyManager webCadPropertyManager;
	
	@Autowired
	private RuntimeRegistrator runtimeRegistrator;

	@Autowired
	private PropertiesFactoryBean properties;
	
	@Autowired
	private SessionHelper sessionHelper;

	private Map<String, String[]> requestParams;

	private Cookie[] cookies;

	private DiscoveryNavigator navigator;

	private ICEPush icePush = new ICEPush();

	public static WebCadUI getCurrent() {
		return (WebCadUI) UI.getCurrent();
	}

	public WebCadUI() {
		addExtension(icePush);
	}

	@Override
	protected void init(final VaadinRequest request) {
		SpringContextHelper helper = (SpringContextHelper) sessionState
				.getParameter(SessionState.SPRING_HELPER);
		if (helper == null) {
			helper = new SpringContextHelper(this);
			sessionState.putParameter(SessionState.SPRING_HELPER, helper);
		}
		setSizeFull();
		navigator = new DiscoveryNavigator(this, new QuizViewDisplay(
				landingLayout)) {
			@Override
			public void navigateTo(String navigationState) {
				try {
					super.navigateTo(navigationState);
				} catch (Exception e) {
					e.printStackTrace();
					//super.navigateTo(NotFound.NAME);
				}
			}
		};
		setContent(landingLayout);

		navigator.addViewChangeListener(new ViewChangeListener() {

			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {
				return true;
			}

			@Override
			public void afterViewChange(ViewChangeEvent event) {
				AbstractContentTile abstractContentTile = (AbstractContentTile) event
						.getNewView();
				sessionState.putParameter(SessionState.LAST_PATH, "!"
						+ abstractContentTile.getPath());
			}

		});

		getSession().addRequestHandler(new RequestHandler() {
			@Override
			public boolean handleRequest(VaadinSession session,
					VaadinRequest request, VaadinResponse response)
					throws IOException {
				requestParams = request.getParameterMap();
				return false;
			}
		});
		if (cookies == null)
			cookies = request.getCookies();
		
		getPushConfiguration().setPushMode(PushMode.AUTOMATIC);
		setPollInterval(1000);
	}

	public Map<String, String[]> getRequestParams() {
		return requestParams;
	}

	public Cookie[] getCookies() {
		return cookies;
	}

	public void setCookie(String name, String value, long lifeTime) {
		Page.getCurrent()
				.getJavaScript()
				.execute(
						String.format(
								"document.cookie = '%s=%s; expires=%s;';",
								name, value, lifeTime));
	}

	public SessionState getSessionState() {
		return sessionState;
	}

	public String getRequestParameter(String name) {
		return requestParams.get(name)[0];
	}

	public String getCookie(String name) {
		if (cookies == null)
			return null;
		for (Cookie cookie : cookies)
			if (cookie.getName().equals(name))
				return cookie.getValue();
		return null;
	}

	public void push() {
		icePush.push();
	}

	public void processUri(String uri) {
		mappingProcessor.processMapping(uri);
	}

    public String getProperty(String propertyName) {
        return this.webCadPropertyManager.getProperty(propertyName);
    }

	@Override
    public void detach() {
		User user = sessionState.getUser();
		if (user!=null)
			getRuntimeRegistrator().removeUser(user, this);
		super.detach();
	}
	
	public SessionHelper getSessionHelper() {
		return sessionHelper;
	}

	public RuntimeRegistrator getRuntimeRegistrator() {
		return runtimeRegistrator;
	}

}

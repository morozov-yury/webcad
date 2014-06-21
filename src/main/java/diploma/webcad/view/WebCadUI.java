package diploma.webcad.view;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.artur.icepush.ICEPush;

import ru.xpoft.vaadin.DiscoveryNavigator;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.RequestHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.UI;

import diploma.webcad.core.init.SpringContext;
import diploma.webcad.core.model.User;
import diploma.webcad.core.service.ContentService;
import diploma.webcad.core.service.RuntimeRegistrator;
import diploma.webcad.core.service.SessionInterlayer;
import diploma.webcad.core.service.SessionState;
import diploma.webcad.view.layouts.MainLayout;
import diploma.webcad.view.model.PageProperties;
import diploma.webcad.view.pages.PageNotFound;

@Component("webCadUI")
@Title("WebCad")
@Scope("prototype")
@Theme("dashboard")
@SuppressWarnings("serial")
@Push
public class WebCadUI extends UI {
	
	private static Logger log = LoggerFactory.getLogger(WebCadUI.class);

	@Autowired
	private MainLayout mainLayout;
	
	@Autowired
	private SessionState sessionState;
	
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
		log.info("UI init");
		
		SpringContext springContext = (SpringContext) sessionState.getParameter(SessionState.Param.SPRING_CONTEXT);
		if (springContext == null) {
			springContext = new SpringContext(this);
			sessionState.putParameter(SessionState.Param.SPRING_CONTEXT, springContext);
		}
		initSessionInterlayer(springContext);
		
		setContent(mainLayout);
		setSizeFull();

		navigator = new DiscoveryNavigator(this, new WebCadViewDisplay(mainLayout)) {
			@Override
			public void navigateTo(String navigationState) {
				try {
					super.navigateTo(navigationState);
				} catch (Exception e) {
					e.printStackTrace();
					PageProperties properties = new PageProperties();
					properties.put("page", navigationState);
					processUri(PageNotFound.NAME, properties);
				}
			}
		};
		
		setNavigator(navigator);

		navigator.addViewChangeListener(new ViewChangeListener() {

			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {
				return true;
			}

			@Override
			public void afterViewChange(ViewChangeEvent event) {
				
			}

		});

		getSession().addRequestHandler(new RequestHandler() {
			@Override
			public boolean handleRequest(VaadinSession session, VaadinRequest request, VaadinResponse response)
					throws IOException {
				requestParams = request.getParameterMap();
				return false;
			}
		});
		
		if (cookies == null) {
			cookies = request.getCookies();
		}
		
		getPushConfiguration().setPushMode(PushMode.AUTOMATIC);
		setPollInterval(1000);
	}
	
	private void initSessionInterlayer (SpringContext springContext) {
		SessionInterlayer sessionInterlayer = springContext.getBean(SessionInterlayer.class);
		sessionInterlayer.setRuntimeRegistrator(springContext.getBean(RuntimeRegistrator.class));
		sessionInterlayer.setSessionState(springContext.getBean(SessionState.class));
		sessionInterlayer.setContentService(springContext.getBean(ContentService.class));
	}

	public Map<String, String[]> getRequestParams() {
		return requestParams;
	}

	public Cookie[] getCookies() {
		return cookies;
	}

	public void setCookie(String name, String value, long lifeTime) {
		JavaScript javaScript = Page.getCurrent().getJavaScript();
		String format = String.format("document.cookie = '%s=%s; expires=%s;';", name, value, lifeTime);
		javaScript.execute(format);
	}

	public SessionState getSessionState() {
		return sessionState;
	}

	public String getRequestParameter(String name) {
		return requestParams.get(name)[0];
	}

	public String getCookie(String name) {
		if (cookies == null) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return cookie.getValue();
			}
		}
		return null;
	}

	public void push() {
		icePush.push();
	}

	public void processUri(String uri) {
		getNavigator().navigateTo(uri);
	}
	
	public void processUri(String uri, PageProperties pageProperties) {
		if (pageProperties == null) {
			getNavigator().navigateTo(uri);
		}
		getNavigator().navigateTo(uri + pageProperties);
	}

	@Override
    public void detach() {
		log.info("UI {} detached", this);
		User user = sessionState.getUser();
		if (user != null) {
			//getRuntimeRegistrator().removeUser(user, this);
		}
		super.detach();
	}

	public RuntimeRegistrator getRuntimeRegistrator() {
		return null;
	}
	
}

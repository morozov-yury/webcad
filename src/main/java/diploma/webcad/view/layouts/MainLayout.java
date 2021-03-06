package diploma.webcad.view.layouts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

import com.vaadin.navigator.View;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.core.init.SpringContext;
import diploma.webcad.core.model.User;
import diploma.webcad.view.Layout;
import diploma.webcad.view.WebCadUI;
import diploma.webcad.view.components.HelpOverlay;
import diploma.webcad.view.dash.UserMenu;

@org.springframework.stereotype.Component	
@Scope("prototype")
public class MainLayout extends CssLayout implements Layout {

	private static final long serialVersionUID = 1L;
	
	private static Logger log = LoggerFactory.getLogger(MainLayout.class);

	private View currentView = null;
	
	HelpOverlay greetingWindow;
	
	private CssLayout content;

	private Component contentLayout;
	
	private VerticalLayout mainMenuWrapper;

	private Label bg;

	public MainLayout() {
		addStyleName("main-layout");
		setSizeFull();
		
		addStyleName("root");
        setSizeFull();
        
        content = new CssLayout();
        content.setSizeFull();
        content.addStyleName("view-content");
        
        greetingWindow =  new HelpOverlay(
                "Добро пожаловать в WebCad",
                "<p>Это система распределенного автоматизированного проектирования цифровых устройсв, которое облегчит решения ваших научно-исследовательских задач.</p><p>Для получения доступа свяжитесь с <a href=\"https://vk.com/morozov.yury\">автором</a> или <a href=\"http://cs.dgtu.donetsk.ua/~miroshkin/index.htm\">администратором</a>.</p>",
                "login");
		greetingWindow.center();
		greetingWindow.setVisible(false);
		
		contentLayout = getContentLayout();
		
		bg = new Label();
        bg.setSizeUndefined();
        bg.addStyleName("login-bg");
	}
	
	@Override
	public void attach() {
		WebCadUI ui = WebCadUI.getCurrent();
		ui.addWindow(greetingWindow);
		
		mainMenuWrapper.addComponent(ui.getMainMenu());
        super.attach();
	}

	@Override
	public void repaint() {
		removeAllComponents();
		
		addComponent(bg);
		
		SpringContext springContext = WebCadUI.getCurrent().getSessionState().getContext();
		LoginLayout loginLayout = springContext.getBean(LoginLayout.class);
		
		User currUser = WebCadUI.getCurrent().getSessionState().getUser();
		if (currUser == null) {
			greetingWindow.setVisible(true);
			addComponent(loginLayout);
			return;
		} else {
		}
		
		greetingWindow.setVisible(false);
		
		addComponent(contentLayout);
		
		content.removeAllComponents();
		
		if (currentView != null) {
			content.addComponent((Component)currentView);
			content.addComponent(WebCadUI.getCurrent().getNotificator());
		}
		
		
	}

	@Override
	public void setContent(View view) {
		currentView = view;
		if (currentView != null) {
			content.removeAllComponents();
			content.addComponent((Component)currentView);
		}
		repaint();
	}

	@Override
	public View getContent() {
		return currentView;
	}
	
	private Component getContentLayout () {
		return new HorizontalLayout() {
            { 
                setSizeFull();
                addStyleName("main-view");
 
                addComponent(new VerticalLayout() {
                    {
                        addStyleName("sidebar");
                        setWidth(null);
                        setHeight("100%");

                        addComponent(new CssLayout() {
                            {
                                addStyleName("branding");
                                Label logo = new Label("<span>distributed</span> WebCad",ContentMode.HTML);
                                logo.setSizeUndefined();
                                addComponent(logo);
                            }
                        });

                        addComponent(new UserMenu());
                        mainMenuWrapper = new VerticalLayout();
                        addComponent(mainMenuWrapper);
                        setExpandRatio(mainMenuWrapper, 1);
                    }
                });
                addComponent(content);
                setExpandRatio(content, 1);
            }

        };
	}

}

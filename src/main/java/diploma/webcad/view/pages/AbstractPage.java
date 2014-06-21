package diploma.webcad.view.pages;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.demo.dashboard.TopGrossingMoviesChart;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import diploma.webcad.core.util.http.HttpUtils;
import diploma.webcad.view.WebCadUI;
import diploma.webcad.view.model.PageProperties;
import diploma.webcad.view.service.NotificationService;

@SuppressWarnings("serial")
public abstract class AbstractPage extends Panel implements View {
	
	private static Logger log = LoggerFactory.getLogger(AbstractPage.class);
	
	@Autowired
	private NotificationService notificationService;

	private PageProperties pageProperties;

	private VerticalLayout content;

	private String caption;

	private HorizontalLayout topLayout;

	public AbstractPage(String caption) {
		this.caption = caption;
		setSizeFull();
		addStyleName("webcad-abstract-page");
		
		content = new VerticalLayout();
		super.setContent(content);
		content.setSizeFull();
		content.setStyleName("dashboard-view");
		
		topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.setSpacing(true);
        topLayout.addStyleName("toolbar");
        content.addComponent(topLayout);
        content.setExpandRatio(topLayout, 0);
        final Label title = new Label(caption);
        title.setSizeUndefined();
        title.addStyleName("h1");
        topLayout.addComponent(title);
        topLayout.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
        topLayout.setExpandRatio(title, 1);
	}

	@Override
	public void setContent(Component content) {
		if (content != null) {
			this.content.addComponent(content);
			this.content.setExpandRatio(content, 2);
		}
	}
	
	public void addComponentToTop (Component content) {
		topLayout.addComponent(content);
        topLayout.setComponentAlignment(content, Alignment.MIDDLE_LEFT);
	}

	public abstract void enter();
	
	@Override
	final public void enter(ViewChangeEvent event) {
		String parameters = event.getParameters();
		Properties urlProperties = HttpUtils.getUrlProperties(parameters);
		pageProperties = new PageProperties(urlProperties);
		if (isAccessAvailable()) {
			enter();
			notificationService.showInfo(getPageLocation() + getPageParameters());
		} else {
			WebCadUI.getCurrent().processUri("403");
		}
	}

	protected boolean isAccessAvailable () {
		return true;
	}

	protected String getPageLocation() {
		VaadinView vaadinViewAnnotation = getClass().getAnnotation(VaadinView.class);
		if (vaadinViewAnnotation == null) {
			return null;
		}
		return vaadinViewAnnotation.value();
	}

	protected PageProperties getPageParameters() {
		return pageProperties;
	}

	protected void refreshPage() {
		WebCadUI.getCurrent().processUri(getPageLocation(), getPageParameters());
	}
	
}
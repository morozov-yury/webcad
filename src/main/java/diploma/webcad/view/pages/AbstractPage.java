package diploma.webcad.view.pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.core.util.http.HttpUtils;
import diploma.webcad.view.WebCadUI;
import diploma.webcad.view.components.HorizontalSeparator;
import diploma.webcad.view.model.PageProperties;
import diploma.webcad.view.service.NotificationService;

@SuppressWarnings("serial")
public abstract class AbstractPage extends Panel implements View {
	
	private static Logger log = LoggerFactory.getLogger(AbstractPage.class);
	
	@Autowired
	private NotificationService notificationService;

	private PageProperties pageProperties;

	private String caption;

	Image pageIcon;

	public AbstractPage(String caption) {
		this.caption = caption;
		setSizeUndefined();
		addStyleName("webcad-abstract-page");
	}

	public abstract void enter();
	
	@Override
	final public void enter(ViewChangeEvent event) {
		pageProperties = new PageProperties(HttpUtils.getUrlProperties(event.getParameters()));
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
	
	public void setPageIcon (String imagePath) {
		pageIcon.setSource(new ThemeResource(imagePath));
		pageIcon.markAsDirty();
	}

	@Override
	public void setContent(Component content) {
		if (content == null) {
			super.setContent(content);
			return;
		}
		HorizontalLayout captionLayout = new HorizontalLayout();
		captionLayout.addStyleName("page-caption");
		this.pageIcon = new Image(null);
		ThemeResource defaultIcon = new ThemeResource("img/page/default-icon.png");
		pageIcon.setSource(defaultIcon);
		captionLayout.addComponent(pageIcon);
		captionLayout.addComponent(new Label(this.caption));
		
		VerticalLayout mainContent = new VerticalLayout(captionLayout, new HorizontalSeparator());
		mainContent.addStyleName("page-content");
		content.addStyleName("full-width");
		mainContent.addComponent(content);
		mainContent.addComponent(new HorizontalSeparator());
		
		super.setContent(mainContent);
	}

	
}
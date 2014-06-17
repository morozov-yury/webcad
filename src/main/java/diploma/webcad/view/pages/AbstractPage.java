package diploma.webcad.view.pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Panel;

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

	public AbstractPage() {
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
	
}
package diploma.webcad.view.pages;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.core.util.http.HttpUtils;
import diploma.webcad.view.WebCadUI;
import diploma.webcad.view.model.PageProperties;

@SuppressWarnings("serial")
public abstract class AbstractPage extends Panel implements View {
	
	private static Logger log = LoggerFactory.getLogger(AbstractPage.class);

	private PageProperties pageProperties;

	private VerticalLayout content;

	private HorizontalLayout topLayout;

	public AbstractPage(String caption) {
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
	public void attach() {
		super.attach();
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
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public void enter(ViewChangeEvent event) {
		Properties urlProperties = HttpUtils.getUrlProperties(event.getParameters());
		pageProperties = new PageProperties(urlProperties);
		if (isAccessAvailable()) {
			enter();
		} else {
			WebCadUI.getCurrent().navigateTo("403");
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

	protected PageProperties getPageProperties() {
		return pageProperties;
	}

	protected void refreshPage() {
		WebCadUI.getCurrent().navigateTo(getPageLocation(), getPageProperties());
	}
	
}
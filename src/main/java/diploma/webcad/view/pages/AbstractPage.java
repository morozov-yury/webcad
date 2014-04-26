package diploma.webcad.view.pages;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;

import diploma.webcad.core.service.SessionState;
import diploma.webcad.manager.MappingProcessor;
import diploma.webcad.view.WebCadUI;
import diploma.webcad.view.components.SessionHelper;

@SuppressWarnings("serial")
public abstract class AbstractPage extends Panel implements View {
	
	private static Logger log = LoggerFactory.getLogger(AbstractPage.class);

	@Autowired
	private SessionState sessionState;

	@Autowired
	private MappingProcessor mappingProcessor;

	@Autowired
	private SessionHelper sessionHelper;

	private String parameters;

	public AbstractPage() {
		setSizeUndefined();
		addStyleName("webcad-abstract-page");
	}

	public abstract void enter();
	
	@Override
	final public void enter(ViewChangeEvent event) {
		parameters = event.getParameters();
		if (isAccessAvailable()) {
			enter();
		} else {
			WebCadUI.getCurrent().processUri("!403");
		}
	}
	
	public boolean isAccessAvailable () {
		return true;
	}

	public String getPageLocation() {
		VaadinView vaadinViewAnnotation = getClass().getAnnotation(VaadinView.class);
		if (vaadinViewAnnotation == null) {
			return null;
		}
		return vaadinViewAnnotation.value();
	}

	public String getPagePath() {
		if (StringUtils.isBlank(getPageParameters())) {
			return getPageLocation();
		}
		return getPageLocation() + "/" + getPageParameters();
	}

	private String getPageParameters() {
		return parameters;
	}

	protected void refreshPage() {
		WebCadUI.getCurrent().processUri("!" + getPagePath());
	}
	
}
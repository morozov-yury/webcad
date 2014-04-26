package diploma.webcad.view.tiles;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;

import diploma.webcad.core.annotation.SessionStateBind;
import diploma.webcad.core.exception.RedirectException;
import diploma.webcad.core.manager.SessionState;
import diploma.webcad.core.view.Tile;
import diploma.webcad.manager.MappingProcessor;
import diploma.webcad.view.WebCadUI;
import diploma.webcad.view.components.SessionHelper;

@SuppressWarnings("serial")
public abstract class AbstractContentTile extends Panel implements Tile, View {

	@Autowired
	private SessionState sessionState;

	protected boolean validated;

	@Autowired
	private MappingProcessor mappingProcessor;

	@Autowired
	private SessionHelper sessionHelper;

	private String parameters;

	public AbstractContentTile() {
		setSizeUndefined();
		setWidth(100, Unit.PERCENTAGE);
		setStyleName("content-tile");
	}

	public void bindSessionParameters() {
		Class<? extends AbstractContentTile> clazz = getClass();
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			SessionStateBind annotation = field
					.getAnnotation(SessionStateBind.class);
			if (annotation != null) {
				try {
					boolean wasAccessible = field.isAccessible();
					field.setAccessible(true);
					switch (annotation.action()) {
					case GET:
						field.set(this,
								sessionState.getParameter(annotation.value()));
						break;
					case TAKE:
						field.set(this,
								sessionState.takeParameter(annotation.value()));
						break;
					}
					field.setAccessible(wasAccessible);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public String getCss() {
		return "";
	}

	public SessionState getSessionState() {
		return sessionState;
	}

	public MappingProcessor getMappingProcessor() {
		return mappingProcessor;
	}

	public SessionHelper getSessionHelper() {
		return sessionHelper;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		parameters = event.getParameters();
		if (validated)
			enterManager(event);
		else
			enterViewer(event);
	}

	public String getTitle() {
		return this.getSessionHelper().getAppRes(this, "title");
	}

	protected void enterViewer(ViewChangeEvent event) {
	}

	protected void enterManager(ViewChangeEvent event) {
	}

	public boolean validate(ViewChangeEvent event) throws RedirectException {
		// TODO Auto-generated method stub
		return true;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	public String getLocation() {
		VaadinView vaadinViewAnnotation = getClass().getAnnotation(
				VaadinView.class);
		if (vaadinViewAnnotation == null)
			return null;
		return vaadinViewAnnotation.value();
	}

	protected void processUri(String uri) {
		WebCadUI.getCurrent().processUri(uri);
	}

	public String getPath() {
		if (StringUtils.isBlank(getParameters()))
			return getLocation();
		return getLocation() + "/" + getParameters();
	}

	private String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	@Override
	public void setContent(Component content) {
		super.setContent(content);
		if (content != null)
			content.setStyleName("landing-layout-content-body");
	}

	protected void refreshPage() {
		processUri("!" + getPath());
	}
}
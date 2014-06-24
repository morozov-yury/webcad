package diploma.webcad.listener;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import diploma.webcad.view.WebCadUI;

@SuppressWarnings("serial")
public class RedirectListener implements ClickListener {

	private final String uri;

	public RedirectListener(String uri) {
		this.uri = uri;
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		WebCadUI.getCurrent().navigateTo(uri);
	}

}

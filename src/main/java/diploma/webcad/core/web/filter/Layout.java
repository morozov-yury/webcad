package diploma.webcad.core.web.filter;

import com.vaadin.navigator.View;
import com.vaadin.ui.ComponentContainer;

public interface Layout extends ComponentContainer {
	
	public void repaint();
	
	public void setContent(View view);
	
	public View getContent();
	
}

package diploma.webcad.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;

public class WebCadViewDisplay implements ViewDisplay {

	private static final long serialVersionUID = 889294679774186836L;
	
	private Layout layout;

	public WebCadViewDisplay(Layout layout) {
		this.layout = layout;
	}
	
	public void setLayout(Layout layout) {
		this.layout = layout;
		showView(layout.getContent());
	}
	
	public Layout getLayout() {
		return layout;
	}

	@Override
	public void showView(View view) {
		layout.setContent(view);
		layout.repaint();
	}

}

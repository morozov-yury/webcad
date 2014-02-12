package diploma.webcad.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;

import diploma.webcad.core.view.Layout;

@SuppressWarnings("serial")
public class QuizViewDisplay implements ViewDisplay {
	
	private Layout layout;

	public QuizViewDisplay(Layout layout) {
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

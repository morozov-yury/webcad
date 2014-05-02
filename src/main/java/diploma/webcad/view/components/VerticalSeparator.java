package diploma.webcad.view.components;

import com.vaadin.ui.CustomLayout;

public class VerticalSeparator extends CustomLayout {

	private static final long serialVersionUID = -3104126933695004225L;
	
	public VerticalSeparator (){
		addStyleName("vertical-separator");
		setHeight(100, Unit.PERCENTAGE);
	}

}

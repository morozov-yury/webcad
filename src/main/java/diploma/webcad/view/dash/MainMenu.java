package diploma.webcad.view.dash;

import java.util.Iterator;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.NativeButton;

import diploma.webcad.view.WebCadUI;

public class MainMenu extends CssLayout {

	private static final long serialVersionUID = -6316820018309025627L;
	
	public MainMenu () {
		addStyleName("menu");
		//addStyleName("no-vertical-drag-hints");
		//addStyleName("no-horizontal-drag-hints");
        setHeight("100%");
        
        addButton("Главная","");
        addButton("Главная","");
        addButton("Главная","");
        addButton("Главная","");
        
        Component component = iterator().next();
        component.addStyleName("selected");
	}
	
	private void addButton (String name, final String mapping) {
		
		Button button = new NativeButton(name);
		
		button.addStyleName("icon-dashboard");
		
		button.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -3431580378578867080L;
			@Override
            public void buttonClick(ClickEvent event) {
                clearMenuSelection();
                event.getButton().addStyleName("selected");
//                if (!nav.getState().equals("/" + view)) {
//                    nav.navigateTo("/" + view);
//                }
                WebCadUI.getCurrent().processUri(mapping);
            }
        });
		
		
		
		addComponent(button);
		
	}
	
	private void clearMenuSelection() {
        for (Iterator<Component> it = iterator(); it.hasNext();) {
            Component next = it.next();
            if (next instanceof NativeButton) {
                next.removeStyleName("selected");
            } /* else if (next instanceof DragAndDropWrapper) {
                // Wow, this is ugly (even uglier than the rest of the code)
                ((DragAndDropWrapper) next).iterator().next().removeStyleName("selected");
            }
            */
        }
    }

}
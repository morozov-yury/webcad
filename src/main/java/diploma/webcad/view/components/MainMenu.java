package diploma.webcad.view.components;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.NativeButton;

import diploma.webcad.view.WebCadUI;
import diploma.webcad.view.pages.MainPage;
import diploma.webcad.view.pages.modelling.GenaPage;
import diploma.webcad.view.pages.modelling.XilinxPage;

public class MainMenu extends CssLayout {

	private static final long serialVersionUID = -6316820018309025627L;
	
	private static Logger log = LoggerFactory.getLogger(MainMenu.class);
	
	private Map<String, Component> components;
	
	public MainMenu () {
		addStyleName("menu");
        setHeight("100%");
        
        components = new HashMap<String, Component>();
        
        addButton("Главная", MainPage.NAME, "icon-home");
        addButton("Gena", GenaPage.NAME, "icon-gena");
        addButton("Xilinx", XilinxPage.NAME, "icon-xilinx");
        
        Component component = iterator().next();
        component.addStyleName("selected");
	}
	
	private void addButton (String name, final String mapping, String style) {
		
		Button button = new NativeButton(name);
		
		button.addStyleName(style);
		
		button.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -3431580378578867080L;
			@Override
            public void buttonClick(ClickEvent event) {
                clearMenuSelection();
                event.getButton().addStyleName("selected");
                WebCadUI.getCurrent().navigateTo(mapping);
            }
        });

		if (!components.containsKey(mapping)) {
			components.put(mapping, button);
		}
		addComponent(button);
	}
	
	private void clearMenuSelection() {
        for (Iterator<Component> it = iterator(); it.hasNext();) {
            Component next = it.next();
            if (next instanceof NativeButton) {
                next.removeStyleName("selected");
            }
        }
    }
	
	public void selectTab (String mapping) {
		clearMenuSelection();
		if (mapping == null) {
			mapping = "";
		}
		if (mapping.isEmpty()) {
			iterator().next().addStyleName("selected");
			return;
		}
		for (Entry<String, Component> entry : components.entrySet()) {
			if (entry.getKey().startsWith(mapping)) {
				entry.getValue().addStyleName("selected");
			}
		}
	}

}

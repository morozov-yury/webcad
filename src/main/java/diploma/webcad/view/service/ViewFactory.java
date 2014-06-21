package diploma.webcad.view.service;

import org.springframework.context.annotation.Scope;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@org.springframework.stereotype.Component
@Scope("singleton")
public class ViewFactory {
	
	public Component wrapComponent (Component content) {
		CssLayout panel = new CssLayout();
        panel.addStyleName("layout-panel");
        panel.setSizeFull();

        Button configure = new Button();
        configure.addStyleName("configure");
        configure.addStyleName("icon-cog");
        configure.addStyleName("icon-only");
        configure.addStyleName("borderless");
        configure.setDescription("Configure");
        configure.addStyleName("small");
        configure.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 2101274756535965486L;
			@Override
            public void buttonClick(ClickEvent event) {
                Notification.show("Not implemented in this demo");
            }
        });
        panel.addComponent(configure);
        
        if (content.getCaption() == null) {
        	content.setCaption("");
        }

        content.addStyleName("cute-top-caption");
        panel.addComponent(content);
        return panel;
	}
	
	public Component wrapTextArea (TextArea notes) {
        notes.setSizeFull();
        Component panel = wrapComponent(notes);
        panel.addStyleName("notes");
        return panel;
	}

}

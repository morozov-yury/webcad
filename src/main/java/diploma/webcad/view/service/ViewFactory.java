package diploma.webcad.view.service;

import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomTable.RowHeaderMode;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;

import diploma.webcad.core.model.gena.GenaLaunch;
import diploma.webcad.core.model.gena.GenaPlacement;
import diploma.webcad.core.model.gena.GenaResultStatus;

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
	
	public FilterTable getGenaLaunchesTable (List<GenaLaunch> genaLaunches) {
		FilterTable filterTable = new FilterTable();
        filterTable.setSizeFull();

        filterTable.setFilterBarVisible(true);

        filterTable.setSelectable(true);
        filterTable.setImmediate(true);
        filterTable.setMultiSelect(true);        
        
        filterTable.setPageLength(5);
        filterTable.setRowHeaderMode(RowHeaderMode.INDEX);
        filterTable.setColumnCollapsingAllowed(true);
        filterTable.setColumnReorderingAllowed(true);
        filterTable.setContainerDataSource(getTableContainer(genaLaunches));
        filterTable.setColumnCollapsed("state", true);
        filterTable.setVisibleColumns("id", "placement", "genaParams", "creationDate", "status");

        return filterTable;
	}
	
	private Container getTableContainer (List<GenaLaunch> genaLaunches) {
		IndexedContainer cont = new IndexedContainer();
		
		cont.addContainerProperty("id", Long.class, null);
        cont.addContainerProperty("placement", GenaPlacement.class, null);
        cont.addContainerProperty("genaParams", String.class, null);
        cont.addContainerProperty("creationDate", Date.class, null);
        cont.addContainerProperty("status", GenaResultStatus.class, null);
        
        for (GenaLaunch launch : genaLaunches) {
        	cont.addItem(launch);
        	cont.getContainerProperty(launch, "id").setValue(launch.getId());
        	cont.getContainerProperty(launch, "placement").setValue(launch.getGenaPlacement());
        	cont.getContainerProperty(launch, "genaParams").setValue(launch.getGenaParams());
        	cont.getContainerProperty(launch, "creationDate").setValue(launch.getCreationDate());
        	cont.getContainerProperty(launch, "status").setValue(launch.getGenaResultStatus());
        }
		
		return cont;
	}

}

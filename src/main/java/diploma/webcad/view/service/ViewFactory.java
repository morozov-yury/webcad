package diploma.webcad.view.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomTable.RowHeaderMode;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;

import diploma.webcad.core.model.simulation.BatchSimulation;
import diploma.webcad.core.model.simulation.BatchSimulationStatus;
import diploma.webcad.core.model.simulation.Device;
import diploma.webcad.core.model.simulation.GenaLaunch;
import diploma.webcad.core.model.simulation.GenaPlacement;
import diploma.webcad.core.model.simulation.GenaResultStatus;
import diploma.webcad.core.model.simulation.XilinxProject;
import diploma.webcad.core.model.simulation.XilinxProjectStatus;

@org.springframework.stereotype.Component
@Scope("singleton")
@SuppressWarnings("unchecked")
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
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
		FilterTable filterTable = getTipicalFilterTable(getLaunchesContainer(genaLaunches));
        filterTable.setSortContainerPropertyId("creationDate");
        return filterTable;
	}
	
	public FilterTable getSimulationsTable(List<BatchSimulation> batchSimulations) {
		FilterTable filterTable = getTipicalFilterTable(getSimulationsComtainer(batchSimulations));
        filterTable.setSortContainerPropertyId("creationDate");
		return filterTable;
	}
	
	public Container getSimulationsComtainer (List<BatchSimulation> batchSimulations) {
		IndexedContainer cont = new IndexedContainer();
		
		cont.addContainerProperty("id", Long.class, null);
		cont.addContainerProperty("creationDate", Timestamp.class, null);
		cont.addContainerProperty("projectsCount", Integer.class, null);
		cont.addContainerProperty("devicesCount", Integer.class, null);
		cont.addContainerProperty("status", BatchSimulationStatus.class, null);
        
        for (BatchSimulation batchSimulation : batchSimulations) {
        	Long id = batchSimulation.getId();
        	cont.addItem(id);
        	cont.getContainerProperty(id, "id").setValue(
        			batchSimulation.getId());      	
        	cont.getContainerProperty(id, "creationDate").setValue(
        			batchSimulation.getCreationDate());     	
        	cont.getContainerProperty(id, "projectsCount").setValue(
        			batchSimulation.getProjects().size());     	
        	cont.getContainerProperty(id, "devicesCount").setValue(
        			batchSimulation.getDevices().size());   	
        	cont.getContainerProperty(id, "status").setValue(batchSimulation.getStatus());
        }
		
		return cont;
	}
	
	public Container getLaunchesContainer (List<GenaLaunch> genaLaunches) {
		IndexedContainer cont = new IndexedContainer();
		
		cont.addContainerProperty("id", Long.class, null);
        cont.addContainerProperty("placement", GenaPlacement.class, null);
        cont.addContainerProperty("genaParams", String.class, null);
        // Here is Timestamp.class because of hibernate returns Timestamp instead Date
        // Issue https://github.com/tepi/FilteringTable/issues/12
        cont.addContainerProperty("creationDate", Timestamp.class, null);
        cont.addContainerProperty("status", GenaResultStatus.class, null);
        
        for (GenaLaunch launch : genaLaunches) {
        	Long id = launch.getId();
        	cont.addItem(id);
        	cont.getContainerProperty(id, "id").setValue(id);
        	cont.getContainerProperty(id, "placement").setValue(launch.getPlacement());
        	cont.getContainerProperty(id, "genaParams").setValue(launch.getParams());
        	cont.getContainerProperty(id, "creationDate").setValue(launch.getCreationDate());
        	cont.getContainerProperty(id, "status").setValue(launch.getStatus());
        }
		
		return cont;
	}
	
	public HierarchicalContainer getDevicesContainer (List<Device> devices) {
		HierarchicalContainer container = new HierarchicalContainer();
		
		container.addContainerProperty("name", String.class, "");
		
		for (Device device : devices) {
			String familyName = device.getDeviceFamily().getName();
			if (container.getItem(familyName) == null) {
				Item parent = container.addItem(familyName);
				parent.getItemProperty("name").setValue(device.getDeviceFamily().getDescription());
			}
			
			Item item = container.addItem(device.getName());
			container.setChildrenAllowed(device.getName(), false);
			container.setParent(device.getName(), familyName);
			item.getItemProperty("name").setValue(device.getName());
		}
		
		return container;
	}
	
	public FilterTable getProjectsTable (List<XilinxProject> projects) {
		FilterTable table = getTipicalFilterTable(getXilinxProjectsContainer(projects));
		table.setSortContainerPropertyId("family");
		return table;
	}
	
	public Container getXilinxProjectsContainer (List<XilinxProject> projects) {
		IndexedContainer cont = new IndexedContainer();
		
		cont.addContainerProperty("id", Long.class, null);
		cont.addContainerProperty("family", String.class, null);
		cont.addContainerProperty("device", String.class, null);
		cont.addContainerProperty("status", XilinxProjectStatus.class, null);
		//cont.addContainerProperty("folderId", String.class, "");
		
		for (XilinxProject project : projects) {
			Long id = project.getId();
        	cont.addItem(id);
        	cont.getContainerProperty(id, "id").setValue(id);
        	cont.getContainerProperty(id, "family").setValue(
        			project.getDevice().getDeviceFamily().getName());
        	cont.getContainerProperty(id, "device").setValue(project.getDevice().getName());
        	cont.getContainerProperty(id, "status").setValue(project.getStatus());
        	//cont.getContainerProperty(id, "folderId").setValue(project.getFolder());
		}
		
		return cont;
	}
	
	private FilterTable getTipicalFilterTable (Container container) {
		FilterTable filterTable = new FilterTable();
		filterTable.setSizeFull();
        filterTable.setFilterBarVisible(true);
        filterTable.setSelectable(true);
        filterTable.setImmediate(true);
        filterTable.setMultiSelect(false); 
        filterTable.setRowHeaderMode(RowHeaderMode.INDEX);
        filterTable.setColumnCollapsingAllowed(true);
        filterTable.setColumnReorderingAllowed(true);
        filterTable.setContainerDataSource(container);
        filterTable.setSortAscending(false);
		return filterTable;
	}

}

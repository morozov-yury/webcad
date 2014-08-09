package diploma.webcad.view.pages.simulation;

import java.util.List;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.tepi.filtertable.FilterTable;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.core.model.User;
import diploma.webcad.core.model.simulation.BatchSimulation;
import diploma.webcad.core.model.simulation.BatchSimulationStatus;
import diploma.webcad.core.model.simulation.XilinxProject;
import diploma.webcad.core.service.XilinxService;
import diploma.webcad.view.WebCadUI;
import diploma.webcad.view.pages.AbstractPage;
import diploma.webcad.view.service.ViewFactory;

@org.springframework.stereotype.Component
@Scope("prototype")
@VaadinView(SimulationsPage.NAME)
public class SimulationsPage extends AbstractPage {

	private static final long serialVersionUID = -5470943902880211139L;

	public static final String NAME = "simulations";
	
	private static Logger log = LoggerFactory.getLogger(XilinxPage.class);
	
	@Autowired
	private ViewFactory viewFactory;
	
	@Autowired
	private XilinxService xilinxService;
	
	private User user;
	
	private FilterTable simulationsTable;
	
	private VerticalLayout infoLayout;
	
	private VerticalLayout projectsLayout;

	private Button runButton;
	
	public SimulationsPage() {
		super("Simulations");
	}

	@Override
	public void enter() {
		HorizontalLayout content = new HorizontalLayout();
		content.setSizeFull();
		content.setSpacing(true);

		user = WebCadUI.getCurrent().getSessionState().getUser();
		List<BatchSimulation> batchSimulations = xilinxService.listBatchSimulation(user);
		simulationsTable = viewFactory.getSimulationsTable(batchSimulations);
		simulationsTable.setCaption("Yous batch simulations");
		
		simulationsTable.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				Long id = (Long) simulationsTable.getValue();
				if (id == null) {
					return;
				}
				BatchSimulation batchSimulation = xilinxService.getBatchSimulation(id, false);
				Hibernate.initialize(batchSimulation.getProjects());
				updateInfoLayout(batchSimulation);
				updateProjectsLayout(batchSimulation);
				
				if (batchSimulation.getStatus() == BatchSimulationStatus.CREATED) {
					runButton.setVisible(true);
				}
			}
		});
		
		content.addComponent(viewFactory.wrapComponent(simulationsTable));
		content.addComponent(new VerticalLayout() {
			private static final long serialVersionUID = 7221212434667732601L;
			{
				setSpacing(true);
				setSizeFull();
				
				infoLayout = new VerticalLayout();
				infoLayout.setCaption("Simulation info");
				infoLayout.setSizeFull();
				infoLayout.setMargin(true);
				
				projectsLayout = new VerticalLayout();
				projectsLayout.setCaption("Simulation rojects");
				projectsLayout.setSizeFull();
				
				addComponent(viewFactory.wrapComponent(infoLayout));
				addComponent(viewFactory.wrapComponent(projectsLayout));
			}
		});
		
		runButton = new Button("Run", new Button.ClickListener() {
			private static final long serialVersionUID = 7105297962177174478L;
			@Override
			public void buttonClick(ClickEvent event) {
				Long id = (Long) simulationsTable.getValue();
				xilinxService.runBatchSimulation(id);
			}
		});
		runButton.setVisible(false);
		runButton.addStyleName("default");
		addComponentToTop(runButton);
		
		if (getPageProperties().containsKey("BatchSimulation.id")) {
			Object property = getPageProperties().get("BatchSimulation.id");
			Long id = Long.valueOf(property.toString());
			simulationsTable.setValue(id);
		}

		setContent(content);
	}
	
	private void updateInfoLayout (BatchSimulation batchSimulation) {	
		infoLayout.removeAllComponents();
		infoLayout.addComponent(new Label(batchSimulation.toString()));
	}
	
	private void updateProjectsLayout (BatchSimulation batchSimulation) {	
		projectsLayout.removeAllComponents();
		List<XilinxProject> projects = batchSimulation.getProjects();
		projectsLayout.addComponent(viewFactory.getProjectsTable(projects));
	}
	
}

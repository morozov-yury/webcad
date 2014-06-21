package diploma.webcad.view.components.gena;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.core.init.SpringContext;
import diploma.webcad.core.service.GenaService;
import diploma.webcad.view.WebCadUI;
import diploma.webcad.view.components.HorizontalOptionGroup;
import diploma.webcad.view.model.gena.GenaParam;
import diploma.webcad.view.model.gena.MachineType;

public class GenaParamSelector extends VerticalLayout {
	
	private static Logger log = LoggerFactory.getLogger(GenaParamSelector.class);
	
	private SpringContext springContextHelper;

	private GenaService genaService;

	private static final long serialVersionUID = -5697510503372938703L;

	private MachineParamsSelector machineParamsSelector;
	
	public GenaParamSelector (GenaParam genaParam) {

		setCaption("Choose run parameters");
		setSizeFull();
		setSpacing(true);
		
		HorizontalOptionGroup<MachineType> mainOptgroup = new HorizontalOptionGroup<MachineType>();
		addComponent(mainOptgroup);
		setExpandRatio(mainOptgroup, 0);
		
		mainOptgroup.setSizeUndefined();
		mainOptgroup.selectItem((genaParam != null) ? genaParam.getMachineType() : MachineType.MEALY);
		mainOptgroup.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -7816685958306922492L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				MachineType machineType = (MachineType) event.getProperty().getValue();
				MachineParamsSelector newSelector = MachineParamsFactory.getSelector(machineType);
				if (machineParamsSelector == null) {
					addComponent(newSelector);
				} else {
					replaceComponent(machineParamsSelector, newSelector);
				}
				machineParamsSelector = newSelector; 
				setExpandRatio(machineParamsSelector, 1);
			}
		});
		mainOptgroup.setContainerDataSource(getContainer());
	}

	@Override
	public void attach() {
		springContextHelper = WebCadUI.getCurrent().getSessionState().getContext();
		genaService = springContextHelper.getBean(GenaService.class);
		super.attach();
	}

	public GenaParam getParameters () {
		return machineParamsSelector.getGenaParam();
	}

	private BeanItemContainer<MachineType> getContainer () {
		BeanItemContainer<MachineType> genaPatamTypes = new BeanItemContainer<MachineType>(
				MachineType.class);
		genaPatamTypes.addBean(MachineType.MEALY);
		genaPatamTypes.addBean(MachineType.MOORE);
		genaPatamTypes.addBean(MachineType.CM);
		genaPatamTypes.addBean(MachineType.CS);
		return genaPatamTypes;
	}

}

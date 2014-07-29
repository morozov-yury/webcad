package diploma.webcad.view.components.gena.cmcs;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

import diploma.webcad.view.components.gena.MachineParamsSelector;
import diploma.webcad.view.model.gena.GenaParam;
import diploma.webcad.view.model.gena.MachineType;
import diploma.webcad.view.model.gena.cmcs.AddrMicroinstParam.EncodingMC;
import diploma.webcad.view.model.gena.cmcs.CMGenaParam;

public class CMParamSelector extends MachineParamsSelector {

	private static final long serialVersionUID = 7645775495896293558L;
	
	@PropertyId("codLogConditions")
	private CheckBox codLogConditionsComp;
	
	@PropertyId("encodingMC")
	private ComboBox encodingMCComp;
	
	@PropertyId("wordWodthRestriction")
	private TextField wordWodthRestrictionComp;

	private CMGenaParam genaParam;
	
	private BeanFieldGroup<CMGenaParam> binder;

	public CMParamSelector(CMGenaParam genaParam) {
		this.genaParam = genaParam;
		setSizeFull();
		
		BeanItem<CMGenaParam> beanItem = new BeanItem<CMGenaParam>(this.genaParam);
		
		codLogConditionsComp = new CheckBox("Кодирование логических условий");
		codLogConditionsComp.setPropertyDataSource(beanItem.getItemProperty("codLogConditions"));
		codLogConditionsComp.setImmediate(true);
		codLogConditionsComp.addValidator(new BeanValidator(CMGenaParam.class, "codLogConditions"));
		
		encodingMCComp = new ComboBox("Кодирование микрокоманд");
		//encodingMCComp.setPropertyDataSource(beanItem.getItemProperty("encodingMC"));
		encodingMCComp.setImmediate(true);
		encodingMCComp.addValidator(new BeanValidator(CMGenaParam.class, "encodingMC"));
		encodingMCComp.setContainerDataSource(getEncodingMCContainer());
		encodingMCComp.setNullSelectionAllowed(false);
		encodingMCComp.setValue(genaParam.getEncodingMC());

		wordWodthRestrictionComp = new TextField("Ограничение ширины слова памяти в битах");
		wordWodthRestrictionComp.setPropertyDataSource(beanItem.getItemProperty("wordWodthRestriction"));
		wordWodthRestrictionComp.setImmediate(true);
		wordWodthRestrictionComp.setWidth("5em");
		wordWodthRestrictionComp.setNullRepresentation("");
		wordWodthRestrictionComp.addValidator(new BeanValidator(CMGenaParam.class, "wordWodthRestriction"));
		
		FormLayout formLayout = new FormLayout(codLogConditionsComp, encodingMCComp, 
				wordWodthRestrictionComp);
		addComponent(formLayout);

		binder = new BeanFieldGroup<CMGenaParam>(CMGenaParam.class);
		binder.setItemDataSource(this.genaParam);
        binder.bindMemberFields(this);
		
	}

	@Override
	public MachineType getMachineType() {
		return MachineType.CM;
	}

	@Override
	public GenaParam getGenaParam() {
		try {
			binder.commit();
			return this.genaParam;
		} catch (CommitException e) {
			//e.printStackTrace();
		}
		return null;
	}
	

}

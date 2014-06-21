package diploma.webcad.view.components.gena.cmcs;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

import diploma.webcad.view.components.gena.MachineParamsSelector;
import diploma.webcad.view.model.gena.GenaParam;
import diploma.webcad.view.model.gena.MachineType;
import diploma.webcad.view.model.gena.cmcs.CSGenaParam;
import diploma.webcad.view.model.gena.cmcs.CSGenaParam;
import diploma.webcad.view.model.gena.cmcs.AddrMicroinstParam.EncodingMC;
import diploma.webcad.view.model.gena.cmcs.CSGenaParam.CodePlacement;
import diploma.webcad.view.model.gena.cmcs.CSGenaParam.ExtCodeConverter;

public class CSParamSelector extends MachineParamsSelector {

	private static final long serialVersionUID = -1154451449013644186L;
	
	@PropertyId("codLogConditions")
	private CheckBox codLogConditionsComp;

	@PropertyId("encodingMC")
	private ComboBox encodingMCComp;
	
	@PropertyId("wordWodthRestriction")
	private TextField wordWodthRestrictionComp;
	
	@PropertyId("elementarizatsiya")
	private CheckBox eltsiyaComp;
	
	@PropertyId("codePlacemet")
	private ComboBox codePlacemetComp;
	
	@PropertyId("extCodeConverter")
	private ComboBox extCodeConverterComp;

	private CSGenaParam genaParam;
	
	private BeanFieldGroup<CSGenaParam> binder;

	public CSParamSelector(CSGenaParam genaParam) {
		this.genaParam = genaParam;
		
		BeanItem<CSGenaParam> beanItem = new BeanItem<CSGenaParam>(this.genaParam);
		
		codLogConditionsComp = new CheckBox("Кодирование логических условий");
		codLogConditionsComp.setPropertyDataSource(beanItem.getItemProperty("codLogConditions"));
		codLogConditionsComp.setImmediate(true);
		codLogConditionsComp.addValidator(new BeanValidator(CSGenaParam.class, "codLogConditions"));
		
		encodingMCComp = new ComboBox("Кодирование микрокоманд");
		encodingMCComp.setPropertyDataSource(beanItem.getItemProperty("encodingMC"));
		encodingMCComp.setImmediate(true);
		encodingMCComp.addValidator(new BeanValidator(CSGenaParam.class, "encodingMC"));
		encodingMCComp.setContainerDataSource(getEncodingMCContainer());

		wordWodthRestrictionComp = new TextField("Ограничение ширины слова памяти в битах");
		wordWodthRestrictionComp.setPropertyDataSource(beanItem.getItemProperty("encodingMC"));
		wordWodthRestrictionComp.setImmediate(true);
		wordWodthRestrictionComp.setWidth("5em");
		wordWodthRestrictionComp.setNullRepresentation("");
		wordWodthRestrictionComp.addValidator(new BeanValidator(CSGenaParam.class, "encodingMC"));
		
		eltsiyaComp = new CheckBox("Элементаризация ОЛЦ");
		eltsiyaComp.setPropertyDataSource(beanItem.getItemProperty("elementarizatsiya"));
		eltsiyaComp.setImmediate(true);
		eltsiyaComp.addValidator(new BeanValidator(CSGenaParam.class, "elementarizatsiya"));
		
		codePlacemetComp = new ComboBox("Размещение кода");
		codePlacemetComp.setPropertyDataSource(beanItem.getItemProperty("codePlacemet"));
		codePlacemetComp.setImmediate(true);
		codePlacemetComp.addValidator(new BeanValidator(CSGenaParam.class, "codePlacemet"));
		codePlacemetComp.setContainerDataSource(getCodePlacementContainer());
		
		extCodeConverterComp = new ComboBox("Использование внешнего преобразователя кодов");
		extCodeConverterComp.setPropertyDataSource(beanItem.getItemProperty("extCodeConverter"));
		extCodeConverterComp.setImmediate(true);
		extCodeConverterComp.addValidator(new BeanValidator(CSGenaParam.class, "extCodeConverter"));
		extCodeConverterComp.setContainerDataSource(getExtCodeConverterContainer());
		
		FormLayout formLayout = new FormLayout(codLogConditionsComp, encodingMCComp, 
				wordWodthRestrictionComp, eltsiyaComp, codePlacemetComp, extCodeConverterComp);
		addComponent(formLayout);

		binder = new BeanFieldGroup<CSGenaParam>(CSGenaParam.class);
		binder.setItemDataSource(this.genaParam);
        binder.bindMemberFields(this);
        
	}

	@Override
	public MachineType getMachineType() {
		return MachineType.CS;
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
	
	private BeanItemContainer<CodePlacement> getCodePlacementContainer () {
		BeanItemContainer<CodePlacement> types = new BeanItemContainer<CodePlacement>(
				CodePlacement.class);
		types.addBean(CodePlacement.THIS_MO);
		types.addBean(CodePlacement.NEXT_MO);
		return types;
	}
	
	private BeanItemContainer<ExtCodeConverter> getExtCodeConverterContainer () {
		BeanItemContainer<ExtCodeConverter> types = new BeanItemContainer<ExtCodeConverter>(
				ExtCodeConverter.class);
		types.addBean(ExtCodeConverter.IN_NEED);
		types.addBean(ExtCodeConverter.MUST_NOT);
		types.addBean(ExtCodeConverter.NECESSARY);
		return types;
	}

}

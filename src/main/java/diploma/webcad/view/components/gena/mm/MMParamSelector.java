package diploma.webcad.view.components.gena.mm;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.view.components.gena.MachineParamsSelector;
import diploma.webcad.view.model.gena.GenaParam;
import diploma.webcad.view.model.gena.mm.MMGenaParam;

public abstract class MMParamSelector extends MachineParamsSelector {

	private static final long serialVersionUID = 3050759398614100805L;

	@PropertyId("codVerGSA")
	private TextField codVerGSAField;

	@PropertyId("ispPromTermov")
	private TextField ispPromTermovField;

	private BeanFieldGroup<MMGenaParam> binder;

	private MMGenaParam genaParam;
	
	public MMParamSelector (MMGenaParam genaParam) {
		this.genaParam = genaParam;
		setSizeFull();
		
		BeanItem<MMGenaParam> beanItem = new BeanItem<MMGenaParam>(this.genaParam);
		
		codVerGSAField = new TextField("Кодирование вершин ГСА [0-3]");
		codVerGSAField.setPropertyDataSource(beanItem.getItemProperty("codVerGSA"));
		codVerGSAField.setImmediate(true);
		codVerGSAField.setWidth("3em");
		codVerGSAField.setMaxLength(1);
		codVerGSAField.setNullRepresentation("");
		codVerGSAField.addValidator(new BeanValidator(MMGenaParam.class, "codVerGSA"));
		
		ispPromTermovField = new TextField("Использование промежуточных термов [0-1]");
		ispPromTermovField.setPropertyDataSource(beanItem.getItemProperty("ispPromTermov"));
		ispPromTermovField.setImmediate(true);
		ispPromTermovField.setWidth("3em");
		ispPromTermovField.setMaxLength(1);
		ispPromTermovField.setNullRepresentation("");
		ispPromTermovField.addValidator(new BeanValidator(MMGenaParam.class, "ispPromTermov"));

		FormLayout formLayout = new FormLayout(codVerGSAField, ispPromTermovField);
		addComponent(formLayout);

		binder = new BeanFieldGroup<MMGenaParam>(MMGenaParam.class);
		binder.setItemDataSource(this.genaParam);
        binder.bindMemberFields(this);
		
	}

	@Override
	public GenaParam getGenaParam () {
		try {
			binder.commit();
			return this.genaParam;
		} catch (CommitException e) {
			//e.printStackTrace();
		}
		return null;
	}

}

package diploma.webcad.view.components.gena;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.view.components.VerticalSeparator;

public class GenaCSParamSelector extends HorizontalLayout {

	private static final long serialVersionUID = -1154451449013644186L;
	
	private TextField wTextField;

	private CheckBox eCheckBox;

	private CheckBox pCheckBox;

	private CheckBox xCheckBox;

	private OptionGroup yOptionGroup;

	private OptionGroup cOptionGroup;

	public GenaCSParamSelector() {
		wTextField = new TextField();
		wTextField.setImmediate(true);
		wTextField.setValue("1");
		wTextField.setColumns(2);
		HorizontalLayout  wTextFieldLayout =  new HorizontalLayout(
				wTextField, 
				new Label("ограничение ширины слова памяти в битах"));
		wTextFieldLayout.setSpacing(true);
		
		eCheckBox = new CheckBox("элементаризация ОЛЦ", true);
		eCheckBox.setImmediate(true);
		pCheckBox = new CheckBox("указание размещения кода", true);
		pCheckBox.setImmediate(true);
		xCheckBox = new CheckBox("кодирование логических условий", true);
		xCheckBox.setImmediate(true);
		
		yOptionGroup = new OptionGroup("Кодирование микрокомад:");
		yOptionGroup.setSizeUndefined();
		yOptionGroup.addItem("0");
		yOptionGroup.setItemCaption("0", "унитарное");
		yOptionGroup.addItem("1");
		yOptionGroup.setItemCaption("1", "совместимое");
		yOptionGroup.addItem("2");
		yOptionGroup.setItemCaption("2", "максимальное");
		yOptionGroup.select("1");
		yOptionGroup.setNullSelectionAllowed(false);
		yOptionGroup.setHtmlContentAllowed(true);
		yOptionGroup.setImmediate(true);
		
		cOptionGroup = new OptionGroup("Ипользование внешнего преобразователя кодов:");
		cOptionGroup.setSizeUndefined();
		cOptionGroup.addItem("0");
		cOptionGroup.setItemCaption("0", "нельзя");
		cOptionGroup.addItem("1");
		cOptionGroup.setItemCaption("1", "по-необходимости");
		cOptionGroup.addItem("2");
		cOptionGroup.setItemCaption("2", "обязательно");
		cOptionGroup.select("1");
		cOptionGroup.setNullSelectionAllowed(false);
		cOptionGroup.setHtmlContentAllowed(true);
		cOptionGroup.setImmediate(true);
		
		setSpacing(true);
		setImmediate(true);
		addComponent(new VerticalLayout(yOptionGroup, cOptionGroup));
		addComponent(new VerticalSeparator());
		addComponent(new VerticalLayout(wTextFieldLayout, eCheckBox, pCheckBox, xCheckBox));
	}

	@Override
	public String toString() {
		String e = "-e " + ((eCheckBox.getValue()) ? "1" : "0");
		String p = "-p " + ((pCheckBox.getValue()) ? "1" : "0");
		String x = "-x " + ((xCheckBox.getValue()) ? "1" : "0");
		String y = "-y " + yOptionGroup.getValue();
		String c = "-c" + cOptionGroup.getValue();
		String w = "-w" + wTextField.getValue();
		return "--CS " + e + " " + p + " " + x + " " + y + " " + c + " " + w;
	}
	
	

}

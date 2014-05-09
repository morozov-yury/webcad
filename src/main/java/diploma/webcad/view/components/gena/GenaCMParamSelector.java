package diploma.webcad.view.components.gena;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.view.components.VerticalSeparator;

public class GenaCMParamSelector extends HorizontalLayout {

	private static final long serialVersionUID = 7645775495896293558L;
	
	private CheckBox xCheckBox;

	private OptionGroup yOptionGroup;

	private TextField wTextField;

	public GenaCMParamSelector() {
		wTextField = new TextField();
		wTextField.setImmediate(true);
		wTextField.setValue("1");
		wTextField.setColumns(2);
		HorizontalLayout  wTextFieldLayout =  new HorizontalLayout(
				wTextField, 
				new Label("ограничение ширины слова памяти в битах"));
		wTextFieldLayout.setSpacing(true);
		
		xCheckBox = new CheckBox("Кодирование логических условий", true);
		xCheckBox.setImmediate(true);
		
		yOptionGroup = new OptionGroup("Кодирование микрокомад:");
		yOptionGroup.setSizeUndefined();
		yOptionGroup.addItem("0");
		yOptionGroup.setItemCaption("0", "унитарное");
		yOptionGroup.addItem("1");
		yOptionGroup.setItemCaption("1", "совместимое");
		yOptionGroup.addItem("2");
		yOptionGroup.setItemCaption("2", "максимальное");
		yOptionGroup.select("0");
		yOptionGroup.setNullSelectionAllowed(false);
		yOptionGroup.setHtmlContentAllowed(true);
		yOptionGroup.setImmediate(true);
		
		setSpacing(true);
		setImmediate(true);
		addComponent(yOptionGroup);
		addComponent(new VerticalSeparator());
		addComponent(new VerticalLayout(wTextFieldLayout, xCheckBox));
	}

	@Override
	public String toString() {
		String x = "-x " + ((xCheckBox.getValue()) ? "1" : "0");
		String y = "-y " + yOptionGroup.getValue();
		String w = "-w " + wTextField.getValue();
		return "--CM " + x + " " + y + " " + w;
	}
	
	

}

package diploma.webcad.view.components.gena;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.view.components.VerticalSeparator;

public class GenaParametersSelector extends VerticalLayout {
	
	private static Logger log = LoggerFactory.getLogger(GenaParametersSelector.class);

	private static final long serialVersionUID = -5697510503372938703L;
	
	public GenaParametersSelector () {
		setSizeFull();
		addStyleName("full-width");
		setSpacing(true);
		
		OptionGroup optionGroup = new OptionGroup("Select an run option:");
		optionGroup.setSizeUndefined();
		
		optionGroup.addItem("mm");
		optionGroup.setItemCaption("mm", "Moore/Mealy");
		
		optionGroup.addItem("cm");
		optionGroup.setItemCaption("cm", "Common memory");
		
		optionGroup.addItem("cs");
		optionGroup.setItemCaption("cs", "Code sharing");
		
		optionGroup.select("mm");
		optionGroup.setNullSelectionAllowed(false);
		optionGroup.setHtmlContentAllowed(true);
		optionGroup.setImmediate(true);
		
		final VerticalLayout additionalParamLayout = new VerticalLayout();
		HorizontalLayout paramChoserLayout = new HorizontalLayout(optionGroup, new VerticalSeparator(), additionalParamLayout);
		paramChoserLayout.setSpacing(true);
		addComponent(paramChoserLayout);
		
		optionGroup.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -1406584044462575876L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				String select = event.getProperty().getValue().toString();
				additionalParamLayout.removeAllComponents();
				if (select.equals("mm")) {
					additionalParamLayout.addComponent(getMMParams());
				} else if (select.equals("cm")) {
					additionalParamLayout.addComponent(getCMParams());
				} else if (select.equals("cs")) {
					additionalParamLayout.addComponent(getCSParams());
				}
			}
		});
		
		additionalParamLayout.addComponent(getMMParams());
		
		TextArea descrTextArea = new TextArea("Put your XML decription here:");
		descrTextArea.addStyleName("full-width");
		addComponent(descrTextArea);
	}
	
	private Component getMMParams () {
		VerticalLayout additionalParamLayout = new VerticalLayout();
		
		TextField codingCertices;
		HorizontalLayout horizontalLayout;
		
		codingCertices = new TextField();
		codingCertices.setValue("0");
		codingCertices.setColumns(2);
		horizontalLayout = new HorizontalLayout(codingCertices, new Label("кодирование вершин ГСА [0-3]"));
		horizontalLayout.setSpacing(true);
		additionalParamLayout.addComponent(horizontalLayout);
		
		codingCertices = new TextField();
		codingCertices.setValue("0");
		codingCertices.setColumns(2);
		horizontalLayout = new HorizontalLayout(codingCertices, new Label("использование промежуточных термов [0-1]"));
		horizontalLayout.setSpacing(true);
		additionalParamLayout.addComponent(horizontalLayout);
		
		return additionalParamLayout;
	}
	
	private Component getCMParams () {
		VerticalLayout additionalParamLayout = new VerticalLayout();

		TextField codingCertices;
		HorizontalLayout horizontalLayout;
		
		codingCertices = new TextField();
		codingCertices.setValue("0");
		codingCertices.setColumns(2);
		horizontalLayout = new HorizontalLayout(codingCertices, new Label("ограничение ширины слова памяти в битах"));
		horizontalLayout.setSpacing(true);
		additionalParamLayout.addComponent(horizontalLayout);
		
		CheckBox checkBox;
		
		checkBox = new CheckBox("Кодирование логических условий", true);
		additionalParamLayout.addComponent(checkBox);
		
		OptionGroup optionGroup = new OptionGroup("Кодирование микрокомад:");
		optionGroup.setSizeUndefined();
		
		optionGroup.addItem("u");
		optionGroup.setItemCaption("u", "унитарное");
		
		optionGroup.addItem("s");
		optionGroup.setItemCaption("s", "совместимое");
		
		optionGroup.addItem("m");
		optionGroup.setItemCaption("m", "максимальное");
		
		optionGroup.select("u");
		optionGroup.setNullSelectionAllowed(false);
		optionGroup.setHtmlContentAllowed(true);
		optionGroup.setImmediate(true);
		
		additionalParamLayout.addComponent(optionGroup);
		
		return additionalParamLayout;
	}
	
	private Component getCSParams () {
		VerticalLayout additionalParamLayout = new VerticalLayout();
		
		TextField codingCertices;
		HorizontalLayout horizontalLayout;
		
		codingCertices = new TextField();
		codingCertices.setValue("0");
		codingCertices.setColumns(2);
		horizontalLayout = new HorizontalLayout(codingCertices, new Label("ограничение ширины слова памяти в битах"));
		horizontalLayout.setSpacing(true);
		additionalParamLayout.addComponent(horizontalLayout);
		
		CheckBox checkBox;
		
		checkBox = new CheckBox("элементаризация ОЛЦ", true);
		additionalParamLayout.addComponent(checkBox);
		
		checkBox = new CheckBox("указание размещения кода", true);
		additionalParamLayout.addComponent(checkBox);
		
		checkBox = new CheckBox("кодирование логических условий", true);
		additionalParamLayout.addComponent(checkBox);
		
		OptionGroup optionGroup = new OptionGroup("Кодирование микрокомад:");
		optionGroup.setSizeUndefined();
		
		optionGroup.addItem("u");
		optionGroup.setItemCaption("u", "унитарное");
		
		optionGroup.addItem("s");
		optionGroup.setItemCaption("s", "совместимое");
		
		optionGroup.addItem("m");
		optionGroup.setItemCaption("m", "максимальное");
		
		optionGroup.select("u");
		optionGroup.setNullSelectionAllowed(false);
		optionGroup.setHtmlContentAllowed(true);
		optionGroup.setImmediate(true);
		
		additionalParamLayout.addComponent(optionGroup);
		
		optionGroup = new OptionGroup("Ипользование внешнего преобразователя кодов:");
		optionGroup.setSizeUndefined();
		
		optionGroup.addItem("n");
		optionGroup.setItemCaption("n", "нельзя");
		
		optionGroup.addItem("s");
		optionGroup.setItemCaption("s", "по-необходимости");
		
		optionGroup.addItem("y");
		optionGroup.setItemCaption("y", "обязательно");
		
		optionGroup.select("s");
		optionGroup.setNullSelectionAllowed(false);
		optionGroup.setHtmlContentAllowed(true);
		optionGroup.setImmediate(true);
		
		additionalParamLayout.addComponent(optionGroup);

		return additionalParamLayout;
	}

}

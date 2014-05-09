package diploma.webcad.view.components.gena;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.core.init.SpringContextHelper;
import diploma.webcad.core.service.GenaService;
import diploma.webcad.view.WebCadUI;
import diploma.webcad.view.components.VerticalSeparator;
import diploma.webcad.view.components.gena.GenaMMParamSelector.MooreMealy;

public class GenaParametersSelector extends VerticalLayout {
	
	private static Logger log = LoggerFactory.getLogger(GenaParametersSelector.class);
	
	private SpringContextHelper springContextHelper;

	private GenaService genaService;

	private static final long serialVersionUID = -5697510503372938703L;
	
	private GenaMMParamSelector genaMooreParamSelector;
	
	private GenaMMParamSelector genaMeelyParamSelector;
	
	private GenaCMParamSelector genaCMParamSelector;
	
	private GenaCSParamSelector genaCSParamSelector;
	
	private Label parametersLabel;
	
	private Component selectedComponent;
	
	public GenaParametersSelector () {
		springContextHelper = WebCadUI.getCurrent().getSessionState().getHelper();
		genaService = springContextHelper.getBean(GenaService.class);
		
		setSizeFull();
		addStyleName("full-width");
		setSpacing(true);

		genaMooreParamSelector = new GenaMMParamSelector(MooreMealy.MOORE);
		genaMeelyParamSelector = new GenaMMParamSelector(MooreMealy.MEELY);
		genaCMParamSelector = new GenaCMParamSelector();
		genaCSParamSelector = new GenaCSParamSelector();
		
		selectedComponent = genaCMParamSelector;
		
		OptionGroup optionGroup = new OptionGroup("Select an run option:");
		optionGroup.setSizeUndefined();
		optionGroup.setNullSelectionAllowed(false);
		optionGroup.setHtmlContentAllowed(true);
		optionGroup.setImmediate(true);
		optionGroup.addItem(genaMooreParamSelector);
		optionGroup.setItemCaption(genaMooreParamSelector, "Moore");
		optionGroup.addItem(genaMeelyParamSelector);
		optionGroup.setItemCaption(genaMeelyParamSelector, "Mealy");
		optionGroup.addItem(genaCMParamSelector);
		optionGroup.setItemCaption(genaCMParamSelector, "Common memory");
		optionGroup.addItem(genaCSParamSelector);
		optionGroup.setItemCaption(genaCSParamSelector, "Code sharing");
		optionGroup.select(selectedComponent);
		
		final VerticalLayout additionalParamLayout = new VerticalLayout();
		HorizontalLayout paramChoserLayout = new HorizontalLayout(optionGroup, new VerticalSeparator(), additionalParamLayout);
		paramChoserLayout.setSpacing(true);
		addComponent(paramChoserLayout);
		
		optionGroup.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -1406584044462575876L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				additionalParamLayout.removeAllComponents();
				selectedComponent = (Component) event.getProperty().getValue();
				additionalParamLayout.addComponent(selectedComponent);
				parametersLabel.setValue(selectedComponent.toString());
			}
		});
		
		additionalParamLayout.addComponent(selectedComponent);
		
		TextArea descrTextArea = new TextArea("Put your XML decription here:");
		descrTextArea.addStyleName("full-width");
		addComponent(descrTextArea);
		
		parametersLabel =  new Label(selectedComponent.toString());
		parametersLabel.setImmediate(true);
	}
	
	public String getParameters () {
		return selectedComponent.toString();
	}

}

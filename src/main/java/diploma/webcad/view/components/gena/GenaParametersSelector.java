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

import diploma.webcad.core.init.SpringContext;
import diploma.webcad.core.service.GenaService;
import diploma.webcad.view.WebCadUI;
import diploma.webcad.view.components.VerticalSeparator;
import diploma.webcad.view.components.gena.GenaMMParamSelector.MooreMealy;

public class GenaParametersSelector extends VerticalLayout {
	
	private static Logger log = LoggerFactory.getLogger(GenaParametersSelector.class);
	
	private SpringContext springContextHelper;

	private GenaService genaService;

	private static final long serialVersionUID = -5697510503372938703L;
	
	private GenaMMParamSelector mooreParamSelector;
	
	private GenaMMParamSelector meelyParamSelector;
	
	private GenaCMParamSelector cMParamSelector;
	
	private GenaCSParamSelector cSParamSelector;
	
	private Label parametersLabel;
	
	private Component selectedComponent;
	
	public GenaParametersSelector () {
		springContextHelper = WebCadUI.getCurrent().getSessionState().getContext();
		genaService = springContextHelper.getBean(GenaService.class);
		
		setCaption("Choose run parameters");
		setSizeFull();
		setSpacing(true);
		
		

		
	}
	
	public String getParameters () {
		return "";
	}

}

package diploma.webcad.view.components.gena;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class GenaMMParamSelector extends VerticalLayout {

	private static final long serialVersionUID = 3050759398614100805L;
	
	public enum MooreMealy {
		MOORE("Moore"),
		MEELY("Meely");
		
		private String name;
		
		private MooreMealy(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
	
	private TextField vTextField;
	
	private TextField tTextField;

	private MooreMealy mooreMealy;

	public GenaMMParamSelector(MooreMealy mooreMealy) {
		this.mooreMealy = mooreMealy;
		setImmediate(true);
		HorizontalLayout horizontalLayout;
		
		vTextField = new TextField();
		vTextField.setValue("0");
		vTextField.setColumns(2);
		horizontalLayout = new HorizontalLayout(vTextField, new Label("кодирование вершин ГСА [0-3]"));
		horizontalLayout.setSpacing(true);
		addComponent(horizontalLayout);
		
		tTextField = new TextField();
		tTextField.setValue("0");
		tTextField.setColumns(2);
		horizontalLayout = new HorizontalLayout(tTextField, new Label("использование промежуточных термов [0-1]"));
		horizontalLayout.setSpacing(true);
		addComponent(horizontalLayout);
	}

	@Override
	public String toString() {
		return "--" + mooreMealy.getName() + " -v " + vTextField.getValue() + " -t " + tTextField.getValue();
	}

}

package diploma.webcad.view.components;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.vaadin.data.Property;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitHandler;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.TransactionalPropertyWrapper;
import com.vaadin.data.util.converter.Converter.ConversionException;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

import diploma.webcad.core.model.Language;

@SuppressWarnings({ "serial", "rawtypes" })
public class LocalizedTextField extends CustomField<Map> implements CommitHandler {

	public static final int MODE_TEXT_FIELD = 0;
	public static final int MODE_TEXT_AREA = 1;
	public static final int MODE_RICH_TEXT_AREA = 2;
	
	
	private int mode = MODE_TEXT_FIELD;
	private FieldGroup fieldGroup;
	private List<Language> langs;
	protected Language currentLanguage = null;
	
	public LocalizedTextField(final List<Language> langs) {
		this(langs, MODE_TEXT_FIELD);
	}
	
	@SuppressWarnings("unchecked")
	public LocalizedTextField(List<Language> langs, int mode) {
		if(mode != MODE_TEXT_FIELD && mode != MODE_TEXT_AREA && mode != MODE_RICH_TEXT_AREA)
			throw new IllegalArgumentException("Wrong mode value");
		this.mode = mode;
		setLanguages(langs);
		setValue(new TreeMap<Language, String>());
		for(Language lang: getLanguages())
			getValue().put(lang, "");
		fieldGroup = createFieldGroup();
		addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
				for(Language lang: getLanguages()) {
					if(!getValue().keySet().contains(lang)) {
						getValue().put(lang, "");
					}
				}
				Map<Language, String> newValue = (Map<Language, String>) event.getProperty().getValue();
				for(Object key: newValue.keySet()) {
					Field<String> field = (Field<String>) fieldGroup.getField(key);
					field.setValue((String) newValue.get(key));
				}
			}
		});
	}
	
	public List<Language> getLanguages() {
		return langs;
	}

	public void setLanguages(List<Language> langs) {
		this.langs = langs;
	}

	@Override
	protected Component initContent() {
		if(mode == MODE_TEXT_FIELD)
			return generatePlainContent();
		else
			return generateTabedContent();
	}

	private FieldGroup createFieldGroup() {
		fieldGroup = new FieldGroup();
		for(Object key: getValue().keySet()) {
			Language lang = (Language) key;
			Field field = null;
			switch (mode) {
				case MODE_TEXT_FIELD:
					field = new TextField(lang.getName());
					break;
				case MODE_TEXT_AREA:
					field = new TextArea(lang.getName());
					break;
				case MODE_RICH_TEXT_AREA:
					field = new RichTextArea(lang.getName());
					break;
			}
			((AbstractComponent) field).setData(lang);
			Property<String> textProperty = new TransactionalPropertyWrapper<String>(new ObjectProperty<String>(""));
			field.setPropertyDataSource(textProperty);
			field.setWidth(100, Unit.PERCENTAGE);
			fieldGroup.bind(field, lang);
		}
		fieldGroup.addCommitHandler(this);
		return fieldGroup;
	}
	
	private Component generatePlainContent() {
		FormLayout formLayout = new FormLayout(fieldGroup.getFields().toArray(new Field[fieldGroup.getFields().size()]));
		return formLayout;
	}

	private Component generateTabedContent() {
		TabSheet tabSheet = new TabSheet(fieldGroup.getFields().toArray(new Field[fieldGroup.getFields().size()]));
		tabSheet.addSelectedTabChangeListener(new SelectedTabChangeListener() {
			
			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				AbstractComponent field = (AbstractComponent) event.getTabSheet().getSelectedTab();
				currentLanguage  = (Language) field.getData();
			}
		});
		currentLanguage = (Language) ((AbstractComponent)tabSheet.getSelectedTab()).getData();
		return tabSheet;
	}
	
	@Override
	public void commit() throws SourceException, InvalidValueException {
		try {
			fieldGroup.commit();
		} catch (CommitException e) {
			e.printStackTrace();
		}
		super.commit();
	}

	@Override
	public Class<? extends Map> getType() {
		return Map.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void preCommit(CommitEvent commitEvent) throws CommitException {
		Collection<Object> propertyIds = commitEvent.getFieldBinder().getBoundPropertyIds();
		for(Object id: propertyIds) {
			Language lang = (Language) id;
			Field<String> field = (Field<String>) commitEvent.getFieldBinder().getField(id);
			field.commit();
			getValue().put(lang, field.getValue());
		}
	}

	@Override
	public void postCommit(CommitEvent commitEvent) throws CommitException {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setValue(Map newFieldValue) throws com.vaadin.data.Property.ReadOnlyException, ConversionException {
		Map<Language, String> newValue = (Map<Language, String>) newFieldValue;
		for(Object key: newValue.keySet()) {
			Field<String> field = (Field<String>) fieldGroup.getField(key);
			field.setValue((String) newValue.get(key));
		}
		super.setValue(new TreeMap<Language, String>(newFieldValue));
	}

	public Language getCurrentLanguage() {
		return currentLanguage;
	}
	
}

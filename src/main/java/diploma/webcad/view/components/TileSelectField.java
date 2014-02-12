package diploma.webcad.view.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class TileSelectField extends CustomField<Object> {

	private static final String SELECTED_OPTION_STYLENAME = "tile-select-field-selected";
	
	private Map<Object, String> options;
	private Layout buttonLayout;
	private boolean multiSelectionEnabled;

	private List<ItemCaptionChangeListener> itemCaptionChangeListeners = new ArrayList<ItemCaptionChangeListener>();
	
	public TileSelectField() {
		buttonLayout = new VerticalLayout();
		options = new HashMap<Object, String>();
		setValue(null);
	}
	
	public TileSelectField(boolean multiSelectionEnabled) {
		this();
		setMultiSelectionEnabled(multiSelectionEnabled);
	}
	
	@Override
	public Class<? extends Object> getType() {
		return Object.class;
	}
	
	public void setOptions(Collection<?> options) {
		this.options.clear();
		for(Object option: options)
			this.options.put(option, "");
		repaint();
	}
	
	public void setItemCaption(Object itemId, String caption) {
		options.put(itemId, caption);
		fireItemCaptionChanged(itemId, caption);
	}

	private void fireItemCaptionChanged(Object itemId, String caption) {
		for(ItemCaptionChangeListener l: itemCaptionChangeListeners) {
			l.captionChanged(itemId, caption);
		}
	}
	
	private void addItemCaptionChangeListener(ItemCaptionChangeListener l) {
		itemCaptionChangeListeners.add(l);
	}
	
	private void removeItemCaptionChangeListener(ItemCaptionChangeListener l) {
		itemCaptionChangeListeners.remove(l);
	}

	private void repaint() {
		buttonLayout.removeAllComponents();
		for(ItemCaptionChangeListener l: itemCaptionChangeListeners)
			removeItemCaptionChangeListener(l);
		for(final Object option: options.keySet()) {
			OptionButton optionButton = new OptionButton(StringUtils.defaultString(options.get(option)), option);
			addValueChangeListener(optionButton);
			addItemCaptionChangeListener(optionButton);
			buttonLayout.addComponent(optionButton);
		}
	}
	
	public boolean isMultiSelectionEnabled() {
		return multiSelectionEnabled;
	}

	public void setMultiSelectionEnabled(boolean multiSelectionEnabled) {
		this.multiSelectionEnabled = multiSelectionEnabled;
		if(multiSelectionEnabled)
			setValue(new ArrayList<Object>());
		else
			setValue(null);
		fireValueChange(true);
	}
	
	private class OptionButton extends Button implements ValueChangeListener, ItemCaptionChangeListener {

		private final Object option;

		public OptionButton(String caption, final Object option) {
			super(caption, new ClickListener() {
				
				@SuppressWarnings("unchecked")
				@Override
				public void buttonClick(ClickEvent event) {
					if(multiSelectionEnabled) {
						Collection<Object> currentValue = (Collection<Object>) getValue();
						if(currentValue.contains(option))
							currentValue.remove(option);
						else
							currentValue.add(option);
						fireValueChange(true);
					} else {
						Object currentValue = getValue();
						if(ObjectUtils.equals(option, currentValue))
							setValue(null);
						else
							setValue(option);
						fireValueChange(true);
					}
				}
			});
			this.option = option;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
			if(multiSelectionEnabled) {
				Collection<Object> currentValue = (Collection<Object>) event.getProperty().getValue();
				if(currentValue.contains(option))
					addStyleName(SELECTED_OPTION_STYLENAME);
				else
					removeStyleName(SELECTED_OPTION_STYLENAME);
			} else {
				Object currentValue = getValue();
				if(ObjectUtils.equals(option, currentValue))
					addStyleName(SELECTED_OPTION_STYLENAME);
				else
					removeStyleName(SELECTED_OPTION_STYLENAME);
			}
		}

		@Override
		public void captionChanged(Object itemId, String caption) {
			if(itemId.equals(option))
				setCaption(caption);
		}
		
	}

	@Override
	protected Component initContent() {
		return buttonLayout;
	}
}

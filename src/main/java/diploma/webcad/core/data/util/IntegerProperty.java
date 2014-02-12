package diploma.webcad.core.data.util;

import com.vaadin.data.util.ObjectProperty;

@SuppressWarnings("serial")
public class IntegerProperty extends ObjectProperty<Integer> {

	private final boolean positiveOnly;

	public IntegerProperty(Integer value, boolean positiveOnly) {
		super(value);
		this.positiveOnly = positiveOnly;
	}
	
	public IntegerProperty(Integer value, Class<Integer> type, boolean positiveOnly) {
		super(value, type);
		this.positiveOnly = positiveOnly;
	}
	
	public IntegerProperty(Integer value, Class<Integer> type, boolean readOnly, boolean positiveOnly) {
		super(value, type, readOnly);
		this.positiveOnly = positiveOnly;
	}

	@Override
	public void setValue(Integer newValue)
			throws com.vaadin.data.Property.ReadOnlyException {
		if(positiveOnly && newValue < 0) throw new IllegalArgumentException();
		super.setValue(newValue);
	}
	
}

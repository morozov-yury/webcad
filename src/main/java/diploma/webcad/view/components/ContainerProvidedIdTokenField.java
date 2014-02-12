package diploma.webcad.view.components;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.vaadin.tokenfield.TokenField;

import com.vaadin.data.Item;

public class ContainerProvidedIdTokenField extends TokenField {

	private static final long serialVersionUID = -7048970170820216343L;
	
	public ContainerProvidedIdTokenField() {
		super();
	}

	public ContainerProvidedIdTokenField(String caption) {
		super(caption);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void rememberToken(String tokenId) {
		Item item = cb.addItem(getTokenCaption(tokenId));
        if (item != null) {
            // Sets the caption property, if used
            if (getTokenCaptionPropertyId() != null) {
            	cb.getContainerProperty(item.getItemProperty("id").getValue(), getTokenCaptionPropertyId()).setValue(tokenId);
            }
            cb.getContainerProperty(item.getItemProperty("id").getValue(), "id")
                    .setValue(item.getItemProperty("id").getValue());
        }
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addToken(Object tokenId) {
		Set<Object> set = (Set<Object>) getValue();
        if (set == null) {
            set = new LinkedHashSet<Object>();
        }
        if (set.contains(tokenId)) {
            return;
        }
        HashSet<Object> newSet = new LinkedHashSet<Object>(set);
        if(getContainerDataSource().containsId(tokenId)) {
	        newSet.add(tokenId);
        } else {
	        Item item = getContainerDataSource().addItem(tokenId);
	        newSet.add(item.getItemProperty("id").getValue());
        }
        setValue(newSet);
	}
	
}

package diploma.webcad.view.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.vaadin.hene.flexibleoptiongroup.FlexibleOptionGroup;
import org.vaadin.hene.flexibleoptiongroup.FlexibleOptionGroupItemComponent;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class HorizontalOptionGroup<T> extends CssLayout {

	private static final long serialVersionUID = -7731496000965557420L;
	
	private FlexibleOptionGroup fop;
	
	private List<Label> labelsList;

	private T selectedItem;

	public HorizontalOptionGroup () {
		addStyleName("horizontal-option-group");
		setSizeUndefined();
		fop = new FlexibleOptionGroup();
		fop.setImmediate(true);	
		labelsList = new ArrayList<Label>();
	}

	private void unselecAllLabels () {
		for (Label label : labelsList) {
			label.removeStyleName("hog-selected-label");
		}
	}

	public void setContainerDataSource(BeanItemContainer<T> container) {
		fop.setContainerDataSource(container);
		
		removeAllComponents();
		for (Iterator<FlexibleOptionGroupItemComponent> iter = fop.getItemComponentIterator(); 
				iter.hasNext();) {
			final FlexibleOptionGroupItemComponent comp = iter.next();
			final Label captionLabel = new Label();
			captionLabel.setSizeUndefined();
			labelsList.add(captionLabel);
	        captionLabel.setCaption(comp.getCaption());
	        
	        T itemId = (T) comp.getItemId();
	        if (itemId.equals(selectedItem)) {
	        	captionLabel.addStyleName("hog-selected-label");
	        	fop.select(comp.getItemId());	
	        }
	        
	        HorizontalLayout horizontalLayout = new HorizontalLayout(comp, captionLabel);
	        horizontalLayout.setSizeUndefined();
	        horizontalLayout.addLayoutClickListener(new LayoutClickListener() {
				private static final long serialVersionUID = -8215692026101968873L;
				@Override
				public void layoutClick(LayoutClickEvent event) {
					unselecAllLabels();
					captionLabel.addStyleName("hog-selected-label");
					fop.select(comp.getItemId());
				}
			});
	        addComponent(horizontalLayout);	
		}
		
		if (selectedItem == null) {
			Label firstItemLabel = labelsList.get(0);
			if (firstItemLabel != null) {
				firstItemLabel.addStyleName("hog-selected-label");
				fop.select(fop.getItemComponentIterator().next().getItemId());
			}
		}
	}

	public void selectItem(T selectedItem) {
		this.selectedItem = selectedItem;
	}
	
	public void addValueChangeListener(ValueChangeListener listener) {
		fop.addValueChangeListener(listener);
	}
	
}

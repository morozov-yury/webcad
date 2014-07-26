package diploma.webcad.view.components;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Tree.TreeDragMode;

public class DeviceSelector extends HorizontalLayout {

	private static final long serialVersionUID = -4705175330103187871L;
	
	private static Logger log = LoggerFactory.getLogger(DeviceSelector.class);

	private HierarchicalContainer leftContainer;
	
	private HierarchicalContainer rightContainer;

	private Tree rightTree;

	private Tree leftTree;

	public DeviceSelector (HierarchicalContainer dataContainer) {
		this.leftContainer = dataContainer;
		this.rightContainer = new HierarchicalContainer();
		this.rightContainer.addContainerProperty("name", String.class, "");
		setSizeFull();
		setSpacing(true);
		
		leftTree = new Tree("Available devices");
		leftTree.setWidth("100%");
		leftTree.setHeight("100%");
		leftTree.addStyleName("device-selector-tree-wrapper");
		addComponent(leftTree);
		leftTree.setContainerDataSource(leftContainer);
		leftTree.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		leftTree.setItemCaptionPropertyId("name");
		leftTree.setDragMode(TreeDragMode.NODE);
		
		rightTree = new Tree("Selected devices");
		rightTree.setWidth("100%");
		rightTree.setHeight("100%");
		rightTree.addStyleName("device-selector-tree-wrapper");
		addComponent(rightTree);
		rightTree.setContainerDataSource(rightContainer);
		rightTree.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		rightTree.setItemCaptionPropertyId("name");
		rightTree.setDragMode(TreeDragMode.NODE);
		
		rightTree.setDropHandler(new DropHandler() {
			
			private static final long serialVersionUID = -474340733164598731L;
			
			@Override
			public AcceptCriterion getAcceptCriterion() {
				// TODO Auto-generated method stub
				return AcceptAll.get();
			}
			
			@Override
			public void drop(DragAndDropEvent event) {
				// TODO Auto-generated method stub
				Object itemId = event.getTransferable().getData("itemId");
				
				Item item = leftContainer.getItem(itemId);
				
				if (rightContainer.getItem(itemId) == null) {
					Item newItem = rightContainer.addItem(itemId);
					newItem.getItemProperty("name").setValue(itemId);
				}
				
				Collection<?> children = leftContainer.getChildren(itemId);
				if (children != null) {
					rightTree.expandItemsRecursively(itemId);
					for (Object childId : children) {
						Item childItem = rightContainer.addItem(childId);
						childItem.getItemProperty("name").setValue(childId);
						rightContainer.setChildrenAllowed(childId, false);
						rightContainer.setParent(childId, itemId);
					}
				} else {
					rightContainer.setChildrenAllowed(itemId, false);
					Object parent = leftContainer.getParent(itemId);
					Item parentItem = rightContainer.getItem(parent);
					if (parentItem == null) {
						parentItem = rightContainer.addItem(parent);
						parentItem.getItemProperty("name").setValue(
								leftContainer.getItem(parent).getItemProperty("name").getValue());
						rightTree.expandItemsRecursively(parent);
					}
					rightContainer.setParent(itemId, parent);
				}
				
				leftContainer.removeItem(itemId);
			}
		});
	}

}

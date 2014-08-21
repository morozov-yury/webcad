package diploma.webcad.view.components;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Container.ItemSetChangeListener;
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

	private static final long serialVersionUID = 2544545306323219448L;

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
		leftTree.setDropHandler(getDropHandler(rightContainer, leftContainer));
		
		rightTree = new Tree("Selected devices");
		rightTree.setWidth("100%");
		rightTree.setHeight("100%");
		rightTree.addStyleName("device-selector-tree-wrapper");
		addComponent(rightTree);
		rightTree.setContainerDataSource(rightContainer);
		rightTree.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		rightTree.setItemCaptionPropertyId("name");
		rightTree.setDragMode(TreeDragMode.NODE);
		rightTree.setDropHandler(getDropHandler(leftContainer, rightContainer));
	}
	
	@SuppressWarnings("unchecked")
	private DropHandler getDropHandler (final HierarchicalContainer sourceContainer, 
			final HierarchicalContainer receiverContainer) {
		return new DropHandler() {
			
			private static final long serialVersionUID = -474340733164598731L;
			
			@Override
			public AcceptCriterion getAcceptCriterion() {
				// TODO Auto-generated method stub
				return AcceptAll.get();
			}
			
			@Override
			public void drop(DragAndDropEvent event) {
				Object itemId = event.getTransferable().getData("itemId");
				final Item item = sourceContainer.getItem(itemId);
				
				if (receiverContainer.getItem(itemId) == null) {
					Item newItem = receiverContainer.addItem(itemId);
					newItem.getItemProperty("name").setValue(
							item.getItemProperty("name").getValue());
				}
				
				if (sourceContainer.areChildrenAllowed(itemId)) {
					Collection<?> children = sourceContainer.getChildren(itemId);
					if (children != null) {
						rightTree.expandItemsRecursively(itemId);
						children = new ConcurrentLinkedQueue<Object>(
								sourceContainer.getChildren(itemId));
						for (Object childId : children) {
							Item childItem = receiverContainer.addItem(childId);
							childItem.getItemProperty("name").setValue(childId);
							receiverContainer.setChildrenAllowed(childId, false);
							receiverContainer.setParent(childId, itemId);
							sourceContainer.removeItem(childId);
						}
					}
				} else {
					receiverContainer.setChildrenAllowed(itemId, false);
					Object parent = sourceContainer.getParent(itemId);
					Item parentItem = receiverContainer.getItem(parent);
					if (parentItem == null && parent != null) {
						parentItem = receiverContainer.addItem(parent);
						parentItem.getItemProperty("name").setValue(
								sourceContainer.getItem(parent).getItemProperty("name").getValue());
						rightTree.expandItemsRecursively(parent);
					}
					receiverContainer.setParent(itemId, parent);
				}
				
				sourceContainer.removeItem(itemId);
			}
		};
	}
	
	public void addItemSetChangeListener (ItemSetChangeListener itemSetChangeListener) {
		rightContainer.addItemSetChangeListener(itemSetChangeListener);
	}
	
	public List<String> listSelectedDevices () {
		return (List<String>) rightContainer.getItemIds();
	}
	
	public void clearSelection () {
		
	}

}

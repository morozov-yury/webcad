package diploma.webcad.view.components;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.vaadin.data.Property;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.converter.Converter.ConversionException;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.MultiFileUpload;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadFinishedHandler;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadStateWindow;

import diploma.webcad.view.model.FileData;

@SuppressWarnings({ "serial", "rawtypes" })
public class MultiFileUploadField extends CustomField<List> implements UploadFinishedHandler {

	private VerticalLayout fileListLayout = new VerticalLayout();
	
	public MultiFileUploadField() {
	}
	
	@Override
	public Class<? extends List> getType() {
		return List.class;
	}

	@Override
	protected Component initContent() {
		VerticalLayout vl = new VerticalLayout();
		MultiFileUpload multiFileUpload = new MultiFileUpload(this, new UploadStateWindow());
		multiFileUpload.setImmediate(true);
		vl.addComponent(multiFileUpload);
		vl.addComponent(fileListLayout);
		return vl;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handleFile(InputStream dataInputStream, String fileName, String mimeType, long length) {
		try {
			final byte[] data = IOUtils.toByteArray(dataInputStream); 
					//new byte[(int) length];
			
			dataInputStream.read(data);
			getValue().add(new FileData(data, mimeType, fileName, data.length));
			repaint();
			fireValueChange(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void repaint() {
		fileListLayout.removeAllComponents();
		for(Object o: getValue()) {
			final FileData fd = (FileData) o;
			final HorizontalLayout fileLayout = new HorizontalLayout();
			fileLayout.addComponent(new Label(fd.getName()));
			fileLayout.addComponent(new Button("X", new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					fileListLayout.removeComponent(fileLayout);
					getValue().remove(fd);
				}
			}));
			fileListLayout.addComponent(fileLayout);
		}
	}
	
	@Override
	protected void setValue(List newFieldValue, boolean repaintIsNotNeeded)
			throws com.vaadin.data.Property.ReadOnlyException,
			ConversionException, InvalidValueException {
		super.setValue(newFieldValue, repaintIsNotNeeded);
		repaint();
	}
	
	@Override
	public void setPropertyDataSource(Property newDataSource) {
		super.setPropertyDataSource(newDataSource);
		setValue((List) newDataSource.getValue());
	}
	
	/*
	@Override
	public void setValue(List newFieldValue)
			throws com.vaadin.data.Property.ReadOnlyException,
			ConversionException {
		throw new ReadOnlyException();
	}
	*/
	
}

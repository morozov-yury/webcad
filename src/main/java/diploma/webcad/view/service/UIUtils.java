package diploma.webcad.view.service;

import java.io.InputStream;

import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;

public class UIUtils {
	
	public static StreamResource createResource(final InputStream stream, String fileName) {
		return new StreamResource(new StreamSource() {
			private static final long serialVersionUID = 5619306792965353218L;
			@Override
			public InputStream getStream() {
				return stream;
			}
		}, fileName);
	}

}

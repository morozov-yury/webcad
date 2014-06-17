package diploma.webcad.view.model;

import java.util.Properties;

public class PageProperties extends Properties {

	private static final long serialVersionUID = 453175988337494641L;
	
	public PageProperties () {
		
	}
	
	public PageProperties (Properties properties) {
		putAll(properties);
	}

	@Override
	public synchronized String toString() {
		StringBuilder properies = new StringBuilder("/?");
		for (java.util.Map.Entry<Object, Object> entry : entrySet()) {
			properies.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		return properies.substring(0, properies.length() - 1);
	}

}

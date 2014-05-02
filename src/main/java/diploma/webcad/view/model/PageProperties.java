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
		String properies = "/?";
		for (java.util.Map.Entry<Object, Object> entry : entrySet()) {
			properies += entry.getKey() + "=" + entry.getValue() + "&";
		}
		return properies.substring(0, properies.length() - 1);
	}

}

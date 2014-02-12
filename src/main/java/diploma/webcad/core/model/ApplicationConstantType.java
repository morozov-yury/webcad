package diploma.webcad.core.model;

/**
 * The types for constants
 * @author Arktos
 */

public enum ApplicationConstantType {
	DEFAULT, ABOUT, SYSTEM, VIEW, SECURITY, NAVIGATION, CONNECTORS, SETTINGS;
	
	public static ApplicationConstantType getDefault() {
		return DEFAULT;
	}
}

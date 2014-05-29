package diploma.webcad.core.model.constant;

/**
 * The types for constants
 * @author Arktos
 */

public enum AppConstantType {
	DEFAULT, ABOUT, SYSTEM, VIEW, SECURITY, NAVIGATION, CONNECTORS, SETTINGS;
	
	public static AppConstantType getDefault() {
		return DEFAULT;
	}
}
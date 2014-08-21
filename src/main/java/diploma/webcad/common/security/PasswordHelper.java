package diploma.webcad.common.security;

import org.apache.commons.lang.RandomStringUtils;

public class PasswordHelper {
	
	public static String generatePassword() {
		return generateNumericCode(8);
	}
	
	public static String generateNumericCode(int count) {
		return RandomStringUtils.randomNumeric(count);
	}
	
	public static String generateAlphaNumericCode(int count) {
		return RandomStringUtils.randomAlphanumeric(count);
	}
}

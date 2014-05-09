package diploma.webcad.core.util.text;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class TextHelper {

	public static String replace(String text, Map<String, String> parameters) {
		if(parameters == null || text == null) return text;
		String src = text;
		Pattern p = Pattern.compile("\\$\\{([^}]*)\\}");
        Matcher m = p.matcher(src);
        while(m.find()) {
        	String str = parameters.get(m.group(1));
            src = m.replaceFirst(StringUtils.defaultString(str));
            m = p.matcher(src);
        }
        return src;
	}
	
	
	
}

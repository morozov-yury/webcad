package diploma.webcad.manager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.server.Page;

@Component
@Scope("singleton")
public class MappingProcessor {
	
	public void processMapping(String path) {
		path = StringUtils.defaultString(path);
		Page.getCurrent().setUriFragment("!" + path);
	}
}

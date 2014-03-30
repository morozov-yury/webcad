package diploma.webcad.core.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class SshManager {
	
	@Autowired 
	private WebCadPropertyManager propertyManager;
	
	

}

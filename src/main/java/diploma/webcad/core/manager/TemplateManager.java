package diploma.webcad.core.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import diploma.webcad.core.dao.TemplateDao;
import diploma.webcad.core.model.Template;

@Service
@Scope("singleton")
public class TemplateManager {
	
	@Autowired
	private TemplateDao templateDao;

	public Template getTmplateByName(String name) {
		return this.templateDao.read(name);
	}
	
}

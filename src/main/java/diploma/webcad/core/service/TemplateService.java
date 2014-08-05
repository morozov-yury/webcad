package diploma.webcad.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import diploma.webcad.core.dao.TemplateDao;
import diploma.webcad.core.model.Template;

@Service
@Scope("singleton")
public class TemplateService {
	
	@Autowired
	private TemplateDao templateDao;

	public Template getTmplateByName(String name) {
		return this.templateDao.get(name);
	}
	
}

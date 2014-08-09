package diploma.webcad.core.dao;

import diploma.webcad.core.model.resource.Template;

public interface TemplateDao extends BaseDao<Template, String> {
	
	public Boolean isExist(String id);
	
}

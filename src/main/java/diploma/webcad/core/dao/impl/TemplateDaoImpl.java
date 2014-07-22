package diploma.webcad.core.dao.impl;

import org.springframework.stereotype.Repository;

import diploma.webcad.core.dao.TemplateDao;
import diploma.webcad.core.model.Template;

@Repository
public class TemplateDaoImpl extends BaseDaoImpl<Template, String> implements TemplateDao {

	public TemplateDaoImpl() {
		super(Template.class);
	}

}

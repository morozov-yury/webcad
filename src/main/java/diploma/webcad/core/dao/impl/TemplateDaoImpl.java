package diploma.webcad.core.dao.impl;

import org.springframework.stereotype.Component;

import diploma.webcad.core.dao.TemplateDao;
import diploma.webcad.core.model.Template;

@Component("templateDao")
public class TemplateDaoImpl extends BaseDaoImpl<Template, String> implements TemplateDao {

	public TemplateDaoImpl() {
		super(Template.class);
	}

}

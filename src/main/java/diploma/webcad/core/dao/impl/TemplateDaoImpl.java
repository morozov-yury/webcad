package diploma.webcad.core.dao.impl;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import diploma.webcad.core.dao.TemplateDao;
import diploma.webcad.core.model.resource.Template;

@Repository
public class TemplateDaoImpl extends BaseDaoImpl<Template, String> implements TemplateDao {

	public TemplateDaoImpl() {
		super(Template.class);
	}

	@Override
	public Boolean isExist(String id) {
		return ((Long) makeCriteria()
				.add(Restrictions.idEq(id))
				.setMaxResults(1)
				.setProjection(Projections.rowCount())
				.uniqueResult()) != 0;
	}

}

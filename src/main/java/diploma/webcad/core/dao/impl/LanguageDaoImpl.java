package diploma.webcad.core.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import diploma.webcad.core.dao.LanguageDao;
import diploma.webcad.core.model.Language;

@Component("languageDao")
public class LanguageDaoImpl extends BaseDaoImpl<Language, String> implements LanguageDao {
	
	public LanguageDaoImpl(){
		super(Language.class);
	}

	@SuppressWarnings("unchecked")
	public List<Language> listByNameIgnoreCase(String name) {
		return makeCriteria().add(Restrictions.ilike("name", name)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}
}

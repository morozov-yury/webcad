package diploma.webcad.core.dao;

import java.util.List;

import diploma.webcad.core.model.Language;


public interface LanguageDao extends BaseDao<Language, String> {
	
	List<Language> listByNameIgnoreCase(String name);
}

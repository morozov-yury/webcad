package diploma.webcad.core.dao;

import java.util.Collection;
import java.util.List;

import diploma.webcad.core.model.ApplicationResource;
import diploma.webcad.core.model.ApplicationResourceCategoryPrefix;
import diploma.webcad.core.model.Language;


/**
 * Better use ContentManager instead
 */
public interface ApplicationResourceDao extends BaseDao<ApplicationResource, String>{

	List<String> listIds();

	String getPrevId(String itemId, ApplicationResourceCategoryPrefix prefix,
			Language language, List<String> filters);

	String getNextId(String itemId, ApplicationResourceCategoryPrefix prefix,
			Language language, List<String> filters);

	String getLastId(ApplicationResourceCategoryPrefix prefix,
			Language language, List<String> filters);

	List<String> listIds(ApplicationResourceCategoryPrefix prefix,
			Language language, List<String> filters, int startIndex,
			int numberOfItems);

	Collection<String> listIds(ApplicationResourceCategoryPrefix prefix,
			Language language, List<String> filters);

	Long getCount(ApplicationResourceCategoryPrefix prefix, Language language,
			List<String> filters);

	boolean isExist(String id);

	String getFirstId(ApplicationResourceCategoryPrefix prefix,
			Language language, List<String> filters);
	
}

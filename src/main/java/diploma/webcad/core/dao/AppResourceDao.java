package diploma.webcad.core.dao;

import java.util.Collection;
import java.util.List;

import diploma.webcad.core.model.Language;
import diploma.webcad.core.model.resource.AppCategoryPrefix;
import diploma.webcad.core.model.resource.AppResource;


/**
 * Better use ContentManager instead
 */
public interface AppResourceDao extends BaseDao<AppResource, String>{

	List<String> listIds();

	String getPrevId(String itemId, AppCategoryPrefix prefix,
			Language language, List<String> filters);

	String getNextId(String itemId, AppCategoryPrefix prefix,
			Language language, List<String> filters);

	String getLastId(AppCategoryPrefix prefix,
			Language language, List<String> filters);

	List<String> listIds(AppCategoryPrefix prefix,
			Language language, List<String> filters, int startIndex,
			int numberOfItems);

	Collection<String> listIds(AppCategoryPrefix prefix,
			Language language, List<String> filters);

	Long getCount(AppCategoryPrefix prefix, Language language,
			List<String> filters);

	boolean isExist(String id);

	String getFirstId(AppCategoryPrefix prefix,
			Language language, List<String> filters);
	
}

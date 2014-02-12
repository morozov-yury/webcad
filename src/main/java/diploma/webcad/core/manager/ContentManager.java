package diploma.webcad.core.manager;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import diploma.webcad.core.dao.ApplicationResourceDao;
import diploma.webcad.core.dao.LanguageDao;
import diploma.webcad.core.dao.TemplateDao;
import diploma.webcad.core.model.ApplicationResource;
import diploma.webcad.core.model.ApplicationResourceCategoryPrefix;
import diploma.webcad.core.model.Language;

@Service
@Scope("singleton")
public class ContentManager {

	@Autowired
	private SystemManager systemManager;
	
	@Autowired
	private ApplicationResourceDao applicationResourceDao;
	
	@Autowired
	private LanguageDao languageDao;
	
	@Autowired
	private TemplateDao templateDao;
	
	//*******************************
	//********* LANGUAGES ***********
	//*******************************
	public Language getLanguage(String iso) {
		return languageDao.read(iso);
	}
	
	public Language getDefaultLanguage() {
		String defaultLanguageIso = systemManager.getConstantValue("default_language");
		return getLanguage(defaultLanguageIso);
	}
	
	public Iterable<Language> listLanguageByNameIgnoreCase(String arg) {
		return languageDao.listByNameIgnoreCase(arg);
	}
	
	public List<Language> listLanguages() {
		return languageDao.list();
	}

	//*******************************************
	//********* APPLICATION RESOURCES ***********
	//*******************************************
	
	public ApplicationResource getApplicationResource(String key) {
		return applicationResourceDao.read(key);
	}

	public String resolveApplicationResource(String key, Language sessionLanguage) {
		if (key == null)
			return "";
		ApplicationResource resource = getApplicationResource(key);
		if (resource == null) {
			return "!!!" + key + "!!!";
		} else {
			return resolveApplicationResource(resource, sessionLanguage);
		}
	}
	
	public String resolveApplicationResource(Class<?> clazz, String keySuffix,
			Language language) {
		String keyPrefix = clazz.getCanonicalName().toLowerCase();
		return resolveApplicationResource(keyPrefix + "." + keySuffix, language);
	}
	
	public String resolveApplicationResource(ApplicationResource resource, Language sessionLanguage) {
		String result = null;
		if (resource == null) {
			result = "!!!nullResource!!!";
		} else {
			if (resource.containsLanguage(sessionLanguage))
				result = resource.getLangValue(sessionLanguage);
			else {
				Language defLanguage = getDefaultLanguage();
				if (resource.containsLanguage(defLanguage))
					result = resource.getLangValue(defLanguage);
				else
					result = "!!!" + resource.getId() + "@" + sessionLanguage.getIso() + "!!!";
			}
		}
		return result;
	}
	
	public String getNextAplicationResourceId(String itemId, ApplicationResourceCategoryPrefix prefix,
			Language language, List<String> filters) {
		return applicationResourceDao.getNextId(itemId, prefix, language, filters);
	}

	public String getPrevAplicationResourceId(String itemId, ApplicationResourceCategoryPrefix prefix,
			Language language, List<String> filters) {
		return applicationResourceDao.getPrevId(itemId, prefix, language, filters);
	}

	public String getFirstAplicationResourceId(ApplicationResourceCategoryPrefix prefix, Language language,
			List<String> filters) {
		return applicationResourceDao.getFirstId(prefix, language, filters);
	}

	public String getLastAplicationResourceId(ApplicationResourceCategoryPrefix prefix, Language language,
			List<String> filters) {
		return applicationResourceDao.getLastId(prefix, language, filters);
	}

	public List<String> listApplicationResourcesIds(ApplicationResourceCategoryPrefix prefix, Language language,
			List<String> filters, int startIndex, int numberOfItems) {
		return applicationResourceDao.listIds(prefix, language, filters, startIndex, numberOfItems);
	}

	public Collection<String> listApplicationResourcesIds(ApplicationResourceCategoryPrefix prefix, Language language,
			List<String> filters) {
		return applicationResourceDao.listIds(prefix, language, filters);
	}

	public Long getApplicationResourcesCount(ApplicationResourceCategoryPrefix prefix, Language language,
			List<String> filters) {
		return applicationResourceDao.getCount(prefix, language, filters);
	}

	public synchronized ApplicationResource createNewApplicationResource(ApplicationResourceCategoryPrefix prefix, Language language,
			List<String> filters) {
		if(filters.size() > 0) {
			System.out.println("Multifilters at createNewApplicationResource(): " + filters.toString());
		}
		String id = prefix.getPrefix() + System.currentTimeMillis();
		while(isApplicationResourceExist(id)) id = prefix.getPrefix() + System.currentTimeMillis();
		ApplicationResource applicationResource = new ApplicationResource(id);
		applicationResource.setLangValue(language, filters.get(0));
		saveApplicationResource(applicationResource);
		return applicationResource;
	}

	private void saveApplicationResource(ApplicationResource applicationResource) {
		applicationResourceDao.saveOrUpdate(applicationResource);
	}

	public boolean isApplicationResourceExist(String id) {
		return applicationResourceDao.isExist(id);
	}

}

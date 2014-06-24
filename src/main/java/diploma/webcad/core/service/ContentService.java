package diploma.webcad.core.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import diploma.webcad.core.dao.AppResourceDao;
import diploma.webcad.core.dao.LanguageDao;
import diploma.webcad.core.dao.TemplateDao;
import diploma.webcad.core.model.Language;
import diploma.webcad.core.model.resource.AppCategoryPrefix;
import diploma.webcad.core.model.resource.AppResource;

@Service
@Scope("singleton")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class ContentService {

	@Autowired
	private SystemService systemManager;
	
	@Autowired
	private AppResourceDao applicationResourceDao;
	
	@Autowired
	private LanguageDao languageDao;
	
	@Autowired
	private TemplateDao templateDao;

	public Language getLanguage(String iso) {
		return languageDao.read(iso);
	}
	
	public Language getDefaultLanguage() {
		String defaultLanguageIso = systemManager.getConstantValue("default_language");
		return getLanguage(defaultLanguageIso);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void addLanguage (Language language) {
		languageDao.saveOrUpdate(language);
	}
	
	public Iterable<Language> listLanguageByNameIgnoreCase(String arg) {
		return languageDao.listByNameIgnoreCase(arg);
	}
	
	public List<Language> listLanguages() {
		return languageDao.list();
	}

	public AppResource getAppResource(String key) {
		return applicationResourceDao.read(key);
	}

	public String getAppResource(Object source, String keySuffix) {
		if(!(source instanceof Class)) {
			return getAppResource(source.getClass(), keySuffix);
		} else {
			//return getAppResource((Class<?>) source, keySuffix, sessionInterlayer.getSessionState().getLanguage());
			return null;
		}
	}

	public String getAppResource(Enum<?> value) {
		return getAppResource(value.getClass(), value.name().toLowerCase());
	}

	public String getAppResource(String key, Language sessionLanguage) {
		if (key == null)
			return "";
		AppResource resource = getAppResource(key);
		if (resource == null) {
			return "!!!" + key + "!!!";
		} else {
			return getAppResource(resource, sessionLanguage);
		}
	}
	
	public String getAppResource(Class<?> clazz, String keySuffix,
			Language language) {
		String keyPrefix = clazz.getCanonicalName().toLowerCase();
		return getAppResource(keyPrefix + "." + keySuffix, language);
	}
	
	public String getAppResource(AppResource resource, Language sessionLanguage) {
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
	
	public String getNextAppResourceId(String itemId, AppCategoryPrefix prefix,
			Language language, List<String> filters) {
		return applicationResourceDao.getNextId(itemId, prefix, language, filters);
	}

	public String getPrevAppResourceId(String itemId, AppCategoryPrefix prefix,
			Language language, List<String> filters) {
		return applicationResourceDao.getPrevId(itemId, prefix, language, filters);
	}

	public String getFirstAppResourceId(AppCategoryPrefix prefix, Language language,
			List<String> filters) {
		return applicationResourceDao.getFirstId(prefix, language, filters);
	}

	public String getLastAppResourceId(AppCategoryPrefix prefix, Language language,
			List<String> filters) {
		return applicationResourceDao.getLastId(prefix, language, filters);
	}

	public List<String> listAppResourcesIds(AppCategoryPrefix prefix, Language language,
			List<String> filters, int startIndex, int numberOfItems) {
		return applicationResourceDao.listIds(prefix, language, filters, startIndex, numberOfItems);
	}

	public Collection<String> listAppResourcesIds(AppCategoryPrefix prefix, Language language,
			List<String> filters) {
		return applicationResourceDao.listIds(prefix, language, filters);
	}

	public Long getAppResourcesCount(AppCategoryPrefix prefix, Language language,
			List<String> filters) {
		return applicationResourceDao.getCount(prefix, language, filters);
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public synchronized AppResource createNewAppResource(AppCategoryPrefix prefix, Language language,
			List<String> filters) {
		if(filters.size() > 0) {
			System.out.println("Multifilters at createNewApplicationResource(): " + filters.toString());
		}
		String id = prefix.getPrefix() + System.currentTimeMillis();
		while(isAppResourceExist(id)) id = prefix.getPrefix() + System.currentTimeMillis();
		AppResource applicationResource = new AppResource(id);
		applicationResource.setLangValue(language, filters.get(0));
		saveAppResource(applicationResource);
		return applicationResource;
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void saveAppResource(AppResource applicationResource) {
		applicationResourceDao.saveOrUpdate(applicationResource);
	}

	public boolean isAppResourceExist(String id) {
		return applicationResourceDao.isExist(id);
	}

}

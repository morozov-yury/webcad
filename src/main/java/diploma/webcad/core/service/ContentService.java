package diploma.webcad.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import diploma.webcad.core.dao.LanguageDao;
import diploma.webcad.core.dao.TemplateDao;
import diploma.webcad.core.model.Language;

@Service
@Scope("singleton")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class ContentService {

	@Autowired
	private SystemService systemManager;

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

	public String getAppResource(Object source, String keySuffix) {
		if(!(source instanceof Class)) {
			return getAppResource(source.getClass(), keySuffix);
		} else {
			//return getAppResource((Class<?>) source, keySuffix, sessionInterlayer.getSessionState().getLanguage());
			return null;
		}
	}

}

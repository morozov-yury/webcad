package diploma.webcad.core.service;

import java.net.URI;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.server.Page;

import diploma.webcad.core.dao.AppConstantDao;
import diploma.webcad.core.dao.LanguageDao;
import diploma.webcad.core.dao.TemplateDao;
import diploma.webcad.core.data.appconstants.Constant;
import diploma.webcad.core.data.appconstants.Constants;
import diploma.webcad.core.model.AppConstant;
import diploma.webcad.core.model.AppConstantType;
import diploma.webcad.core.model.Language;
import diploma.webcad.core.model.resource.Template;

@Service
@Scope("singleton")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class ContentService {

	@Autowired
	private LanguageDao languageDao;
	
	@Autowired
	private TemplateDao templateDao;
	
	@Autowired
	private AppConstantDao applicationConstantDao;

	public Language getLanguage(String iso) {
		return languageDao.get(iso);
	}
	
	public Language getDefaultLanguage() {
		String defaultLanguageIso = getConstantValue("default_language");
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
	
	public Template getTemplate (String id) {
		return templateDao.get(id);
	}
	
	public String getConstantValue(String id) {
		if(id == null) return null;
		final AppConstant constant = getApplicationConstant(id);
		if(constant == null) return "";
		return constant.getValue();
	}
	
	public AppConstant getApplicationConstant(String id) {
		if(id == null) return null;
		return applicationConstantDao.get(id);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void saveApplicationConstant(AppConstant constant) {
		if(constant == null) {
			return;
		}
		try {
			applicationConstantDao.saveOrUpdate(constant);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readConstants(Constants constants) {
		for(Constant constant : constants.getConstants()) {
			if(getApplicationConstant(constant.getKey()) == null) {
				AppConstantType type;
				try {
					type = AppConstantType.valueOf(constant.getType());
				} catch(Exception ex) {
					type = AppConstantType.getDefault();
				}
				AppConstant appConstant = new AppConstant(
						constant.getKey(), constant.getValue(), constant.getDescription(),  type);
				saveApplicationConstant(appConstant);
			}
		}
	}
	
	public String getApplicationPath() {
		URI location = Page.getCurrent().getLocation();
		StringBuilder sb = new StringBuilder();
		sb.append(location.getScheme()).append("://").append(location.getHost());
		if(location.getPort() >= 0) {
			sb.append(":").append(location.getPort());
		}
		sb.append(StringUtils.removeEnd(location.getPath(), "/"));
		return sb.toString();
	}

}

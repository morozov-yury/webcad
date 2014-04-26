package diploma.webcad.core.init;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import diploma.webcad.common.content.Resources;
import diploma.webcad.common.content.UTF8Control;
import diploma.webcad.common.security.MD5Helper;
import diploma.webcad.core.dao.ApplicationResourceDao;
import diploma.webcad.core.dao.LanguageDao;
import diploma.webcad.core.dao.UserDao;
import diploma.webcad.core.model.ApplicationConstant;
import diploma.webcad.core.model.ApplicationConstantType;
import diploma.webcad.core.model.ApplicationResource;
import diploma.webcad.core.model.ApplicationResourceValue;
import diploma.webcad.core.model.Language;
import diploma.webcad.core.model.User;
import diploma.webcad.core.service.SystemService;

public class Installer {

	private static final Logger log = Logger.getLogger(Installer.class);
	private SpringContextHelper helper;
	//private ServletContext servletContext; //Not used

	public Installer(SpringContextHelper helper, ServletContext servletContext) {
		this.helper = helper;
		//this.servletContext = servletContext;
	}
	
	public void install() {
		log.info("initLanguages...");
		initLanguages();
		log.info("loadApplicationResources...");
		loadApplicationResources();
		log.info("initSystemWallet...");
		createSystemAccount();
		log.info("loadRegions...");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String marker = "installed: " + format.format(new Date());
		log.info("INSTALLATION FINISH: " + marker);
		ApplicationConstant constant = new ApplicationConstant("installed", marker, "Installation flag", ApplicationConstantType.SYSTEM);
		helper.getBean(SystemService.class).saveApplicationConstant(constant );
		log.info("INSTALLATION END.");
	}

	
	private void createSystemAccount() {
		User systemUser = new User("2@2", MD5Helper.getHash("2"));
		UserDao userDao = helper.getBean(UserDao.class);
		userDao.saveOrUpdate(systemUser);
	}

	private void initLanguages() {
		try {
			LanguageDao languageDao = helper.getBean(LanguageDao.class);
			Language l = languageDao.read("en");
			if (l == null) {
				l = new Language();
				l.setIso("en");
				l.setName("English");
				languageDao.saveOrUpdate(l);
			}
			l = languageDao.read("ru");
			if (l == null) {
				l = new Language();
				l.setIso("ru");
				l.setName("Russian");
				languageDao.saveOrUpdate(l);
			}
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	private void loadApplicationResources() {
		LanguageDao languageDao = (LanguageDao) helper.getBean("languageDao");
		ApplicationResourceDao applicationResourceDao = (ApplicationResourceDao) helper.getBean("applicationResourceDao");
		List<Language> languages = languageDao.list();
		for (Language language : languages) {
			ResourceBundle bundle = ResourceBundle.getBundle(Resources.APPLICATION_RESOURCE_BUNDLE_BASE, new Locale(language.getIso()), new UTF8Control());
			for(String key: bundle.keySet()) {
				ApplicationResource appResource = applicationResourceDao.read(key);
				if (appResource == null) {
					appResource = new ApplicationResource(key);
				}
				if (!appResource.containsLanguage(language)) {
					String val = bundle.getString(key);
					appResource.getLangs().add(new ApplicationResourceValue(language, val));
					try {
						applicationResourceDao.saveOrUpdate(appResource);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}

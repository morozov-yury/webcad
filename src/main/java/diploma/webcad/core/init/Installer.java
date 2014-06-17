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
import diploma.webcad.core.dao.AppResourceDao;
import diploma.webcad.core.dao.LanguageDao;
import diploma.webcad.core.dao.UserDao;
import diploma.webcad.core.model.Language;
import diploma.webcad.core.model.User;
import diploma.webcad.core.model.UserRole;
import diploma.webcad.core.model.constant.AppConstant;
import diploma.webcad.core.model.constant.AppConstantType;
import diploma.webcad.core.model.resource.AppResource;
import diploma.webcad.core.model.resource.AppValue;
import diploma.webcad.core.service.SystemService;

public class Installer {

	private static final Logger log = Logger.getLogger(Installer.class);
	private SpringContext helper;
	//private ServletContext servletContext; //Not used

	public Installer(SpringContext helper, ServletContext servletContext) {
		this.helper = helper;
		//this.servletContext = servletContext;
	}
	
	public void install() {
		log.info("initLanguages...");
		initLanguages();
		
		log.info("loadApplicationResources...");
		loadApplicationResources();
		
		log.info("initSystemAccount...");
		createSystemAccount();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String marker = "installed: " + format.format(new Date());
		log.info("INSTALLATION FINISH: " + marker);
		AppConstant constant = new AppConstant("installed", marker, "Installation flag", AppConstantType.SYSTEM);
		helper.getBean(SystemService.class).saveApplicationConstant(constant );
		log.info("INSTALLATION END.");
	}

	
	private void createSystemAccount() {
		User systemUser = new User("admin", MD5Helper.getHash("admin"));
		systemUser.setUserRole(UserRole.ADMIN);
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
		LanguageDao languageDao = helper.getBean(LanguageDao.class);
		AppResourceDao applicationResourceDao = helper.getBean(AppResourceDao.class);
		List<Language> languages = languageDao.list();
		for (Language language : languages) {
			ResourceBundle bundle = ResourceBundle.getBundle(Resources.APPLICATION_RESOURCE_BUNDLE_BASE, new Locale(language.getIso()), new UTF8Control());
			for(String key: bundle.keySet()) {
				AppResource appResource = applicationResourceDao.read(key);
				if (appResource == null) {
					appResource = new AppResource(key);
				}
				if (!appResource.containsLanguage(language)) {
					String val = bundle.getString(key);
					appResource.getLangs().add(new AppValue(language, val));
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

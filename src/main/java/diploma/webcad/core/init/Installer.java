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
import diploma.webcad.core.model.AppConstant;
import diploma.webcad.core.model.AppConstantType;
import diploma.webcad.core.model.Language;
import diploma.webcad.core.model.resource.AppResource;
import diploma.webcad.core.model.resource.AppValue;
import diploma.webcad.core.service.ContentService;
import diploma.webcad.core.service.SystemService;
import diploma.webcad.core.service.UserService;

public class Installer {

	private static final Logger log = Logger.getLogger(Installer.class);
	
	private SpringContext helper;

	public Installer(SpringContext helper, ServletContext servletContext) {
		this.helper = helper;
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
		AppConstant constant = new AppConstant("installed", marker, "Installation flag", 
				AppConstantType.SYSTEM);
		helper.getBean(SystemService.class).saveApplicationConstant(constant );
		log.info("INSTALLATION END.");
	}

	
	private void createSystemAccount() {
		UserService userService = helper.getBean(UserService.class);
		ContentService contentService = helper.getBean(ContentService.class);
		
		Language language = contentService.getDefaultLanguage();

		userService.createUser("admin", "admin", language);
		
		userService.createUser("user", "user", language);
	}

	private void initLanguages() {
		try {
			ContentService contentService = helper.getBean(ContentService.class);

			Language l = contentService.getLanguage("en");
			if (l == null) {
				l = new Language();
				l.setIso("en");
				l.setName("English");
				contentService.addLanguage(l);
			}
			l = contentService.getLanguage("ru");
			if (l == null) {
				l = new Language();
				l.setIso("ru");
				l.setName("Russian");
				contentService.addLanguage(l);
			}
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	private void loadApplicationResources() {
		ContentService contentService = helper.getBean(ContentService.class);
		
		List<Language> languages = contentService.listLanguages();
		for (Language language : languages) {
			ResourceBundle bundle = ResourceBundle.getBundle(Resources.APPLICATION_RESOURCE_BUNDLE_BASE, new Locale(language.getIso()), new UTF8Control());
			for(String key: bundle.keySet()) {
				AppResource appResource = contentService.getAppResource(key);
				if (appResource == null) {
					appResource = new AppResource(key);
				}
				if (!appResource.containsLanguage(language)) {
					String val = bundle.getString(key);
					appResource.getLangs().add(new AppValue(language, val));
					try {
						contentService.saveAppResource(appResource);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}

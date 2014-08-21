package diploma.webcad.core.init;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import diploma.webcad.core.model.AppConstant;
import diploma.webcad.core.model.AppConstantType;
import diploma.webcad.core.model.Language;
import diploma.webcad.core.service.ContentService;
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
		
		log.info("initSystemAccount...");
		createSystemAccount();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String marker = "installed: " + format.format(new Date());
		log.info("INSTALLATION FINISH: " + marker);
		
		AppConstant constant = new AppConstant("installed", marker, "Installation flag", 
				AppConstantType.SYSTEM);
		helper.getBean(ContentService.class).saveApplicationConstant(constant);
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

}

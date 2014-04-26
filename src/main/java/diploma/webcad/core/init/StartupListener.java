package diploma.webcad.core.init;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import diploma.webcad.common.content.Resources;
import diploma.webcad.common.content.UTF8Control;
import diploma.webcad.core.dao.ApplicationResourceDao;
import diploma.webcad.core.dao.LanguageDao;
import diploma.webcad.core.dao.TemplateDao;
import diploma.webcad.core.data.appconstants.Constants;
import diploma.webcad.core.data.templates.XmlLocale;
import diploma.webcad.core.data.templates.XmlTemplate;
import diploma.webcad.core.data.templates.XmlTemplates;
import diploma.webcad.core.model.ApplicationResource;
import diploma.webcad.core.model.ApplicationResourceValue;
import diploma.webcad.core.model.Language;
import diploma.webcad.core.model.Template;
import diploma.webcad.core.service.SystemService;

public class StartupListener implements ServletContextListener {

	private static final String SESSION_FACTORY_BEAN = "sessionFactory";
	private static final Logger log = Logger.getLogger(StartupListener.class);
	private ServletContext servletContext;

	public void contextInitialized(ServletContextEvent sce) {

		servletContext = sce.getServletContext();
		SpringContextHelper helper = new SpringContextHelper(
				sce.getServletContext());
		SessionFactory sessionFactory = (SessionFactory) helper
				.getBean(SESSION_FACTORY_BEAN);
		Session session = null;
		boolean participate = false;
		Transaction transaction = null;
		if (TransactionSynchronizationManager.hasResource(sessionFactory)) {
			// do not modify the Session: just set the participate flag
			participate = true;
		} else {
			if (log.isDebugEnabled()) {
				log.debug("Opening temporary Hibernate session in StartupListener");
			}
			session = sessionFactory.openSession(); // SessionFactoryUtils.getSession(sessionFactory,
													// true);
			transaction = session.beginTransaction();
			TransactionSynchronizationManager.bindResource(sessionFactory,
					new SessionHolder(session));
		}

		loadConstants(helper);

		if (!installed(helper)) {
			log.info("INSTALLATION REQUIRED. START.");
			Installer installer = new Installer(helper, servletContext);
			installer.install();
		}

		long currentTimeMillis = System.currentTimeMillis();
		loadApplicationResources(helper);
		log.info("  --> loadApplicationResources done. "
				+ (System.currentTimeMillis() - currentTimeMillis));
		currentTimeMillis = System.currentTimeMillis();
		//loadTemplates(helper);
		log.info("  --> loadMailTemplates done. "
				+ (System.currentTimeMillis() - currentTimeMillis));
		log.info("--> Startup initialization done.");

		// closing session
		if (!participate) {
			TransactionSynchronizationManager.unbindResource(sessionFactory);
			if (log.isDebugEnabled()) {
				log.debug("Closing temporary Hibernate session in StartupListener");
			}
			transaction.commit();
			session.close();
			// SessionFactoryUtils.releaseSession(session, sessionFactory);
		}
	}

	private boolean installed(SpringContextHelper helper) {
		String installed = helper.getBean(SystemService.class)
				.getConstantValue("installed");
		return installed.startsWith("installed");
	}

	private void loadApplicationResources(SpringContextHelper helper) {
		LanguageDao languageDao = (LanguageDao) helper.getBean("languageDao");
		ApplicationResourceDao applicationResourceDao = (ApplicationResourceDao) helper
				.getBean("applicationResourceDao");
		List<Language> languages = languageDao.list();
		for (Language language : languages) {
			ResourceBundle bundle = ResourceBundle.getBundle(
					Resources.APPLICATION_RESOURCE_BUNDLE_BASE, new Locale(
							language.getIso()), new UTF8Control());
			for (String key : bundle.keySet()) {
				ApplicationResource appResource = applicationResourceDao
						.read(key);
				if (appResource == null) {
					appResource = new ApplicationResource(key);
				}
				if (!appResource.containsLanguage(language)) {
					String val = bundle.getString(key);
					appResource.getLangs().add(
							new ApplicationResourceValue(language, val));
					try {
						applicationResourceDao.saveOrUpdate(appResource);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void loadConstants(SpringContextHelper helper) {
		try {
			final JAXBContext jaxbContext = JAXBContext
					.newInstance(Constants.class);
			final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			servletContext.getResourceAsStream("constants.xml");
			final InputStream is = servletContext
					.getResourceAsStream("/WEB-INF/classes/constants.xml");
			final Constants constants = (Constants) unmarshaller.unmarshal(is);
			getSystemManager(helper).readConstants(constants);
		} catch (JAXBException e) {
			log.error(e);
		}
	}

	private void loadTemplates(SpringContextHelper helper) {
		String[] templateFiles = {
				"/WEB-INF/classes/templates/mail_templates.xml", 
				"/WEB-INF/classes/templates/microblog_templates.xml",
				"/WEB-INF/classes/templates/notification_templates.xml",
				"/WEB-INF/classes/templates/message_templates.xml",
				"/WEB-INF/classes/templates/wall_post_templates.xml"
				};
		JAXBContext jaxbContext;
		
		try {
			jaxbContext = JAXBContext.newInstance(XmlTemplates.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			
			for (String fileName : templateFiles) {
				InputStream is = servletContext.getResourceAsStream(fileName);
				XmlTemplates items = (XmlTemplates) unmarshaller.unmarshal(is);
				TemplateDao templateDao = helper.getBean(TemplateDao.class);
				List<Template> existTemplates = templateDao.list();
				LanguageDao langDao = helper.getBean(LanguageDao.class);
				
				for (XmlTemplate t : items.getItems()) {
					Template template = null;
					for (Template existЕemplate : existTemplates) {
						if (existЕemplate.getId().equals(t.getName())) {
							template = existЕemplate;
							break;
						}
					}
					
					if (template == null) {
						template = new Template();
					}
					
					template.setId(t.getName());
					
					for (XmlLocale locale : t.getLocales()) {
						
						Language lang = langDao.read(locale.getLanguage());
						
						if (lang != null) {
							Map<Language, String> titles = template.getTitles();
							if (titles == null) {
								titles = new HashMap<Language, String>();
							}
							Map<Language, String> bodies = template.getBodies();
							if (bodies == null) {
								bodies = new HashMap<Language, String>();
							}
							titles.put(lang, locale.getTitle());
							bodies.put(lang, locale.getBody());
							template.setTitles(titles);
							template.setBodies(bodies);
							templateDao.saveOrUpdate(template);
						} else {
							log.error("No language with id \"" + locale.getLanguage() + "\" in system");
						}
					}
				}	
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private SystemService getSystemManager(SpringContextHelper helper) {
		return helper.getBean(SystemService.class);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}

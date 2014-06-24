package diploma.webcad.core.init;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import diploma.webcad.common.content.Resources;
import diploma.webcad.common.content.UTF8Control;
import diploma.webcad.core.dao.AppResourceDao;
import diploma.webcad.core.dao.FileResourceDao;
import diploma.webcad.core.dao.LanguageDao;
import diploma.webcad.core.dao.TemplateDao;
import diploma.webcad.core.data.appconstants.Constants;
import diploma.webcad.core.data.templates.XmlLocale;
import diploma.webcad.core.data.templates.XmlTemplate;
import diploma.webcad.core.data.templates.XmlTemplates;
import diploma.webcad.core.model.Language;
import diploma.webcad.core.model.resource.AppResource;
import diploma.webcad.core.model.resource.AppValue;
import diploma.webcad.core.model.template.Template;
import diploma.webcad.core.service.SystemService;

public class StartupListener implements ServletContextListener {
	
	private static Logger log = LoggerFactory.getLogger(StartupListener.class);

	private ServletContext servletContext;

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		servletContext = servletContextEvent.getServletContext();
		
		final SpringContext helper = new SpringContext(servletContextEvent.getServletContext());
		TransactionTemplate transactionTemplate = helper.getBean(TransactionTemplate.class);

		transactionTemplate.execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus status) {
				try {
					loadConstants(helper);
					
					createFileResourceFolders(helper);
			
					if (!installed(helper)) {
						log.info("INSTALLATION REQUIRED. START.");
						Installer installer = new Installer(helper, servletContext);
						installer.install();
					}
			
					long currentTimeMillis = System.currentTimeMillis();
					loadApplicationResources(helper);
					log.info("  --> loadApplicationResources done. {}", (System.currentTimeMillis() - currentTimeMillis));
					currentTimeMillis = System.currentTimeMillis();
					//loadTemplates(helper);
					log.info("  --> loadMailTemplates done. {}", (System.currentTimeMillis() - currentTimeMillis));
					log.info("--> Startup initialization done.");
					
				} catch (RuntimeException e) {
					status.setRollbackOnly();
					throw e;
				} catch (IOException e) {
					status.setRollbackOnly();
					throw new RuntimeException("");
				}
				return null;
			}
		});

	}

	private boolean installed(SpringContext helper) {
		String installed = helper.getBean(SystemService.class).getConstantValue("installed");
		return installed.startsWith("installed");
	}
	
	private void createFileResourceFolders (SpringContext helper) throws IOException {
		PropertiesFactoryBean propertiesFactory = helper.getBean(PropertiesFactoryBean.class);
		Properties properties = propertiesFactory.getObject();
		
		String appServPath = properties.getProperty("fileresource.placement.app_server.path");
		Files.createDirectories(Paths.get(appServPath));
	}

	private void loadApplicationResources(SpringContext helper) {
		LanguageDao languageDao = (LanguageDao) helper.getBean(LanguageDao.class);
		AppResourceDao applicationResourceDao = helper.getBean(AppResourceDao.class);
		List<Language> languages = languageDao.list();
		for (Language language : languages) {
			String iso = language.getIso();
			UTF8Control utf8Control = new UTF8Control();
			Locale locale = new Locale(iso);
			ResourceBundle bundle = ResourceBundle.getBundle
					(Resources.APPLICATION_RESOURCE_BUNDLE_BASE, locale, utf8Control);
			for (String key : bundle.keySet()) {
				AppResource appResource = applicationResourceDao.read(key);
				if (appResource == null) {
					appResource = new AppResource(key);
				}
				if (!appResource.containsLanguage(language)) {
					String val = bundle.getString(key);
					AppValue appValue = new AppValue(language, val);
					appResource.getLangs().add(appValue);
					try {
						applicationResourceDao.saveOrUpdate(appResource);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void loadConstants(SpringContext helper) {
		try {
			final JAXBContext jaxbContext = JAXBContext.newInstance(Constants.class);
			final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			servletContext.getResourceAsStream("constants.xml");
			final InputStream is = servletContext.getResourceAsStream("/WEB-INF/classes/constants.xml");
			final Constants constants = (Constants) unmarshaller.unmarshal(is);
			getSystemManager(helper).readConstants(constants);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	private void loadTemplates(SpringContext helper) {
		String[] templateFiles = {
				"/WEB-INF/classes/templates/templates1.xml", 
				"/WEB-INF/classes/templates/templates2.xml"
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

	private SystemService getSystemManager(SpringContext helper) {
		return helper.getBean(SystemService.class);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}

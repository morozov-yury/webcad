package diploma.webcad.core.init;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
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
import diploma.webcad.core.dao.LanguageDao;
import diploma.webcad.core.dao.TemplateDao;
import diploma.webcad.core.data.appconstants.Constants;
import diploma.webcad.core.data.templates.XmlLocale;
import diploma.webcad.core.data.templates.XmlTemplate;
import diploma.webcad.core.data.templates.XmlTemplates;
import diploma.webcad.core.model.Language;
import diploma.webcad.core.model.Template;
import diploma.webcad.core.model.modelling.Device;
import diploma.webcad.core.model.modelling.DeviceFamilies;
import diploma.webcad.core.model.modelling.DeviceFamily;
import diploma.webcad.core.model.resource.AppResource;
import diploma.webcad.core.model.resource.AppValue;
import diploma.webcad.core.service.SystemService;
import diploma.webcad.core.service.XilinxService;

public class StartupListener implements ServletContextListener {
	
	private static Logger log = LoggerFactory.getLogger(StartupListener.class);
	
	private static String familyDevicesFilePath = "/WEB-INF/classes/families_devices.xml";
	
	private static String tamplatesFilePath = "/WEB-INF/classes/templates/templates.xml";
	
	private static String constantFilePath = "/WEB-INF/classes/constants.xml";

	private ServletContext servletContext;

	private SpringContext helper;

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		servletContext = servletContextEvent.getServletContext();
		
		helper = new SpringContext(servletContextEvent.getServletContext());
		TransactionTemplate transactionTemplate = helper.getBean(TransactionTemplate.class);

		transactionTemplate.execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus status) {
				try {
					loadConstants();
					loadFamilyDevices();
					createFileResourceFolders();
			
					if (!installed()) {
						log.info("INSTALLATION REQUIRED. START.");
						Installer installer = new Installer(helper, servletContext);
						installer.install();
					}
			
					long currentTimeMillis = System.currentTimeMillis();
					loadApplicationResources();
					log.info("  --> loadApplicationResources done. {}", (System.currentTimeMillis() - currentTimeMillis));
					currentTimeMillis = System.currentTimeMillis();
					//loadTemplates(helper);
					log.info("  --> loadMailTemplates done. {}", (System.currentTimeMillis() - currentTimeMillis));
					log.info("--> Startup initialization done.");
					
				} catch (Exception e) {
					log.info("{}", e);
					status.setRollbackOnly();
					throw new RuntimeException(e.getMessage());
				} 
				return null;
			}
		});

	}

	private boolean installed() {
		String installed = helper.getBean(SystemService.class).getConstantValue("installed");
		return installed.startsWith("installed");
	}
	
	private void createFileResourceFolders () throws IOException {
		PropertiesFactoryBean propertiesFactory = helper.getBean(PropertiesFactoryBean.class);
		Properties properties = propertiesFactory.getObject();
		
		String appServPath = properties.getProperty("fileresource.placement.app_server.path");
		Files.createDirectories(Paths.get(appServPath));
	}

	private void loadApplicationResources() {
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

	private void loadConstants() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Constants.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			InputStream is = servletContext.getResourceAsStream(constantFilePath);
			Constants constants = (Constants) unmarshaller.unmarshal(is);
			helper.getBean(SystemService.class).readConstants(constants);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	private void loadFamilyDevices () {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(DeviceFamilies.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			InputStream is = servletContext.getResourceAsStream(familyDevicesFilePath);
			DeviceFamilies xmlDeviceFamilies = (DeviceFamilies) unmarshaller.unmarshal(is);
			if (xmlDeviceFamilies != null) {
				XilinxService xilinxService = helper.getBean(XilinxService.class);
				for (DeviceFamily xmlDeviceFamily : xmlDeviceFamilies.getDeviceFamilies()) {
					log.info("{}", xmlDeviceFamily.getName());
					DeviceFamily persDeviceFamily = xilinxService.getDeviceFamily(
							xmlDeviceFamily.getName());
					if (persDeviceFamily == null) {
						persDeviceFamily = new DeviceFamily();
						persDeviceFamily.setName(xmlDeviceFamily.getName());
						persDeviceFamily.setDescription(xmlDeviceFamily.getDescription());
						xilinxService.saveDeviceFamily(persDeviceFamily);
					}
					for (Device xmlDevice : xmlDeviceFamily.getDevices()) {
						log.info("--{}", xmlDevice.getName());
						if (!xilinxService.isDeviceExist(xmlDevice.getName())) {
							xmlDevice.setDeviceFamily(persDeviceFamily);
							xilinxService.saveDevice(xmlDevice);
							persDeviceFamily.getDevices().add(xmlDevice);
						}
					}
					xilinxService.saveDeviceFamily(persDeviceFamily);
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	private void loadTemplates() {
		String[] templateFiles = {
				tamplatesFilePath
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
							log.error("No language with id \"" + locale.getLanguage() 
									+ "\" in system");
						}
					}
				}	
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}

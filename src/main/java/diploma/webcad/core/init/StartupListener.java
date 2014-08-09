package diploma.webcad.core.init;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

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

import diploma.webcad.core.dao.TemplateDao;
import diploma.webcad.core.data.appconstants.Constants;
import diploma.webcad.core.model.resource.Template;
import diploma.webcad.core.model.resource.Templates;
import diploma.webcad.core.model.simulation.Device;
import diploma.webcad.core.model.simulation.DeviceFamilies;
import diploma.webcad.core.model.simulation.DeviceFamily;
import diploma.webcad.core.service.SystemService;
import diploma.webcad.core.service.XilinxService;

public class StartupListener implements ServletContextListener {
	
	private static Logger log = LoggerFactory.getLogger(StartupListener.class);
	
	private static String familyDevicesFilePath = "/WEB-INF/classes/families_devices.xml";
	
	private static String tamplatesFilePath = "/WEB-INF/classes/templates/templates.xml";
	
	private static String constantFilePath = "/WEB-INF/classes/constants.xml";

	private ServletContext servletContext;

	private SpringContext context;

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		servletContext = servletContextEvent.getServletContext();
		
		context = new SpringContext(servletContextEvent.getServletContext());
		TransactionTemplate transactionTemplate = context.getBean(TransactionTemplate.class);

		transactionTemplate.execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus status) {
				try {
					loadConstants();
					loadFamilyDevices();
					loadTemplates();
					createFileResourceFolders();
			
					if (!installed()) {
						log.info("INSTALLATION REQUIRED. START.");
						Installer installer = new Installer(context, servletContext);
						installer.install();
					}

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
		String installed = context.getBean(SystemService.class).getConstantValue("installed");
		return installed.startsWith("installed");
	}
	
	private void createFileResourceFolders () throws IOException {
		PropertiesFactoryBean propertiesFactory = context.getBean(PropertiesFactoryBean.class);
		Properties properties = propertiesFactory.getObject();
		
		String appServPath = properties.getProperty("fileresource.placement.app_server.path");
		Files.createDirectories(Paths.get(appServPath));
	}

	private void loadConstants() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Constants.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			InputStream is = servletContext.getResourceAsStream(constantFilePath);
			Constants constants = (Constants) unmarshaller.unmarshal(is);
			context.getBean(SystemService.class).readConstants(constants);
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
				XilinxService xilinxService = context.getBean(XilinxService.class);
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
		TemplateDao templateDao = context.getBean(TemplateDao.class);
		String[] templateFiles = {
				tamplatesFilePath
				};
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Templates.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			for (String fileName : templateFiles) {
				InputStream is = servletContext.getResourceAsStream(fileName);
				Templates templates = (Templates) unmarshaller.unmarshal(is);
				if (templates != null) {
					for (Template template : templates.getTemplates()) {
						if (!templateDao.isExist(template.getId())) {
							templateDao.save(template);
							log.info("Loaded template '{}'", template.getId());
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

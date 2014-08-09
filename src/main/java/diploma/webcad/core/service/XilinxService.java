package diploma.webcad.core.service;

import java.util.List;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import diploma.webcad.core.dao.BatchSimulationDao;
import diploma.webcad.core.dao.DeviceDao;
import diploma.webcad.core.dao.DeviceFamilyDao;
import diploma.webcad.core.dao.XilinxProjectDao;
import diploma.webcad.core.model.User;
import diploma.webcad.core.model.resource.FSResource;
import diploma.webcad.core.model.simulation.BatchSimulation;
import diploma.webcad.core.model.simulation.BatchSimulationStatus;
import diploma.webcad.core.model.simulation.Device;
import diploma.webcad.core.model.simulation.DeviceFamily;
import diploma.webcad.core.model.simulation.GenaLaunch;
import diploma.webcad.core.model.simulation.XilinxProject;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class XilinxService {
	
	private static Logger log = LoggerFactory.getLogger(XilinxService.class);

	@Autowired
	private DeviceFamilyDao deviceFamilyDao;
	
	@Autowired
	private DeviceDao deviceDao;
	
	@Autowired 
	private XilinxProjectDao xilinxProjectDao;
	
	@Autowired
	private BatchSimulationDao batchSimulationDao;
	
	@Autowired
	private FSResourceService fsResourceService;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveDeviceFamily (DeviceFamily deviceFamily) {
		deviceFamilyDao.saveOrUpdate(deviceFamily);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveDevice (Device device) {
		deviceDao.saveOrUpdate(device);
	}
	
	public DeviceFamily getDeviceFamily(String name) {
		return this.deviceFamilyDao.getDeviceFamily(name); 
	}
	
	public List<DeviceFamily> listDeviceFamilies () {
		return deviceFamilyDao.list();
	}
	
	public List<Device> listDevices () {
		return deviceDao.list();
	}
	
	public List<Device> listDevises (List<String> names) {
		return deviceDao.listDevises(names);
	}
	
	public Device getDevice (String name) {
		return deviceDao.getByName(name);
	}
	
	public Boolean isDeviceExist (String deviceName) {
		return deviceDao.list("name", deviceName).size() != 0;
	}
	
	public List<BatchSimulation> listBatchSimulation(User user) {
		return batchSimulationDao.list("user", user);
	}
	
	public BatchSimulation getBatchSimulation(Long id, boolean lazy) {
		BatchSimulation batchSimulation = batchSimulationDao.get(id);
		if (!lazy) {
			Hibernate.initialize(batchSimulation.getDevices());
			Hibernate.initialize(batchSimulation.getProjects());
		}
		return batchSimulation;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public BatchSimulation createBatchSimulation (GenaLaunch genaLaunch, List<Device> devices, 
			User user) {
		log.info("Creation BatchSimulation start");
		log.info("GenaLaunch id = {}",  genaLaunch.getId());
		log.info("Devices count = {}",  devices.size());
		BatchSimulation batchSimulation = new BatchSimulation();
		batchSimulation.setDevices(devices);
		batchSimulation.setUser(user);
		
		for (Device device : devices) {
			XilinxProject xilinxProjects = createXilinxProject(genaLaunch, device);
			batchSimulation.getProjects().add(xilinxProjects);
		}
		batchSimulation.setStatus(BatchSimulationStatus.CREATED);
		batchSimulationDao.save(batchSimulation);
		log.info("Creation BatchSimulation end");
		return batchSimulation;
	}
	
	private XilinxProject createXilinxProject (GenaLaunch genaLaunch, Device device) {
		log.info("Creation XilinxProject, device = {}", device.getName());
		XilinxProject xilinxProject = new XilinxProject();
		xilinxProject.setDevice(device);		
		xilinxProject.setGenaLaunch(genaLaunch);
		xilinxProjectDao.save(xilinxProject);
		return xilinxProject;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void runBatchSimulation (BatchSimulation simulation) {
		if (simulation.getStatus() != BatchSimulationStatus.CREATED) {
			throw new IllegalStateException("You can run only simulation with status CREATED");
		}
		simulation.setStatus(BatchSimulationStatus.PREPEARING);
		simulation = batchSimulationDao.merge(simulation);
		
		List<XilinxProject> projects = simulation.getProjects();
		for (XilinxProject project : projects) {
			makeTcl(project);
		}
		
	}
	
	private void makeTcl (XilinxProject project) {
		log.debug("Start making TCL file fo project[{}], status[{}]", 
				project.getId(), project.getStatus().toString());
		
		FSResource folder = project.getFolder();
		if (folder == null) {
			throw new IllegalStateException("Project's folder can't be null");
		}
		String folderPath = fsResourceService.getFSResourcePath(folder);
		log.debug("In folder '{}'", folderPath);
		
		Device device = project.getDevice();
		log.debug("For device '{}'", device.getName());
		
		
		
	}

}

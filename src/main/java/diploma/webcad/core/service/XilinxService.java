package diploma.webcad.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import diploma.webcad.core.dao.DeviceDao;
import diploma.webcad.core.dao.DeviceFamilyDao;
import diploma.webcad.core.model.modelling.Device;
import diploma.webcad.core.model.modelling.DeviceFamily;

@Service
public class XilinxService {

	@Autowired
	private DeviceFamilyDao deviceFamilyDao;
	
	@Autowired
	private DeviceDao deviceDao;
	
	public void saveDeviceFamily (DeviceFamily deviceFamily) {
		deviceFamilyDao.saveOrUpdate(deviceFamily);
	}
	
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
	
	public Boolean isDeviceExist (String deviceName) {
		return deviceDao.list("name", deviceName).size() != 0;
	}
	
}

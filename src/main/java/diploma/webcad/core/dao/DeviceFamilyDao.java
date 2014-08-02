package diploma.webcad.core.dao;

import diploma.webcad.core.model.simulation.DeviceFamily;

public interface DeviceFamilyDao extends BaseDao<DeviceFamily, Long> {

	public DeviceFamily getDeviceFamily(String name);

	public Boolean isDeviceExist(DeviceFamily deviceFamily, String deviceName);

}

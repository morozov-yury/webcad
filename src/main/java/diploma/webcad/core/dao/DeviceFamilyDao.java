package diploma.webcad.core.dao;

import diploma.webcad.core.model.modelling.DeviceFamily;

public interface DeviceFamilyDao extends BaseDao<DeviceFamily, Long> {

	public DeviceFamily getDeviceFamily(String name);

	public Boolean isDeviceExist(DeviceFamily deviceFamily, String deviceName);

}

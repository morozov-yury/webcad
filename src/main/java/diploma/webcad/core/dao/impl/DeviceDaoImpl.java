package diploma.webcad.core.dao.impl;

import org.springframework.stereotype.Repository;

import diploma.webcad.core.dao.DeviceDao;
import diploma.webcad.core.model.modelling.Device;

@Repository
public class DeviceDaoImpl extends BaseDaoImpl<Device, Long> implements
		DeviceDao {

	public DeviceDaoImpl() {
		super(Device.class);
	}

}

package diploma.webcad.core.dao;

import java.util.List;

import diploma.webcad.core.model.simulation.Device;

public interface DeviceDao extends BaseDao<Device, Long> {

	public Device getByName(String name);

	public List<Device> listDevises(List<String> names);

}

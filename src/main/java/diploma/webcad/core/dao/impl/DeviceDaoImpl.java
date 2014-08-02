package diploma.webcad.core.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import diploma.webcad.core.dao.DeviceDao;
import diploma.webcad.core.model.simulation.Device;

@Repository
@SuppressWarnings("unchecked")
public class DeviceDaoImpl extends BaseDaoImpl<Device, Long> implements
		DeviceDao {

	public DeviceDaoImpl() {
		super(Device.class);
	}

	@Override
	public Device getByName(String name) {
		return (Device) makeCriteria()
				.add(Restrictions.eq("name", name))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.uniqueResult();
	}

	@Override
	public List<Device> listDevises(List<String> names) {
		return makeCriteria()
				.add(Restrictions.in("name", names))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
	}

}

package diploma.webcad.core.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import diploma.webcad.core.dao.DeviceFamilyDao;
import diploma.webcad.core.model.simulation.DeviceFamily;

@Repository
public class DeviceFamilyDaoImpl extends BaseDaoImpl<DeviceFamily, Long>
		implements DeviceFamilyDao {

	public DeviceFamilyDaoImpl() {
		super(DeviceFamily.class);
	}

	@Override
	public DeviceFamily getDeviceFamily(String name) {
		return (DeviceFamily) makeCriteria().add(Restrictions.eq("name", name))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.uniqueResult();
	}

	@Override
	public Boolean isDeviceExist(DeviceFamily deviceFamily, String deviceName) {
		return (makeCriteria()
				.add(Restrictions.idEq(deviceFamily.getId()))
				.createAlias("devices", "device")
				.add(Restrictions.eq("name", deviceName))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.uniqueResult() != null);
	}

}

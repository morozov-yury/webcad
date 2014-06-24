package diploma.webcad.core.dao.impl;

import org.springframework.stereotype.Repository;

import diploma.webcad.core.dao.GenaLaunchDao;
import diploma.webcad.core.model.gena.GenaLaunch;

@Repository
public class GenaLaunchDaoImpl extends BaseDaoImpl<GenaLaunch, Long> implements
		GenaLaunchDao {

	public GenaLaunchDaoImpl() {
		super(GenaLaunch.class);
	}

}

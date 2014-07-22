package diploma.webcad.core.dao;

import java.util.List;

import diploma.webcad.core.model.User;
import diploma.webcad.core.model.modelling.GenaLaunch;

public interface GenaLaunchDao extends BaseDao<GenaLaunch, Long> {
	
	public List<GenaLaunch> listLastUserLauches (User user, int count);

}

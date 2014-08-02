package diploma.webcad.core.dao;

import java.util.List;

import diploma.webcad.core.model.User;
import diploma.webcad.core.model.simulation.GenaLaunch;
import diploma.webcad.core.model.simulation.GenaResultStatus;

public interface GenaLaunchDao extends BaseDao<GenaLaunch, Long> {
	
	public List<GenaLaunch> listLastUserLauches (User user, int count);

	public List<GenaLaunch> listLastUserLauches(User user, GenaResultStatus status, int count);

}

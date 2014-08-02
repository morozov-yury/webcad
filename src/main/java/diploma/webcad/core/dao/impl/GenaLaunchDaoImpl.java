package diploma.webcad.core.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import diploma.webcad.core.dao.GenaLaunchDao;
import diploma.webcad.core.model.User;
import diploma.webcad.core.model.simulation.GenaLaunch;
import diploma.webcad.core.model.simulation.GenaResultStatus;

@Repository
@SuppressWarnings("unchecked")
public class GenaLaunchDaoImpl extends BaseDaoImpl<GenaLaunch, Long> implements GenaLaunchDao {

	public GenaLaunchDaoImpl() {
		super(GenaLaunch.class);
	}

	@Override
	public List<GenaLaunch> listLastUserLauches(User user, int count) {
		return makeCriteria()
				.add(Restrictions.eq("user", user))
				.addOrder(Order.desc("creationDate"))
				.setMaxResults(count)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	@Override
	public List<GenaLaunch> listLastUserLauches(User user, GenaResultStatus status, int count) {
		return makeCriteria()
				.add(Restrictions.eq("user", user))
				.add(Restrictions.eq("status", status))
				.addOrder(Order.desc("creationDate"))
				.setMaxResults(count)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

}

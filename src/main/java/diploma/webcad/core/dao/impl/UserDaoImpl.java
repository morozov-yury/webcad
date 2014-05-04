package diploma.webcad.core.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import diploma.webcad.core.dao.UserDao;
import diploma.webcad.core.model.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User, Long> implements UserDao {
	
	public UserDaoImpl() {
		super(User.class);
	}

	public User getByEmail(String email) {
		return (User) getSession().createCriteria(type)
				.add(Restrictions.eq("email", email)).uniqueResult();
	}

	@Override
	public boolean isUserExist(String login) {
		return (Long) makeCriteria().add(Restrictions.eq("email", login))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.setProjection(Projections.rowCount()).uniqueResult() == 1;
	}

	@Override
	public Long getUserCount() {
		return (Long) makeCriteria()
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.setProjection(Projections.rowCount())
				.uniqueResult();
	}
}
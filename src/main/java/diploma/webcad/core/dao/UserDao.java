package diploma.webcad.core.dao;

import diploma.webcad.core.model.User;

public interface UserDao extends BaseDao<User, Long> {
	public User getByEmail(String email);

	public boolean isUserExist(String login);

	public Long getUserCount();
}

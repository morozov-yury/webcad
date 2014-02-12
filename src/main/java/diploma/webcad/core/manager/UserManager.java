package diploma.webcad.core.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import diploma.webcad.common.security.MD5Helper;
import diploma.webcad.core.dao.UserDao;
import diploma.webcad.core.exception.UserAlreadyExistException;
import diploma.webcad.core.exception.UserRetrievingException;
import diploma.webcad.core.model.Language;
import diploma.webcad.core.model.User;

@Service
public class UserManager {
	
	@Autowired
	private UserDao userDao;
	
	public List<User> list() {
		return userDao.list();
	}
	
	public User retrieveByEmail(String email) throws UserRetrievingException {
		User u = null;
		try {
			u = userDao.getByEmail(email);
		} catch (Exception e) {
			throw new UserRetrievingException();
		}
		if (u!=null)
			return u;
		else
			throw new UserRetrievingException();
	}

	public boolean isUserExist(String login) {
		return userDao.isUserExist(login);
	}

	public User getUser(String login) throws UserRetrievingException {
		return retrieveByEmail(login);
	}
	
	public Long getUserCount() {
		return userDao.getUserCount();
	}

	public void createUser(String login, String password, Language language) throws UserAlreadyExistException {
		if(isUserExist(login)) throw new UserAlreadyExistException();
		User user = new User(login, MD5Helper.getHash(password));
		user.setLanguage(language);
		userDao.saveOrUpdate(user);
	}

}

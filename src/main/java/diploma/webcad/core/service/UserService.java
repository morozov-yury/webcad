package diploma.webcad.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import diploma.webcad.common.security.MD5Helper;
import diploma.webcad.core.dao.UserDao;
import diploma.webcad.core.exception.UserAlreadyExistException;
import diploma.webcad.core.exception.UserRetrievingException;
import diploma.webcad.core.model.Language;
import diploma.webcad.core.model.User;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Transactional
	public List<User> list() {
		return userDao.list();
	}
	
	@Transactional
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

	@Transactional
	public boolean isUserExist(String login) {
		return userDao.isUserExist(login);
	}

	@Transactional
	public User getUser(String login) throws UserRetrievingException {
		return retrieveByEmail(login);
	}
	
	@Transactional
	public Long getUserCount() {
		return userDao.getUserCount();
	}

	@Transactional
	public void createUser(String login, String password, Language language) throws UserAlreadyExistException {
		if(isUserExist(login)) throw new UserAlreadyExistException();
		User user = new User(login, MD5Helper.getHash(password));
		user.setLanguage(language);
		userDao.saveOrUpdate(user);
	}

}

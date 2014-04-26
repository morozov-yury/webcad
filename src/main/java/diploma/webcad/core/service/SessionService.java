package diploma.webcad.core.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@Scope("singleton")
public class SessionService {
	
	@Autowired
	private SessionFactory sessionFactory;

	public SessionService() {
		
	}
	
	public Session openSession() {
		Session session = null;
		if (!TransactionSynchronizationManager.hasResource(sessionFactory)) {
			session = sessionFactory.openSession();
			TransactionSynchronizationManager.bindResource(sessionFactory,
					new SessionHolder(session));
		}
		return session;
	}
	
	public void closeSession(Session session) {
		if (session != null) {
			TransactionSynchronizationManager.unbindResource(sessionFactory);
			SessionFactoryUtils.closeSession(session);
		}
	}

}

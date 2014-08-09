package diploma.webcad.core.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import diploma.webcad.core.dao.BaseDao;

@Repository
@SuppressWarnings("unchecked")
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public abstract class BaseDaoImpl <T, PK extends Serializable> implements BaseDao<T, PK> {
	protected Class<? extends Serializable> type;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public BaseDaoImpl(Class<? extends Serializable> type) {
        this.type = type;
    }
	
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }	
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	protected Session openSession() {
		return sessionFactory.openSession();
	}
	
	@Override
	public T get(PK id) {
		return (T) getSession().get(type, id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(T persistentObject) {
		getSession().delete(persistentObject);
	}

	@Override
	public List<T> list() {
		return getSession().createCriteria(type).list();
	}

	@Override
	public List<T> list(String property, Object value) {
		return getSession().createCriteria(type).add(Restrictions.eq(property, value)).list();
	}

	@Override
	public List<T> listSortedAsc(String property) {
		return getSession().createCriteria(type).addOrder(Order.asc(property)).list();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public T merge(T entity) {
		return (T) getSession().merge(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void refresh(T entity) {
		getSession().refresh(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
	}
	
	@Override
	public void update(T entity) {
		getSession().update(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public PK save(T entity) {
		return (PK) getSession().save(entity);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void batchSaveOrUpdate(Collection<T> entities) {
		Iterator<T> iterator = entities.iterator();
		while(iterator.hasNext()) {
			getSession().saveOrUpdate(iterator.next());
		}
	}
	
	protected Criteria makeCriteria() {
		return getSession().createCriteria(type);
	}
	
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}

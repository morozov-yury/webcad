package diploma.webcad.core.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import diploma.webcad.core.dao.BaseDao;

@Repository
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
	
	@Transactional
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	protected Session openSession() {
		return sessionFactory.openSession();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T read(PK id) {
		try { 
			return (T) getSession().get(type, id); 
		} catch(ObjectNotFoundException e) { 
			return null;
		}
	}

	@Transactional(readOnly = false)
	@Override
	public void delete(T persistentObject) {
		getSession().delete(persistentObject);
		getSession().flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> list() {
		return getSession().createCriteria(type).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> list(String property, Object value) {
		return getSession().createCriteria(type).add(Restrictions.eq(property, value)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> listSortedAsc(String property) {
		return getSession().createCriteria(type).addOrder(Order.asc(property)).list();
	}

	@Transactional(readOnly = false)
	@Override
	public T merge(T entity) {
		getSession().merge(entity);
		getSession().flush();
		return entity;
	}

	@Override
	public void refresh(T entity) {
		getSession().refresh(entity);
	}

	@Transactional(readOnly = false)
	@Override
	public void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
		getSession().flush();
	}
	
	@Transactional(readOnly = false)
	@Override
	public T create(T entity) {
		@SuppressWarnings("unchecked")
		T e = (T) getSession().save(entity);
		getSession().flush();
		return e;
	}
	
	public void batchSaveOrUpdate(Collection<T> entities) {
		int i = 0;
		Iterator<T> iterator = entities.iterator();
		while(iterator.hasNext()) {
			getSession().saveOrUpdate(iterator.next());
			i++;
			if(i % 50 == 0) {
				getSession().flush();
				getSession().clear();
			}
		}
	}
	
	protected Criteria makeCriteria() {
		return getSession().createCriteria(type);
	}
	
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}

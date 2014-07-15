package diploma.webcad.core.dao.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import diploma.webcad.core.dao.AppResourceDao;
import diploma.webcad.core.model.Language;
import diploma.webcad.core.model.resource.AppCategoryPrefix;
import diploma.webcad.core.model.resource.AppResource;

@Repository
public class AppResourceDaoImpl extends
		BaseDaoImpl<AppResource, String> implements AppResourceDao {

	public AppResourceDaoImpl() {
		super(AppResource.class);
	}

	@Override
	public void saveOrUpdate(AppResource entity) {
		if(entity.getId() == null) entity.setId(Long.toString(System.currentTimeMillis()));
		super.saveOrUpdate(entity);
	}
	
	private void addNameFilter(Criteria criteria, List<String> filters, Language lang) {
		Disjunction filterDisjunction = Restrictions.disjunction();
		criteria.createCriteria("langs", "l");
		for (String filter : filters) {
			filterDisjunction.add(Restrictions.and(Restrictions.ilike("l.value", filter + "%"), Restrictions.eq("l.language", lang)));
		}
		criteria.add(filterDisjunction);
	}
	
	private void addPrefixFilter(Criteria criteria, AppCategoryPrefix prefix) {
		criteria.add(Restrictions.like("id", prefix.getPrefix() + "%"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> listIds() {
		return makeCriteria().setProjection(Projections.id()).list();
	}

	@Override
	public String getPrevId(String itemId,
			AppCategoryPrefix prefix, Language language,
			List<String> filters) {
		Criteria criteria = makeCriteria();
		addNameFilter(criteria, filters, language);
		addPrefixFilter(criteria, prefix);
		criteria.add(Restrictions.lt("id", itemId))
		.setMaxResults(1);
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.id());
		projectionList.add(Projections.max("id"));
		criteria.setProjection(projectionList);
		return (String) criteria.uniqueResult();
	}

	@Override
	public String getNextId(String itemId,
			AppCategoryPrefix prefix, Language language,
			List<String> filters) {
		Criteria criteria = makeCriteria();
		addNameFilter(criteria, filters, language);
		addPrefixFilter(criteria, prefix);
		criteria.add(Restrictions.gt("id", itemId))
		.setMaxResults(1);
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.id());
		projectionList.add(Projections.min("id"));
		criteria.setProjection(projectionList);
		return (String) criteria.uniqueResult();
	}

	@Override
	public String getFirstId(AppCategoryPrefix prefix,
			Language language, List<String> filters) {
		Criteria criteria = makeCriteria();
		addNameFilter(criteria, filters, language);
		addPrefixFilter(criteria, prefix);
		criteria.addOrder(Order.asc("id"))
		.setMaxResults(1)
		.setProjection(Projections.id());
		return (String) criteria.uniqueResult();
	}
	
	@Override
	public String getLastId(AppCategoryPrefix prefix,
			Language language, List<String> filters) {
		Criteria criteria = makeCriteria();
		addNameFilter(criteria, filters, language);
		addPrefixFilter(criteria, prefix);
		criteria.addOrder(Order.desc("id"))
		.setMaxResults(1)
		.setProjection(Projections.id());
		return (String) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> listIds(AppCategoryPrefix prefix,
			Language language, List<String> filters, int startIndex,
			int numberOfItems) {
		Criteria criteria = makeCriteria();
		addNameFilter(criteria, filters, language);
		addPrefixFilter(criteria, prefix);
		criteria.setFirstResult(startIndex)
		.setMaxResults(numberOfItems)
		.setProjection(Projections.id());
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<String> listIds(AppCategoryPrefix prefix,
			Language language, List<String> filters) {
		Criteria criteria = makeCriteria();
		addNameFilter(criteria, filters, language);
		addPrefixFilter(criteria, prefix);
		criteria.setProjection(Projections.id());
		return criteria.list();
	}

	@Override
	public Long getCount(AppCategoryPrefix prefix,
			Language language, List<String> filters) {
		Criteria criteria = makeCriteria();
		addNameFilter(criteria, filters, language);
		addPrefixFilter(criteria, prefix);
		return (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
	}

	@Override
	public boolean isExist(String id) {
		return ((Long)makeCriteria().add(Restrictions.eq("id", id)).setProjection(Projections.rowCount()).uniqueResult()) > 0;
	}

}

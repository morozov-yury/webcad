package diploma.webcad.core.dao.impl;

import org.springframework.stereotype.Repository;

import diploma.webcad.core.dao.AppConstantDao;
import diploma.webcad.core.model.AppConstant;

@Repository
public class AppConstantDaoImpl extends BaseDaoImpl<AppConstant, String> implements AppConstantDao {

	public AppConstantDaoImpl() {
		super(AppConstant.class);
	}
}

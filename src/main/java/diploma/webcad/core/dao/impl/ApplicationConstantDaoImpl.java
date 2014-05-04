/**
 * 
 */
package diploma.webcad.core.dao.impl;

import org.springframework.stereotype.Repository;

import diploma.webcad.core.dao.ApplicationConstantDao;
import diploma.webcad.core.model.constant.ApplicationConstant;

@Repository
public class ApplicationConstantDaoImpl extends BaseDaoImpl<ApplicationConstant, String> implements ApplicationConstantDao {

	public ApplicationConstantDaoImpl() {
		super(ApplicationConstant.class);
	}
}

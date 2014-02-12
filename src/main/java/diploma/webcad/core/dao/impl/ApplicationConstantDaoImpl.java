/**
 * 
 */
package diploma.webcad.core.dao.impl;

import org.springframework.stereotype.Component;

import diploma.webcad.core.dao.ApplicationConstantDao;
import diploma.webcad.core.model.ApplicationConstant;

/**
 * @author Arktos
 */

@Component("applicationConstantDao")
public class ApplicationConstantDaoImpl extends BaseDaoImpl<ApplicationConstant, String> implements ApplicationConstantDao {

	public ApplicationConstantDaoImpl() {
		super(ApplicationConstant.class);
	}
}

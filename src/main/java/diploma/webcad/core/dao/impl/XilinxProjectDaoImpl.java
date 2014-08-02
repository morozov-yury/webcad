package diploma.webcad.core.dao.impl;

import diploma.webcad.core.dao.XilinxProjectDao;
import diploma.webcad.core.model.modelling.XilinxProject;

public class XilinxProjectDaoImpl extends BaseDaoImpl<XilinxProject, Long>
		implements XilinxProjectDao {

	public XilinxProjectDaoImpl() {
		super(XilinxProject.class);
	}

}

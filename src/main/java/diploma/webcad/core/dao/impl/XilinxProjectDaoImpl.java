package diploma.webcad.core.dao.impl;

import org.springframework.stereotype.Repository;

import diploma.webcad.core.dao.XilinxProjectDao;
import diploma.webcad.core.model.simulation.XilinxProject;

@Repository
public class XilinxProjectDaoImpl extends BaseDaoImpl<XilinxProject, Long>
		implements XilinxProjectDao {

	public XilinxProjectDaoImpl() {
		super(XilinxProject.class);
	}

}

package diploma.webcad.core.dao.impl;

import org.springframework.stereotype.Repository;

import diploma.webcad.core.dao.FileResourceDao;
import diploma.webcad.core.model.resource.FSResource;

@Repository
public class FileResourceDaoImpl extends
		BaseDaoImpl<FSResource, String> implements FileResourceDao {

	public FileResourceDaoImpl() {
		super(FSResource.class);
	}

}

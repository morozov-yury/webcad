package diploma.webcad.core.dao.impl;

import org.springframework.stereotype.Repository;

import diploma.webcad.core.dao.FileResourceDao;
import diploma.webcad.core.model.resource.FileResource;

@Repository
public class FileResourceDaoImpl extends
		BaseDaoImpl<FileResource, String> implements FileResourceDao {

	public FileResourceDaoImpl() {
		super(FileResource.class);
	}

}

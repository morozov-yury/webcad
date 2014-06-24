package diploma.webcad.core.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import diploma.webcad.core.dao.FileResourceDao;
import diploma.webcad.core.model.User;
import diploma.webcad.core.model.resource.FileResource;
import diploma.webcad.core.model.resource.FileResourcePlacement;

@Service
@Scope("singleton")
public class FileResourceManager {
	
	@Value("${fileresource.placement.app_server.path}")
	private String appServPlacementPath;
	
	@Value("${fileresource.placement.neclus.path}")
	private String neclusPlacementPath;
	
	@Value("${fileresource.placement.type}")
	private String defFilesPlacement;
	
	@Autowired
	private FileResourceDao fileResourceDao;
	
	public FileResource createFileResByContent (User user, byte[] data) {
		FileResource fileResource = new FileResource();
		fileResource.setUser(user);
		fileResource.setPlacement(getDefPlacement());
		fileResourceDao.saveOrUpdate(fileResource);
		
		boolean saveFileData = saveFileData(fileResource, data);
		if (!saveFileData) {
			return null;
		}
		
		return fileResource;
	}
	
	public String getFileResourcePath (FileResource fileResource) {
		Long userId = fileResource.getUser().getId();
		Long fileResourceId = fileResource.getId();
		StringBuilder filePath = new StringBuilder();
		
		switch (fileResource.getPlacement()) {
		case APP_SERVER:
			filePath.append(appServPlacementPath);
			filePath.append("/").append(userId).append("/").append(fileResourceId);
			return filePath.toString();
		case NECLUS:
			filePath.append(neclusPlacementPath);
			filePath.append("/").append(userId).append("/").append(fileResourceId);
			return filePath.toString();
		}
		
		throw new IllegalStateException("FileResourcePlacement " + fileResource.getPlacement() 
				+ " isn't implemented");
	}
	
	private boolean saveFileData (FileResource fileResource, byte[] data) {
		String filePath = getFileResourcePath(fileResource);
		switch (fileResource.getPlacement()) {
		case APP_SERVER:
			return saveFileDataToAppServer(filePath, data);
		case NECLUS:
			throw new IllegalStateException("File saving to neclus isn't implemented");
		}
		return false;
	}
	
	private boolean saveFileDataToAppServer (String path, byte[] data) {
		Path file = Paths.get(path);
		Path folderPath = file.getParent();
		BufferedOutputStream bos = null;
		try {
			Files.createDirectories(folderPath);
			Files.createFile(file);
			bos = new BufferedOutputStream(new FileOutputStream(file.toFile()));
			bos.write(data);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (bos != null) {
				try {
					bos.flush();
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}
	
	private FileResourcePlacement getDefPlacement () {
		return FileResourcePlacement.valueOf(defFilesPlacement.toUpperCase());
	}

}

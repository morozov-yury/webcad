package diploma.webcad.core.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.zeroturnaround.zip.ZipUtil;

import diploma.webcad.core.dao.FileResourceDao;
import diploma.webcad.core.model.User;
import diploma.webcad.core.model.resource.FSResource;
import diploma.webcad.core.model.resource.FSResourcePlacement;
import diploma.webcad.core.model.resource.FSResourceType;

@Service
@Scope("singleton")
public class FSResourceService {
	
	private static Logger log = LoggerFactory.getLogger(FSResourceService.class);
	
	@Value("${fileresource.placement.app_server.path}")
	private String appServResPlacementPath;
	
	@Value("${fileresource.placement.neclus.path}")
	private String neclusResPlacementPath;
	
	@Value("${fileresource.placement.type}")
	private String defFilesPlacement;
	
	@Autowired
	private FileResourceDao fileResourceDao;
	
	public FSResource createFileResByContent (User user, byte[] data) {
		FSResource fsResource = new FSResource();
		fsResource.setFsResourceType(FSResourceType.FILE);
		fsResource.setUser(user);
		fsResource.setPlacement(getDefPlacement());
		fileResourceDao.saveOrUpdate(fsResource);
		
		boolean saveFileData = saveFileData(fsResource, data);
		if (!saveFileData) {
			return null;
		}
		
		return fsResource;
	}
	
	public FSResource createFolder (User user) {
		FSResource fsResource = new FSResource();
		fsResource.setFsResourceType(FSResourceType.FOLDER);
		fsResource.setUser(user);
		fsResource.setPlacement(getDefPlacement());
		fileResourceDao.saveOrUpdate(fsResource);
		if (!saveFolder(fsResource)) {
			fileResourceDao.delete(fsResource);
			return null;
		}
		return fsResource;
	}
	
	private boolean saveFolder (FSResource fsResource) {
		if (fsResource.getFsResourceType() != FSResourceType.FOLDER) {
			log.error("Can't save folder, because FSResource isn't folder");
			log.error("FSResource.id=[{}], type=[{}]", 
					fsResource.getId(), 
					fsResource.getFsResourceType());
			return false;
		}
		
		Path folderPath = Paths.get(getFSResourcePath(fsResource));
		if (folderPath.toFile().exists()) {
			log.error("Can't save folder, because it already created");
			log.error("FSResource.id=[{}], type=[{}]", fsResource.getId());
			return false;
		}
		try {
			Files.createDirectories(folderPath);
		} catch (IOException e) {
			log.error("{}", e.getMessage());
			log.error("FSResource.id=[{}], type=[{}]", fsResource.getId());
			return false;
		}
		
		return true;
	}

	public String getFSResourcePath (FSResource fsResource) {
		Long userId = fsResource.getUser().getId();
		Long fileResourceId = fsResource.getId();
		StringBuilder filePath = new StringBuilder();
		
		switch (fsResource.getPlacement()) {
		case APP_SERVER:
			filePath.append(appServResPlacementPath);
			filePath.append("/").append(userId).append("/").append(fileResourceId);
			return filePath.toString();
		case NECLUS:
			filePath.append(neclusResPlacementPath);
			filePath.append("/").append(userId).append("/").append(fileResourceId);
			return filePath.toString();
		}
		
		throw new IllegalStateException("FileResourcePlacement " + fsResource.getPlacement() 
				+ " isn't implemented");
	}
	
	public String getFSResourceDirPath (FSResource fsResource) {
		Long userId = fsResource.getUser().getId();
		Long fileResourceId = fsResource.getId();
		StringBuilder filePath = new StringBuilder();
		
		switch (fsResource.getPlacement()) {
		case APP_SERVER:
			filePath.append(appServResPlacementPath);
			filePath.append("/").append(userId).append("/").append(fileResourceId);
			return filePath.toString();
		case NECLUS:
			filePath.append(neclusResPlacementPath);
			filePath.append("/").append(userId);
			return filePath.toString();
		}
		
		throw new IllegalStateException("FileResourcePlacement " + fsResource.getPlacement() 
				+ " isn't implemented");
	}
	
	private boolean saveFileData (FSResource fsResource, byte[] data) {
		String filePath = getFSResourcePath(fsResource);
		switch (fsResource.getPlacement()) {
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
	
	private FSResourcePlacement getDefPlacement () {
		return FSResourcePlacement.valueOf(defFilesPlacement.toUpperCase());
	}
	
	public byte[] getData (FSResource inputResource) {
		if (inputResource == null) {
			throw new IllegalStateException("FSResource can't be null");
		}
		if (inputResource.getFsResourceType() != FSResourceType.FILE) {
			throw new IllegalStateException("You can get byte data only from file");
		}
		
		String path = this.getFSResourcePath(inputResource);
		File file = new File(path);
		try {
			return IOUtils.toByteArray(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private String getFSResourceZipPath (FSResource fsResource) {
		String fsResourcePath = getFSResourcePath(fsResource);
		return fsResourcePath + ".zip";
	}
	
	public InputStream zipFSResource (FSResource fsResource) {
		FSResourcePlacement placement = fsResource.getPlacement();
		String fsResourcePath = getFSResourcePath(fsResource);
		String fsResourceZipPath = getFSResourceZipPath(fsResource);
		if (placement == FSResourcePlacement.APP_SERVER) {
			File data = new File(fsResourcePath);
			File zip = new File(fsResourceZipPath);
			try {
				if (zip.exists()) {
					return new FileInputStream(zip);
				}
				ZipUtil.pack(data, zip);
				return new FileInputStream(zip);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		}
		throw new IllegalStateException("Only APP_SERVER file placement allowed");
	}
	
	public void saveFSResource (FSResource fsResource) {
		fileResourceDao.saveOrUpdate(fsResource);
	}
	
	public FSResource copyFSResource (FSResource fsResource) {
		FSResource newFsResource = new FSResource();
		newFsResource.setUser(fsResource.getUser());
		newFsResource.setPlacement(FSResourcePlacement.APP_SERVER);
		newFsResource.setFsResourceType(fsResource.getFsResourceType());
		fileResourceDao.save(newFsResource);
		
		File fsResFile = new File(getFSResourcePath(fsResource));
		File newFSResFile = new File(getFSResourcePath(newFsResource));
		try {
			switch (fsResource.getPlacement()) {
			case APP_SERVER:
				if (fsResource.getFsResourceType() == FSResourceType.FOLDER) {
					FileUtils.copyDirectory(fsResFile, newFSResFile);
				} else {
					FileUtils.copyFile(fsResFile, newFSResFile);
				}
				break;
			case NECLUS:
				return null;
			default:
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return newFsResource;
	}

}

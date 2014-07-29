package diploma.webcad.core.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import diploma.webcad.core.dao.GenaLaunchDao;
import diploma.webcad.core.model.User;
import diploma.webcad.core.model.modelling.GenaLaunch;
import diploma.webcad.core.model.modelling.GenaPlacement;
import diploma.webcad.core.model.modelling.GenaResultStatus;
import diploma.webcad.core.model.resource.FSResource;

@Service
@Scope("singleton")
public class GenaService {
	
	private static Logger log = LoggerFactory.getLogger(GenaService.class);
	
	@Value("${gena.placement.tomcat}")
	private String genaIntPath;
	
	@Value("${gena.placement.cluster}")
	private String genaExtPath;
	
	@Autowired
	private GenaLaunchDao genaLaunchDao;
	
	@Autowired
	private FSResourceService fsManager;

	public GenaLaunch createGenaLaunch (User user, String  genaParams, String xmlDecription) {
		FSResource fileResource = fsManager.createFileResByContent(
				user, xmlDecription.getBytes());
		
		if (fileResource == null) {
			return null;
		}
		
		GenaLaunch genaLaunch =  new GenaLaunch();
		genaLaunch.setStatus(GenaResultStatus.FAILED);
		genaLaunch.setParams(genaParams);
		genaLaunch.setPlacement(GenaPlacement.INTERNAL);
		genaLaunch.setUser(user);
		genaLaunch.setData(fileResource);
		
		genaLaunchDao.saveOrUpdate(genaLaunch);

		GenaResultStatus genaResultStatus = runExecutable(genaLaunch);
		genaLaunch.setStatus(genaResultStatus);

		genaLaunchDao.saveOrUpdate(genaLaunch);
		return genaLaunch;
	}
	
	private GenaResultStatus runExecutable (GenaLaunch genaLaunch) {
		switch (genaLaunch.getPlacement()) {
		case EXTERNAL:
			return null;
		case INTERNAL:
			return runExecTomcat(genaLaunch);
		}
		return null;
	}
	
	private GenaResultStatus runExecTomcat (GenaLaunch genaLaunch) {
		GenaResultStatus resultStatus = GenaResultStatus.PROCESSED;
		
		FSResource resultFolder = fsManager.createFolder(genaLaunch.getUser());
		if (resultFolder == null) {
			log.error("Can't create result folder");
			return null;
		}

		String sourcePath = fsManager.getFSResourcePath(genaLaunch.getData());
		String genaParams = genaLaunch.getParams();
		String resultFolderPath =  fsManager.getFSResourcePath(resultFolder);
		String cmd = new StringBuilder(genaIntPath).append(" ").append(sourcePath).append(" ")
				.append(resultFolderPath).append(" ").append(genaParams).toString();
		
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			switch (process.waitFor()) {
			case 0:
				genaLaunch.setResult(resultFolder);
				resultStatus = GenaResultStatus.SUCCESSFUL;
				break;
			case 1:
				resultStatus = GenaResultStatus.INCCORECT_PARAMS;
				break;
			case 2:
				resultStatus = GenaResultStatus.INCCORECT_XML;
				break;
			default:
				resultStatus = GenaResultStatus.UNEXPECTED_ERROR;
				break;
			}
		} catch (IOException e) {
			return GenaResultStatus.RUNTIME_ERROR;
		} catch (InterruptedException e) {
			return GenaResultStatus.RUNTIME_ERROR;
		}
		
		genaLaunch.setStatus(resultStatus);
		
		return resultStatus;
	}
	
	public List<GenaLaunch> listAllLaunches (User user) {
		return genaLaunchDao.list("user", user);
	}
	
	public List<GenaLaunch> listLastUserLauches(User user, int count) {
		return genaLaunchDao.listLastUserLauches(user, count);
	}
	
	public List<GenaLaunch> listLastUserLauches(User user, GenaResultStatus status, int count) {
		return genaLaunchDao.listLastUserLauches(user, status, count);
	}

}

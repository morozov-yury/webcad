package diploma.webcad.core.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import diploma.webcad.core.dao.GenaLaunchDao;
import diploma.webcad.core.model.User;
import diploma.webcad.core.model.gena.GenaLaunch;
import diploma.webcad.core.model.gena.GenaPlacement;
import diploma.webcad.core.model.gena.GenaResult;
import diploma.webcad.core.model.gena.GenaResultStatus;
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
	private FSResourceManager fsManager;

	public GenaLaunch createGenaLaunch (User user, String  genaParams, String xmlDecription) {
		GenaResult genaResult = new GenaResult();
		genaResult.setGenaResultStatus(GenaResultStatus.FAILED);
		
		FSResource fileResource = fsManager.createFileResByContent(
				user, xmlDecription.getBytes());
		
		GenaLaunch genaLaunch =  new GenaLaunch();
		genaLaunch.setGenaParams(genaParams);
		genaLaunch.setGenaPlacement(GenaPlacement.INTERNAL);
		genaLaunch.setGenaResult(genaResult);
		genaLaunch.setUser(user);
		genaLaunchDao.saveOrUpdate(genaLaunch);
		
		if (fileResource == null) {
			genaLaunchDao.saveOrUpdate(genaLaunch);
			return genaLaunch;
		}

		genaLaunch.setInputData(fileResource);

		GenaResultStatus genaResultStatus = runExecutable(genaLaunch);
		genaResult.setGenaResultStatus(genaResultStatus);

		genaLaunchDao.saveOrUpdate(genaLaunch);
		return genaLaunch;
	}
	
	private GenaResultStatus runExecutable (GenaLaunch genaLaunch) {
		switch (genaLaunch.getGenaPlacement()) {
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

		String sourcePath = fsManager.getFSResourcePath(genaLaunch.getInputData());
		String genaParams = genaLaunch.getGenaParams();
		String resultFolderPath =  fsManager.getFSResourcePath(resultFolder);
		String cmd = new StringBuilder(genaIntPath).append(" ").append(sourcePath).append(" ")
				.append(resultFolderPath).append(" ").append(genaParams).toString();
		
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			switch (process.waitFor()) {
			case 0:
				genaLaunch.setResultData(resultFolder);
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
		
		genaLaunch.getGenaResult().setGenaResultStatus(resultStatus);
		
		return resultStatus;
	}

}

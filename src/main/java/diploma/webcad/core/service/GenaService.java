package diploma.webcad.core.service;

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
import diploma.webcad.core.model.resource.FileResource;

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
	private FileResourceManager fileResourceManager;

	public GenaResult run (User user, String  genaParams, String xmlDecription) {
		GenaResult genaResult = new GenaResult();
		genaResult.setGenaResultStatus(GenaResultStatus.SUCCESSFUL);
		
		FileResource fileResource = fileResourceManager.createFileResByContent(
				user, xmlDecription.getBytes());
		
		GenaLaunch genaLaunch =  new GenaLaunch();
		genaLaunch.setGenaParams(genaParams);
		genaLaunch.setGenaPlacement(GenaPlacement.INTERNAL);
		genaLaunch.setGenaResult(genaResult);
		genaLaunch.setUser(user);
		
		genaLaunchDao.saveOrUpdate(genaLaunch);
		
		if (fileResource == null) {
			genaResult.setGenaResultStatus(GenaResultStatus.FAILED);
			genaLaunchDao.saveOrUpdate(genaLaunch);
			return genaResult;
		}
		
		genaLaunch.setInputData(fileResource);

		
		
		
		
		genaLaunchDao.saveOrUpdate(genaLaunch);
		
		return genaResult;
	}

}

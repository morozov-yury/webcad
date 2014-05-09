package diploma.webcad.core.service;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class GenaService {
	
	@Value("${gena.path}")
	private String genaPath;
	
	private static Logger log = LoggerFactory.getLogger(GenaService.class);
	
	public void run (String params) {
		log.info(params);
		String runCommand = "\"" + genaPath + "\" C:/programs/apache-tomcat-7.0.47/gena/1394554287130.xml " + params;
		log.info(runCommand);
	}

}

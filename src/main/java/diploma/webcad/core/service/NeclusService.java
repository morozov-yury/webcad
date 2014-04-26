package diploma.webcad.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

@Service
@Scope("singleton")
public class NeclusService {
	
	private static Logger log = LoggerFactory.getLogger(NeclusService.class);
	
	@Autowired
	private SshProperties sshProperties;
	
	public boolean isNeclusOnline () {
		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(sshProperties.getUsername(), sshProperties.getHost(), sshProperties.getPort());
			session.setUserInfo(sshProperties);
			session.connect();
			session.disconnect();
		} catch (JSchException e) {
			log.info(e.getMessage());
			return false;
		} 
		return true;
	}

}

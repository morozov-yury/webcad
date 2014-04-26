package diploma.webcad.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.UserInfo;

@Service
@Scope("singleton")
public class SshProperties implements UserInfo {
	
	private static Logger log = LoggerFactory.getLogger(UserInfo.class);
	
	@Autowired
	private WebCadProperties propertyManager;

	@Override
	public String getPassphrase() {
		return propertyManager.getProperty("Passphrase");
	}

	@Override
	public String getPassword() {
		return propertyManager.getProperty("ssh.neclus.password");
	}

	@Override
	public boolean promptPassphrase(String arg0) {
		log.info(arg0);
		return true;
	}

	@Override
	public boolean promptPassword(String arg0) {
		//log.info(arg0);
		return true;
	}

	@Override
	public boolean promptYesNo(String arg0) {
		//log.info(arg0);
		return true;
	}

	@Override
	public void showMessage(String arg0) {
		log.info(arg0);
	}

	public String getUsername() {
		return propertyManager.getProperty("ssh.neclus.username");
	}

	public String getHost() {
		return propertyManager.getProperty("ssh.neclus.host");
	}

	public int getPort() {
		return Integer.valueOf(propertyManager.getProperty("ssh.neclus.port"));
	}

}

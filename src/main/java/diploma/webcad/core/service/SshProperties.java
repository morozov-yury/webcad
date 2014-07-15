package diploma.webcad.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.UserInfo;

@Service
@Scope("singleton")
public class SshProperties implements UserInfo {
	
	private static Logger log = LoggerFactory.getLogger(UserInfo.class);
	
	@Value("${ssh.neclus.password}")
	private String password;
	
	@Value("${ssh.neclus.username}")
	private String username;
	
	@Value("${ssh.neclus.host}")
	private String host;
	
	@Value("${ssh.neclus.port}")
	private String port;

	@Override
	public String getPassphrase() {
		return "Passphrase";
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean promptPassphrase(String arg0) {
		return true;
	}

	@Override
	public boolean promptPassword(String arg0) {
		return true;
	}

	@Override
	public boolean promptYesNo(String arg0) {
		return true;
	}

	@Override
	public void showMessage(String arg0) {
		log.info(arg0);
	}

	public String getUsername() {
		return username;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return Integer.valueOf(port);
	}

}

package diploma.webcad.core.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.util.ArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.gwt.user.client.rpc.core.java.util.Collections;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import diploma.webcad.core.model.ExecResult;

@Service
@Scope("singleton")
public class SshService implements InitializingBean, DisposableBean   {
	
	private static Logger log = LoggerFactory.getLogger(SshService.class);
	
	@Autowired
	private SshProperties sshProperties;

	private Session session;

	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			updateeSession();
		} catch (Exception e) {
			session = null;
			e.printStackTrace();
		}
	}
	
	@Override
	public void destroy() throws Exception {
		session.disconnect();
	}
	
	private void updateeSession () throws JSchException {
		JSch jsch = new JSch();
		session = jsch.getSession(sshProperties.getUsername(), sshProperties.getHost(), 
				sshProperties.getPort());
		session.setUserInfo(sshProperties);
		session.connect();
	}
	
	public boolean isNeclusOnline () {
		return true;
	}
	
	public void test () throws JSchException, IOException {
		log.info("");
		String command = "ls";
		
		ChannelExec channel = (ChannelExec) session.openChannel("exec");
		channel.setCommand(command);
    	channel.setXForwarding(true);
    	ExecResult execResult = exec("ls");
    	log.info("{}", execResult);   
	}
	
	public ExecResult exec(String command) throws JSchException, IOException {
		ExecResult execResult = new ExecResult();
		ChannelExec channel = (ChannelExec) session.openChannel("exec");
		channel.setCommand(command);
    	channel.setXForwarding(true);

    	ByteArrayOutputStream baos = new ByteArrayOutputStream();

		InputStream in = channel.getInputStream();
		channel.connect();
    	while (true) {
    		while(in.available() > 0) {
    			byte[] tmp = new byte[in.available()];
	            int i = in.read(tmp, 0, in.available());
	            baos.write(tmp);
	            if (i < 0) {
	            	break;
	            }
    		}
    		if (channel.isClosed()) {
    			if(in.available() > 0) {
    				continue; 
    			}
            	execResult.setExitStatus(channel.getExitStatus());
            	break;
    		}
    		try{
    			Thread.sleep(1000);
			} catch(Exception ee) { }
    	}

		channel.disconnect();

		execResult.setData(baos.toByteArray());

		return execResult;
	}

}

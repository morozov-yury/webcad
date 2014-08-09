package diploma.webcad.core.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.hwpf.model.FSPADocumentPart;
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
import diploma.webcad.core.model.resource.FSResource;
import diploma.webcad.core.model.resource.FSResourcePlacement;

@Service
@Scope("singleton")
public class SshService implements InitializingBean, DisposableBean   {
	
	private static Logger log = LoggerFactory.getLogger(SshService.class);
	
	@Autowired
	private SshProperties sshProperties;
	
	@Autowired
	private FSResourceService fsResourceService;

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
		log.info("Making ssh connection to {}", sshProperties.getHost());
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
	
	public ExecResult transferResToNeclus (FSResource fsResource) throws Exception {
		if (fsResource.getPlacement() != FSResourcePlacement.APP_SERVER) {
			throw new IllegalStateException("FSResource must settle on app server");
		}
		
		if (!session.isConnected()) {
			session.connect();
		}
		
		ExecResult execResult = null;
		
		String fsLocalPath = fsResourceService.getFSResourcePath(fsResource);
		File file = new File(fsResourceService.getFSResourcePath(fsResource));
		fsResource.setPlacement(FSResourcePlacement.NECLUS);
		String fsRemotePath = fsResourceService.getFSResourcePath(fsResource);
		
		execResult = makeRemoteDir(fsResourceService.getFSResourceDirPath(fsResource));
		if (execResult.getExitStatus() != 0) {
			return execResult;
		}
		
		String command = "scp -r -t " + fsRemotePath;
		ChannelExec channel = (ChannelExec) session.openChannel("exec");
		channel.setCommand(command);
		
		OutputStream out = channel.getOutputStream();
		InputStream in = channel.getInputStream();
		
		channel.connect();
		execResult = checkExexResult(in);
		if (execResult.getExitStatus() != 0) {
			return execResult;
		}
		
		long filesize = file.length();
		command = "C0644 " + filesize + " ";
		
		if (fsLocalPath.lastIndexOf('/') > 0 ){
			command += fsLocalPath.substring(fsLocalPath.lastIndexOf('/') + 1);
		} else{
			command += fsLocalPath;
		}
		command += "\n";
		
		out.write(command.getBytes()); 
		out.flush();
		
		execResult = checkExexResult(in);
		if (execResult.getExitStatus() != 0) {
			return execResult;
		}
		
		FileInputStream  fis = new FileInputStream(fsLocalPath);
		byte[] buf = new byte[1024];
		while (true) {
			int len = fis.read(buf, 0, buf.length);
			if (len <= 0) break;
			out.write(buf, 0, len); //out.flush();
		}
		fis.close();
		
		out.write(0);
		out.flush();
		
		execResult = checkExexResult(in);
		if (execResult.getExitStatus() != 0) {
			return execResult;
		}
		
		out.close();
		channel.disconnect();
		
		fsResourceService.saveFSResource(fsResource);
		
		execResult.setExitStatus(0);
		execResult.setData("Transfer was successful".getBytes());
		return execResult;
	}
	
	private ExecResult checkExexResult (InputStream in) throws IOException {
		ExecResult execResult = new ExecResult();
		if (in.read() != 0) {
			StringBuffer sb = new StringBuffer();
			int c;
			do {
				c=in.read();
				sb.append((char)c);
			}
			while(c!='\n');
			execResult.setData(sb.toString().getBytes());
		}
		return execResult;
	}
	
	public ExecResult makeRemoteDir (String dirPath) throws Exception {
		String command = "mkdir -p " + dirPath;
		return exec(command);
	}

}

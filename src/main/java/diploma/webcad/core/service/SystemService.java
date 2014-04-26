package diploma.webcad.core.service;

import java.net.URI;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.vaadin.server.Page;

import diploma.webcad.core.dao.ApplicationConstantDao;
import diploma.webcad.core.data.appconstants.Constant;
import diploma.webcad.core.data.appconstants.Constants;
import diploma.webcad.core.model.ApplicationConstant;
import diploma.webcad.core.model.ApplicationConstantType;

@Service
@Scope("singleton")
public class SystemService {
	
	@Autowired
	private ApplicationConstantDao applicationConstantDao;
	
	public String getConstantValue(String id) {
		if(id == null) return null;
		final ApplicationConstant constant = getApplicationConstant(id);
		if(constant == null) return "";
		return constant.getValue();
	}
	
	public ApplicationConstant getApplicationConstant(String id) {
		if(id == null) return null;
		return applicationConstantDao.read(id);
	}
	
	public void saveApplicationConstant(ApplicationConstant constant) {
		if(constant == null) return;
		try {
			applicationConstantDao.saveOrUpdate(constant);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readConstants(Constants constants) {
		for(Constant constant : constants.getConstants()) {
			if(getApplicationConstant(constant.getKey()) == null) {
				ApplicationConstantType type;
				try {
					type = ApplicationConstantType.valueOf(constant.getType());
				} catch(Exception ex) {
					type = ApplicationConstantType.getDefault();
				}
				saveApplicationConstant(new ApplicationConstant(constant.getKey(), 
						constant.getValue(), constant.getDescription(), 
						type));
			}
		}
	}
	
	public String getApplicationPath() {
		URI location = Page.getCurrent().getLocation();
		StringBuilder sb = new StringBuilder();
		sb.append(location.getScheme())
			.append("://")
			.append(location.getHost());
		if(location.getPort() >= 0) {
			sb.append(":")
				.append(location.getPort());
		}
		sb.append(StringUtils.removeEnd(location.getPath(), "/"));
		return sb.toString();
	}
}

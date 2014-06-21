package diploma.webcad.core.service;

import java.net.URI;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.server.Page;

import diploma.webcad.core.dao.AppConstantDao;
import diploma.webcad.core.data.appconstants.Constant;
import diploma.webcad.core.data.appconstants.Constants;
import diploma.webcad.core.model.constant.AppConstant;
import diploma.webcad.core.model.constant.AppConstantType;

@Service
@Scope("singleton")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class SystemService {
	
	@Autowired
	private AppConstantDao applicationConstantDao;
	
	public String getConstantValue(String id) {
		if(id == null) return null;
		final AppConstant constant = getApplicationConstant(id);
		if(constant == null) return "";
		return constant.getValue();
	}
	
	public AppConstant getApplicationConstant(String id) {
		if(id == null) return null;
		return applicationConstantDao.read(id);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void saveApplicationConstant(AppConstant constant) {
		if(constant == null) {
			return;
		}
		try {
			applicationConstantDao.saveOrUpdate(constant);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readConstants(Constants constants) {
		for(Constant constant : constants.getConstants()) {
			if(getApplicationConstant(constant.getKey()) == null) {
				AppConstantType type;
				try {
					type = AppConstantType.valueOf(constant.getType());
				} catch(Exception ex) {
					type = AppConstantType.getDefault();
				}
				AppConstant appConstant = new AppConstant(
						constant.getKey(), constant.getValue(), constant.getDescription(),  type);
				saveApplicationConstant(appConstant);
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

package diploma.webcad.core.dao.impl;

import org.springframework.stereotype.Component;

import diploma.webcad.core.dao.TriggerJobInfoDao;
import diploma.webcad.core.model.trigger.TriggerJobInfo;

@Component("triggerJobInfoDao")
public class TriggerJobInfoDaoImpl extends BaseDaoImpl<TriggerJobInfo, Long>
		implements TriggerJobInfoDao {

	public TriggerJobInfoDaoImpl() {
		super(TriggerJobInfo.class);
	}


}

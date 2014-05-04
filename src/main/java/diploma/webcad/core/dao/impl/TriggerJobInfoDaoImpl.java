package diploma.webcad.core.dao.impl;

import org.springframework.stereotype.Repository;

import diploma.webcad.core.dao.TriggerJobInfoDao;
import diploma.webcad.core.model.trigger.TriggerJobInfo;

@Repository
public class TriggerJobInfoDaoImpl extends BaseDaoImpl<TriggerJobInfo, Long>
		implements TriggerJobInfoDao {

	public TriggerJobInfoDaoImpl() {
		super(TriggerJobInfo.class);
	}


}

package diploma.webcad.trigger;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import diploma.webcad.core.dao.TriggerJobInfoDao;
import diploma.webcad.core.model.trigger.TriggerJobType;
import diploma.webcad.core.service.TriggerService;
import diploma.webcad.core.util.date.DateUtils;

public abstract class AbstractTriggerJob implements TriggerJob {

	private static final long serialVersionUID = 1756345435752342321L;

	@Autowired
	private TriggerService triggerManager;
	
	@Autowired
	private TriggerJobInfoDao triggerJobInfoDao;
	
	private Boolean working = false;

	@Override
	public final void performTriggerLogic() throws Exception {
		if (!working) {
			try {
				this.working = true;
				System.out.println(DateUtils.formatDateTime(new Date()) + " ###--TRIGGER--### " 
						+ getTriggerJobType().toString() + " START");
				perform();
			} finally {
				this.working = false;
				System.out.println(DateUtils.formatDateTime(new Date()) + " ###--TRIGGER--### " 
						+ getTriggerJobType().toString() + " END");
			}
		} else {
			System.out.println("Trigger still works. Calling canceled");
		}
	}
	
	protected abstract void perform ()  throws Exception;
	
	public abstract TriggerJobType getTriggerJobType ();

	public TriggerService getTriggerManager() {
		return triggerManager;
	}

	public TriggerJobInfoDao getTriggerJobInfoDao() {
		return triggerJobInfoDao;
	}
	
	protected Long getCurrentTime() {
		return (new Date()).getTime();
	}

}

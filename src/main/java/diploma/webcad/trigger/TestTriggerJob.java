package diploma.webcad.trigger;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import diploma.webcad.core.annotation.HibernateSessionRequired;
import diploma.webcad.core.model.trigger.TriggerJobType;
import diploma.webcad.core.service.RuntimeRegistrator;
import diploma.webcad.core.service.UserService;

@Service("testTriggerJob")
public class TestTriggerJob extends AbstractTriggerJob {
	private static final long serialVersionUID = 8704847374791432866L;

	private transient static final Logger log = Logger
			.getLogger(TestTriggerJob.class);
	
	@Autowired
	private RuntimeRegistrator runtimeRegistrator;
	
	@Autowired
	private UserService userManager;
	
	@HibernateSessionRequired
	public void perform() throws Exception {
		System.out.println("Test trigger job start!");
		log.error("Test trigger job start!");

		System.out.println("Test trigger job end!");
		log.error("Test trigger job end!");
	}

	@Override
	public TriggerJobType getTriggerJobType() {
		return TriggerJobType.TEST;
	}
}

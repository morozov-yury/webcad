package diploma.webcad.trigger;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import diploma.webcad.core.annotation.HibernateSessionRequired;

@Service("testTriggerJob")
public class TestTriggerJob implements TriggerJob {
	
	private static final long serialVersionUID = 8704847374791432866L;

	private transient static final Logger log = Logger.getLogger(TestTriggerJob.class);
	
	@HibernateSessionRequired
	public void performTriggerLogic() {
		System.out.println("Test trigger job start!");
		log.error("Test trigger job start!");

		System.out.println("Test trigger job end!");
		log.error("Test trigger job end!");
	}

}

package diploma.webcad.trigger;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class TriggerJobBean extends QuartzJobBean {

    private static final Logger log = Logger.getLogger(TriggerJobBean.class); 

	private ApplicationContext ctx;
	private String triggerJob;

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.ctx = applicationContext;
	}

	protected void executeInternal(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		TriggerJob triggerJob = (TriggerJob) ctx.getBean(getTriggerJob());
		try {
			triggerJob.performTriggerLogic();
		} catch (Exception e) {
		    log.error("Trigger job invocation error: ", e);
			e.printStackTrace();
		}
	}

	public void setTriggerJob(String triggerJob) {
		this.triggerJob = triggerJob;
	}

	public String getTriggerJob() {
		return triggerJob;
	}

}

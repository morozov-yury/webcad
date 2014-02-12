package diploma.webcad.core.model.trigger;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "triggerJobInfo_Contest_Start_Request_Accepting")
public class ContestStartRequestAcceptingInfo extends TriggerJobInfo implements Serializable {

	private static final long serialVersionUID = 17653652387239202L;
	
	private Boolean contestAware;

	public ContestStartRequestAcceptingInfo() {
		setContestAware(false);
	}

	public Boolean getContestAware() {
		return contestAware;
	}

	public void setContestAware(Boolean contestAware) {
		this.contestAware = contestAware;
	}

}

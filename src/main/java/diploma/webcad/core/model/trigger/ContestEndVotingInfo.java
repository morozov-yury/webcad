package diploma.webcad.core.model.trigger;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "triggerJobInfo_Contest_End_Voting")
public class ContestEndVotingInfo extends TriggerJobInfo implements Serializable {
	
	private static final long serialVersionUID = 1765365193544202L;
	
	private Boolean contestAware20perc;
	private Boolean contestAware10perc;
	private Boolean contestAware5perc;

	public ContestEndVotingInfo() {
		setContestAware10perc(false);
		setContestAware20perc(false);
		setContestAware5perc(false);
	}
	
	public Boolean getContestAware20perc() {
		return contestAware20perc;
	}

	public void setContestAware20perc(Boolean contestAware20perc) {
		this.contestAware20perc = contestAware20perc;
	}

	public Boolean getContestAware10perc() {
		return contestAware10perc;
	}

	public void setContestAware10perc(Boolean contestAware10perc) {
		this.contestAware10perc = contestAware10perc;
	}

	public Boolean getContestAware5perc() {
		return contestAware5perc;
	}

	public void setContestAware5perc(Boolean contestAware5perc) {
		this.contestAware5perc = contestAware5perc;
	}

}

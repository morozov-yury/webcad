package diploma.webcad.core.model.trigger;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "triggerJobInfo")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class TriggerJobInfo implements Serializable {

	private static final long serialVersionUID = 12234255141424L;
	
	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
}

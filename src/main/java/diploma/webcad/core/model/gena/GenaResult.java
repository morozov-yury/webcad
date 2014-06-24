package diploma.webcad.core.model.gena;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "genaResults")
public class GenaResult implements Serializable {

	private static final long serialVersionUID = -5398917912639586732L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "genaResultStatus", nullable = false, unique = false)
	private GenaResultStatus genaResultStatus;
	
	public GenaResult () {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GenaResultStatus getGenaResultStatus() {
		return genaResultStatus;
	}

	public void setGenaResultStatus(GenaResultStatus genaResultStatus) {
		this.genaResultStatus = genaResultStatus;
	}

}

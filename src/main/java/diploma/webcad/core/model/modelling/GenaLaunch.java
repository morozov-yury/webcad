package diploma.webcad.core.model.modelling;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import diploma.webcad.core.model.User;
import diploma.webcad.core.model.resource.FSResource;

@Entity
@Table(name = "genaLaunches")
public class GenaLaunch implements Serializable {

	private static final long serialVersionUID = 1900655406665158287L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(targetEntity = User.class, optional = false)
	private User user;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@Column(name = "genaPlacement", nullable = false)
	private GenaPlacement genaPlacement;
	
	@Column(name = "genaParams", nullable = false, unique = false, length = 64)
	private String genaParams;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = FSResource.class, optional = true)
	private FSResource inputResource;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = FSResource.class, optional = true)
	private FSResource resultResource;
	
	@Column(name = "genaResultStatus", nullable = false)
	private GenaResultStatus genaResultStatus;
	
	public GenaLaunch () {
		setCreationDate(new Date());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public GenaPlacement getGenaPlacement() {
		return genaPlacement;
	}

	public void setGenaPlacement(GenaPlacement genaPlacement) {
		this.genaPlacement = genaPlacement;
	}

	public String getGenaParams() {
		return genaParams;
	}

	public void setGenaParams(String genaParams) {
		this.genaParams = genaParams;
	}

	public FSResource getInputResource() {
		return inputResource;
	}

	public void setInputResource(FSResource inputData) {
		this.inputResource = inputData;
	}

	public FSResource getResultResource() {
		return resultResource;
	}

	public void setResultResource(FSResource resultData) {
		this.resultResource = resultData;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public GenaResultStatus getGenaResultStatus() {
		return genaResultStatus;
	}

	public void setGenaResultStatus(GenaResultStatus genaResultStatus) {
		this.genaResultStatus = genaResultStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getCreationDate() == null) ? 0 : getCreationDate().hashCode());
		result = prime * result
				+ ((getGenaParams() == null) ? 0 : getGenaParams().hashCode());
		result = prime * result
				+ ((getGenaPlacement() == null) ? 0 : getGenaPlacement().hashCode());
		result = prime
				* result
				+ ((getGenaResultStatus() == null) ? 0 : getGenaResultStatus().hashCode());
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getInputResource() == null) ? 0 : getInputResource().hashCode());
		result = prime * result
				+ ((getResultResource() == null) ? 0 : getResultResource().hashCode());
		result = prime * result + ((getUser() == null) ? 0 : getUser().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenaLaunch other = (GenaLaunch) obj;
		if (getCreationDate() == null) {
			if (other.getCreationDate() != null)
				return false;
		} else if (!getCreationDate().equals(other.getCreationDate()))
			return false;
		if (getGenaParams() == null) {
			if (other.getGenaParams() != null)
				return false;
		} else if (!getGenaParams().equals(other.getGenaParams()))
			return false;
		if (getGenaPlacement() != other.getGenaPlacement())
			return false;
		if (getGenaResultStatus() != other.getGenaResultStatus())
			return false;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		if (getInputResource() == null) {
			if (other.getInputResource() != null)
				return false;
		} else if (!getInputResource().equals(other.getInputResource()))
			return false;
		if (getResultResource() == null) {
			if (other.getResultResource() != null)
				return false;
		} else if (!getResultResource().equals(other.getResultResource()))
			return false;
		if (getUser() == null) {
			if (other.getUser() != null)
				return false;
		} else if (!getUser().equals(other.getUser()))
			return false;
		return true;
	}

}

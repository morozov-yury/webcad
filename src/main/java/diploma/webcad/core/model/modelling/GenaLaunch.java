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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import diploma.webcad.core.model.User;
import diploma.webcad.core.model.resource.FSResource;

@Entity
public class GenaLaunch implements Serializable {

	private static final long serialVersionUID = -6359344158229211420L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(optional = false)
	private User user;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@Column(nullable = false)
	private GenaPlacement placement;
	
	@Column(nullable = false, length = 64)
	private String params;
	
	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	private FSResource data;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private FSResource result;
	
	private GenaResultStatus status;
	
	public GenaLaunch () {
		setStatus(GenaResultStatus.FAILED);
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

	public GenaPlacement getPlacement() {
		return placement;
	}

	public void setPlacement(GenaPlacement genaPlacement) {
		this.placement = genaPlacement;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String genaParams) {
		this.params = genaParams;
	}

	public FSResource getData() {
		return data;
	}

	public void setData(FSResource data) {
		this.data = data;
	}

	public FSResource getResult() {
		return result;
	}

	public void setResult(FSResource result) {
		this.result = result;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public GenaResultStatus getStatus() {
		return status;
	}

	public void setStatus(GenaResultStatus status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getCreationDate() == null) ? 0 : getCreationDate().hashCode());
		result = prime * result
				+ ((getParams() == null) ? 0 : getParams().hashCode());
		result = prime * result
				+ ((getPlacement() == null) ? 0 : getPlacement().hashCode());
		result = prime
				* result
				+ ((getStatus() == null) ? 0 : getStatus().hashCode());
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getData() == null) ? 0 : getData().hashCode());
		result = prime * result
				+ ((getResult() == null) ? 0 : getResult().hashCode());
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
		if (getParams() == null) {
			if (other.getParams() != null)
				return false;
		} else if (!getParams().equals(other.getParams()))
			return false;
		if (getPlacement() != other.getPlacement())
			return false;
		if (getStatus() != other.getStatus())
			return false;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		if (getData() == null) {
			if (other.getData() != null)
				return false;
		} else if (!getData().equals(other.getData()))
			return false;
		if (getResult() == null) {
			if (other.getResult() != null)
				return false;
		} else if (!getResult().equals(other.getResult()))
			return false;
		if (getUser() == null) {
			if (other.getUser() != null)
				return false;
		} else if (!getUser().equals(other.getUser()))
			return false;
		return true;
	}

}

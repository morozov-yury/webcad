package diploma.webcad.core.model.modelling;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import diploma.webcad.core.model.User;
import diploma.webcad.core.model.resource.FSResource;
import diploma.webcad.core.service.XilinxProjectStatus;

@Entity
public class XilinxProject implements Serializable {

	private static final long serialVersionUID = 8298386498399209809L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(optional = false)
	private User user;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	private FSResource folder;
	
	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	private GenaLaunch genaLaunch;
	
	@Enumerated
	private XilinxProjectStatus status;

	public XilinxProject() {
		
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public FSResource getFolder() {
		return folder;
	}

	public void setFolder(FSResource folder) {
		this.folder = folder;
	}

	public GenaLaunch getGenaLaunch() {
		return genaLaunch;
	}

	public void setGenaLaunch(GenaLaunch genaLaunch) {
		this.genaLaunch = genaLaunch;
	}

	public XilinxProjectStatus getStatus() {
		return status;
	}

	public void setStatus(XilinxProjectStatus status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getCreationDate() == null) ? 0 : getCreationDate().hashCode());
		result = prime * result + ((getFolder() == null) ? 0 : getFolder().hashCode());
		result = prime * result
				+ ((getGenaLaunch() == null) ? 0 : getGenaLaunch().hashCode());
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
		result = prime * result + ((getUser() == null) ? 0 : getUser().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		XilinxProject other = (XilinxProject) obj;
		if (getCreationDate() == null) {
			if (other.getCreationDate() != null) {
				return false;
			}
		} else if (!getCreationDate().equals(other.getCreationDate())) {
			return false;
		}
		if (getFolder() == null) {
			if (other.getFolder() != null) {
				return false;
			}
		} else if (!getFolder().equals(other.getFolder())) {
			return false;
		}
		if (getGenaLaunch() == null) {
			if (other.getGenaLaunch() != null) {
				return false;
			}
		} else if (!getGenaLaunch().equals(other.getGenaLaunch())) {
			return false;
		}
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		if (getStatus() != other.getStatus()) {
			return false;
		}
		if (getUser() == null) {
			if (other.getUser() != null) {
				return false;
			}
		} else if (!getUser().equals(other.getUser())) {
			return false;
		}
		return true;
	}
	
}

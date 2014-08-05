package diploma.webcad.core.model.simulation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import diploma.webcad.core.model.User;

@Entity
public class BatchSimulation implements Serializable {

	private static final long serialVersionUID = -9133325668305624613L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(optional = false)
	private User user;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<XilinxProject> projects;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Device> devices;
	
	@Column(nullable = false)
	private BatchSimulationStatus status;

	public BatchSimulation() {
		setCreationDate(new Date());
		setProjects(new ArrayList<XilinxProject>());
		setDevices(new ArrayList<Device>());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getCreationDate() == null) ? 0 : getCreationDate().hashCode());
		result = prime * result + ((getDevices() == null) ? 0 : getDevices().hashCode());
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getProjects() == null) ? 0 : getProjects().hashCode());
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
		BatchSimulation other = (BatchSimulation) obj;
		if (getCreationDate() == null) {
			if (other.getCreationDate() != null) {
				return false;
			}
		} else if (!getCreationDate().equals(other.getCreationDate())) {
			return false;
		}
		if (getDevices() == null) {
			if (other.getDevices() != null) {
				return false;
			}
		} else if (!getDevices().equals(other.getDevices())) {
			return false;
		}
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		if (getProjects() == null) {
			if (other.getProjects() != null) {
				return false;
			}
		} else if (!getProjects().equals(other.getProjects())) {
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

	public List<XilinxProject> getProjects() {
		return projects;
	}

	public void setProjects(List<XilinxProject> projects) {
		this.projects = projects;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public BatchSimulationStatus getStatus() {
		return status;
	}

	public void setStatus(BatchSimulationStatus status) {
		this.status = status;
	}

}

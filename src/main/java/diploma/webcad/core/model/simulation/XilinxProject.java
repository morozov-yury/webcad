package diploma.webcad.core.model.simulation;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import diploma.webcad.core.model.resource.FSResource;
import diploma.webcad.core.service.XilinxProjectStatus;

@Entity
public class XilinxProject implements Serializable {

	private static final long serialVersionUID = 6925236967772395897L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private FSResource folder;
	
	@ManyToOne(optional = false)
	private GenaLaunch genaLaunch;
	
	@ManyToOne(optional = false)
	private Device device;
	
	@Enumerated
	private XilinxProjectStatus status;

	public XilinxProject() {
		setStatus(XilinxProjectStatus.PREPEARED_TO_CREATION);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getDevice() == null) ? 0 : getDevice().hashCode());
		result = prime * result + ((getFolder() == null) ? 0 : getFolder().hashCode());
		result = prime * result
				+ ((getGenaLaunch() == null) ? 0 : getGenaLaunch().hashCode());
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
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
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		if (getDevice() == null) {
			if (other.getDevice() != null) {
				return false;
			}
		} else if (!getDevice().equals(other.getDevice())) {
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
		if (getStatus() != other.getStatus()) {
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

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public XilinxProjectStatus getStatus() {
		return status;
	}

	public void setStatus(XilinxProjectStatus status) {
		this.status = status;
	}

}

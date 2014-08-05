package diploma.webcad.core.model.resource;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import diploma.webcad.core.model.User;

/**
 * This is entity that describe file system resource.
 * It can saved on different servers.
 * 
 * @author morozov.yury
 *
 */
@Entity
public class FSResource implements Serializable {
	
	private static final long serialVersionUID = -3542499586657600169L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class, optional = false)
	private User user;
	
	@Column(name = "creationDate", nullable = false)
	private Date creationDate;
	
	@Column(name = "placement", nullable = false)
	private FSResourcePlacement placement;
	
	@Column(name = "fsResourceType", nullable = false)
	private FSResourceType fsResourceType;

	public FSResource() {
		creationDate = new Date();
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

	public FSResourcePlacement getPlacement() {
		return placement;
	}

	public void setPlacement(FSResourcePlacement placement) {
		this.placement = placement;
	}

	public FSResourceType getFsResourceType() {
		return fsResourceType;
	}

	public void setFsResourceType(FSResourceType fsResourceType) {
		this.fsResourceType = fsResourceType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		FSResource other = (FSResource) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "FSResource [id=" + id + ", placement=" + placement
				+ ", fsResourceType=" + fsResourceType + "]";
	}

}

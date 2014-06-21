package diploma.webcad.core.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public abstract class ContentResource implements Serializable {
	private static final long serialVersionUID = -3542499586657600169L;
	
	private Long id;
	
	private String uri;
	
	private String description;
	
	private Boolean active;
	
	private Date creationDate;
	
	private Date deletionDate;

	public ContentResource() {
		creationDate = new Date();
		uri = "";
		description = "";
		active = false;
	}
	
	@Column(unique=true, updatable=true)
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
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
		ContentResource other = (ContentResource) obj;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}

	public Date getDeletionDate() {
		return deletionDate;
	}

	public void setDeletionDate(Date deletionDate) {
		this.deletionDate = deletionDate;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}

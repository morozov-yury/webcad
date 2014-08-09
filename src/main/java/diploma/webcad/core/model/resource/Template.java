package diploma.webcad.core.model.resource;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "template", propOrder = { "id", "title", "body" })
public class Template implements Serializable {

	private static final long serialVersionUID = 201346219620246305L;

	@Id
	@XmlAttribute(name = "id")
    private String id;
    
	@Lob
	@NotNull
	@XmlElement(name = "title")
    private String title;
    
	@Lob
	@NotNull
	@XmlElement(name = "body")
    private String body;

    public Template() {
    	
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getBody() == null) ? 0 : getBody().hashCode());
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
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
		Template other = (Template) obj;
		if (getBody() == null) {
			if (other.getBody() != null) {
				return false;
			}
		} else if (!getBody().equals(other.getBody())) {
			return false;
		}
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		if (getTitle() == null) {
			if (other.getTitle() != null) {
				return false;
			}
		} else if (!getTitle().equals(other.getTitle())) {
			return false;
		}
		return true;
	}

}

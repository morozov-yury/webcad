package diploma.webcad.core.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Template implements Serializable {

	private static final long serialVersionUID = -4439573560442073799L;

	@Id
    private String id;
    
	@Lob
	@ElementCollection(fetch = FetchType.EAGER)
    private Map<Language, String> titles;
    
	@Lob
	@ElementCollection(fetch = FetchType.EAGER)
    private Map<Language, String> bodies;
    
    private TemplateAccessType templateAccessType;
    
    public Template() {
    	setTitles(new HashMap<Language, String>());
		setBodies(new HashMap<Language, String>());
	}

	public Template(String id) {
		this();
		setId(id);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Map<Language, String> getTitles() {
		return titles;
	}

	public void setTitles(Map<Language, String> titles) {
		this.titles = titles;
	}

	public Map<Language, String> getBodies() {
		return bodies;
	}

	public void setBodies(Map<Language, String> bodies) {
		this.bodies = bodies;
	}

	public TemplateAccessType getTemplateAccessType() {
		return templateAccessType;
	}

	public void setTemplateAccessType(TemplateAccessType templateAccessType) {
		this.templateAccessType = templateAccessType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getBodies() == null) ? 0 : getBodies().hashCode());
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime
				* result
				+ ((getTemplateAccessType() == null) ? 0 : getTemplateAccessType()
						.hashCode());
		result = prime * result + ((getTitles() == null) ? 0 : getTitles().hashCode());
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
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		if (getBodies() == null) {
			if (other.getBodies() != null) {
				return false;
			}
		} else if (!getBodies().equals(other.getBodies())) {
			return false;
		}
		if (getTemplateAccessType() != other.getTemplateAccessType()) {
			return false;
		}
		if (getTitles() == null) {
			if (other.getTitles() != null) {
				return false;
			}
		} else if (!getTitles().equals(other.getTitles())) {
			return false;
		}
		return true;
	}

}

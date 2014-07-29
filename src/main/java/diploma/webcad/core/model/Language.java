package diploma.webcad.core.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Language implements Serializable, Comparable<Language> {

	private static final long serialVersionUID = -5434957227982138816L;

	@Id
	private String iso;
	
	private String name;

	public Language() {
		
	}
	
	public Language(String language, String name) {
		setIso(language);
		setName(name);
	}

	public String getIso() {
		return iso;
	}

	public void setIso(String language) {
		this.iso = language;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(Language o) {
		return iso.compareTo(o.iso);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getIso() == null) ? 0 : getIso().hashCode());
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
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
		Language other = (Language) obj;
		if (getIso() == null) {
			if (other.getIso() != null)
				return false;
		} else if (!getIso().equals(other.getIso()))
			return false;
		if (getName() == null) {
			if (other.getName() != null)
				return false;
		} else if (!getName().equals(other.getName()))
			return false;
		return true;
	}
	
}

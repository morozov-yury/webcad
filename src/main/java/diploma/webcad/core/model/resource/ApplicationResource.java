package diploma.webcad.core.model.resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import diploma.webcad.core.model.Language;

@Entity
@Table(name="resources")
public class ApplicationResource implements Serializable{

	private static final long serialVersionUID = 5260758137083224889L;
	
	private String id = null;
	private List<ApplicationResourceValue> langs;
	
	public ApplicationResource() {
		langs = new ArrayList<ApplicationResourceValue>();
	}
	
	public ApplicationResource(String id) {
		this();
		this.id = id;
	}
	
	@Id
	@Column(name="id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval=false, targetEntity=ApplicationResourceValue.class)
	public List<ApplicationResourceValue> getLangs() {
		return langs;
	}
	
	public void setLangs(List<ApplicationResourceValue> langs) {
		this.langs = langs;
	}
	
	@Transient
	public Map<Language, String> getStringLangMap() {
		Map<Language, String> result = new HashMap<Language, String>();
		for(ApplicationResourceValue applicationResourceValue: langs)
			result.put(applicationResourceValue.getLanguage(), applicationResourceValue.getValue());
		return result;
	}
	
	public void setStringLangMap(Map<Language, String> langMap) {
		this.langs.clear();
		for(Language lang: langMap.keySet()) {
			if(!StringUtils.isBlank(langMap.get(lang)))
				this.langs.add(new ApplicationResourceValue(lang, langMap.get(lang)));
		}
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicationResource other = (ApplicationResource) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public boolean containsLanguage(Language language) {
		for(ApplicationResourceValue applicationResourceValue: langs)
			if(applicationResourceValue.getLanguage().equals(language)) return true;
		return false;
	}

	@Transient
	public String getLangValue(Language language) {
		for(ApplicationResourceValue applicationResourceValue: langs)
			if(applicationResourceValue.getLanguage().equals(language)) return applicationResourceValue.getValue();
		return null;
	}
	
	public void setLangValue(Language language, String value) {
		for(ApplicationResourceValue applicationResourceValue: langs)
			if(applicationResourceValue.getLanguage().equals(language)) {
				applicationResourceValue.setValue(value);
				return;
			}
		ApplicationResourceValue applicationResourceValue = new ApplicationResourceValue(language, value);
		langs.add(applicationResourceValue);
	}
}

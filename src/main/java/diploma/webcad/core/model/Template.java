package diploma.webcad.core.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyColumn;

@Entity
public class Template implements Serializable {
	
	private static final long serialVersionUID = -9215483508114784752L;

    private String id;
    private Map<Language, String> titles;
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

	@Id
    @Column(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@ElementCollection(fetch = FetchType.EAGER)
	@JoinTable(name = "TemplatesTitles", joinColumns = @JoinColumn(name = "id"))
	@MapKeyColumn(name = "locale")
	@Column(name = "value", length = 1024)
	public Map<Language, String> getTitles() {
		return titles;
	}

	public void setTitles(Map<Language, String> titles) {
		this.titles = titles;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@JoinTable(name = "TemplatesBody", joinColumns = @JoinColumn(name = "id"))
	@MapKeyColumn(name = "locale")
	@Column(name = "value", length = 1024)
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

}

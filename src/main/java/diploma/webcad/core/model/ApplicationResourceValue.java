package diploma.webcad.core.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ApplicationResourceValue {
	private Long id;
	private String value;
	private Language language;
	
	public ApplicationResourceValue() {
	}
	
	public ApplicationResourceValue(Language lang, String value) {
		this.language = lang;
		this.value = value;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(columnDefinition="TEXT")
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=Language.class, cascade=CascadeType.PERSIST, optional=false)
	public Language getLanguage() {
		return language;
	}
	
	public void setLanguage(Language language) {
		this.language = language;
	}
	
	
}

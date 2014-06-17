package diploma.webcad.core.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import diploma.webcad.core.annotation.NotNull;

@Entity
@Table(name = "users")
public class User implements Serializable {
	private static final long serialVersionUID = -9215483528958346752L;

	private Long id;
	
	private String password;
	
	private String name;
	
	private Language language;
	
	private Date registrationDate;
	
	private UserRole userRole;
	
	public User() {
		registrationDate = new Date();
		userRole = UserRole.USER;
	}
	
	public User(@NotNull String name, @NotNull String password) {
		this();
		this.password = password;
		this.setName(name);
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "password")
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne
	public Language getLanguage() {
		return language;
	}
	
	public void setLanguage(Language language) {
		this.language = language;
	}
	
	@Column(name = "date")
	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	
	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}


}

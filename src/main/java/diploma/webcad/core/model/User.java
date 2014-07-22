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
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "users")
public class User implements Serializable {
	
	private static final long serialVersionUID = -9215483528958346752L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name", nullable=false, unique=true)
	private String name;
	
	@Column(name = "password", nullable=false)
	private String password;
	
	@ManyToOne(optional=false)
	private Language language;
	
	@Column(name = "date")
	private Date registrationDate;
	
	@Column(name = "userrole", nullable=false)
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
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public Language getLanguage() {
		return language;
	}
	
	public void setLanguage(Language language) {
		this.language = language;
	}
	
	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getLanguage() == null) ? 0 : getLanguage().hashCode());
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
		result = prime * result
				+ ((getPassword() == null) ? 0 : getPassword().hashCode());
		result = prime
				* result
				+ ((getRegistrationDate() == null) ? 0 : getRegistrationDate().hashCode());
		result = prime * result
				+ ((getUserRole() == null) ? 0 : getUserRole().hashCode());
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
		User other = (User) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		if (getLanguage() == null) {
			if (other.getLanguage() != null)
				return false;
		} else if (!getLanguage().equals(other.getLanguage()))
			return false;
		if (getName() == null) {
			if (other.getName() != null)
				return false;
		} else if (!getName().equals(other.getName()))
			return false;
		if (getPassword() == null) {
			if (other.getPassword() != null)
				return false;
		} else if (!getPassword().equals(other.getPassword()))
			return false;
		if (getRegistrationDate() == null) {
			if (other.getRegistrationDate() != null)
				return false;
		} else if (!getRegistrationDate().equals(other.getRegistrationDate()))
			return false;
		if (getUserRole() != other.getUserRole())
			return false;
		return true;
	}

}

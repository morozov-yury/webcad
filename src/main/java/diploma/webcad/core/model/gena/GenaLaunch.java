package diploma.webcad.core.model.gena;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import diploma.webcad.core.model.User;
import diploma.webcad.core.model.resource.FSResource;

@Entity
@Table(name = "genaLaunches")
public class GenaLaunch implements Serializable {

	private static final long serialVersionUID = 1900655406665158287L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class, optional = false)
	private User user;
	
	@Column(name = "creationDate", nullable = false, unique = false)
	private Date creationDate;
	
	@Column(name = "genaPlacement", nullable = false, unique = false)
	private GenaPlacement genaPlacement;
	
	@Column(name = "genaParams", nullable = false, unique = false, length = 64)
	private String genaParams;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = FSResource.class, optional = true)
	private FSResource inputResource;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = FSResource.class, optional = true)
	private FSResource resultResource;
	
	@Column(name = "genaResultStatus", nullable = false, unique = false)
	private GenaResultStatus genaResultStatus;
	
	public GenaLaunch () {
		setCreationDate(new Date());
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

	public GenaPlacement getGenaPlacement() {
		return genaPlacement;
	}

	public void setGenaPlacement(GenaPlacement genaPlacement) {
		this.genaPlacement = genaPlacement;
	}

	public String getGenaParams() {
		return genaParams;
	}

	public void setGenaParams(String genaParams) {
		this.genaParams = genaParams;
	}

	public FSResource getInputResource() {
		return inputResource;
	}

	public void setInputResource(FSResource inputData) {
		this.inputResource = inputData;
	}

	public FSResource getResultResource() {
		return resultResource;
	}

	public void setResultResource(FSResource resultData) {
		this.resultResource = resultData;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public GenaResultStatus getGenaResultStatus() {
		return genaResultStatus;
	}

	public void setGenaResultStatus(GenaResultStatus genaResultStatus) {
		this.genaResultStatus = genaResultStatus;
	}

}

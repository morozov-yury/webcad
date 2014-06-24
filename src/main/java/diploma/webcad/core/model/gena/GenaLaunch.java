package diploma.webcad.core.model.gena;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import diploma.webcad.core.model.User;
import diploma.webcad.core.model.resource.FileResource;

@Entity
@Table(name = "genaLaunches")
public class GenaLaunch implements Serializable {

	private static final long serialVersionUID = 1900655406665158287L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class, optional = false)
	private User user;
	
	@Column(name = "genaPlacement", nullable = false, unique = false)
	private GenaPlacement genaPlacement;
	
	@OneToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER, 
			orphanRemoval = true, targetEntity = GenaResult.class)
	private GenaResult genaResult;
	
	@Column(name = "genaParams", nullable = false, unique = false, length = 64)
	private String genaParams;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = FileResource.class, optional = true)
	private FileResource inputData;
	
	public GenaLaunch () {
		
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

	public GenaResult getGenaResult() {
		return genaResult;
	}

	public void setGenaResult(GenaResult genaResult) {
		this.genaResult = genaResult;
	}

	public String getGenaParams() {
		return genaParams;
	}

	public void setGenaParams(String genaParams) {
		this.genaParams = genaParams;
	}

	public FileResource getInputData() {
		return inputData;
	}

	public void setInputData(FileResource inputData) {
		this.inputData = inputData;
	}

}

package diploma.webcad.core.model.modelling;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.amazonaws.services.identitymanagement.model.User;

import diploma.webcad.core.model.resource.FSResource;

//@Entity
//@Table(name = "xtclshLaunches")
public class XtclshLaunch implements Serializable {

	private static final long serialVersionUID = 8643624603308987571L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(targetEntity = User.class, optional = false)
	private User user;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	private FSResource tclFile;
	
	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	private FSResource sourceFolder;
	
	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	private FSResource resultFolder;

}

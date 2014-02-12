package diploma.webcad.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * model for constants
 * @author Arktos
 */

@Entity
@Table(name = "constants")
public class ApplicationConstant implements Serializable{

	private static final long serialVersionUID = 3713200904592128122L;
	private String id;
	private String value;
	private String description;
	private ApplicationConstantType type;
	
	public ApplicationConstant(@NotNull String id, String value, String description, ApplicationConstantType type) {
		super();
		if(type == null) {
			type = ApplicationConstantType.getDefault();
		}
		this.id = id;
		this.value = value;
		this.description = description;
		this.type = type;
	}

	public ApplicationConstant(@NotNull String id, String value, String description) {
		this(id, value, description, ApplicationConstantType.getDefault());
	}

	public ApplicationConstant(@NotNull String id, String value) {
		this(id, value, null);
	}
	
	public ApplicationConstant(@NotNull String id) {
		this(id, null);
	}
	
	public ApplicationConstant() {
		this("");
	}

	@Id
	@Column(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "value")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	public ApplicationConstantType getType() {
		return type;
	}

	public void setType(ApplicationConstantType type) {
		this.type = type;
	}
}

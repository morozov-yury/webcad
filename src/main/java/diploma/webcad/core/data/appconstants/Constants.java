package diploma.webcad.core.data.appconstants;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "constants")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "constants", propOrder = { "constants" })
public class Constants {
	
	@XmlElement(name = "constant")
	private List<Constant> constants;

	@NotNull
	public List<Constant> getConstants() {
		if(constants == null) {
			constants = new ArrayList<Constant>();
		}
		return constants;
	}

	public void setConstants(List<Constant> constants) {
		this.constants = constants;
	}
}

package diploma.webcad.core.data.appconstants;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "constant", propOrder = { "key", "value", "description", "type" })
public class Constant {

	@XmlAttribute(name = "key")
	private String key;
	
	@XmlAttribute(name = "value")
	private String value;
	
	@XmlAttribute(name = "description")
	private String description;
	
	@XmlAttribute(name = "type")
	private String type;
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
}

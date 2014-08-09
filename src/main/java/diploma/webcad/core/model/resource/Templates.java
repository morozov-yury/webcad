package diploma.webcad.core.model.resource;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "templates")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "templates", propOrder = { })
public class Templates {
	
	@XmlElement(name = "template")
	private List<Template> templates;

	public List<Template> getTemplates() {
		return templates;
	}

	public void setTemplates(List<Template> templates) {
		this.templates = templates;
	}

}

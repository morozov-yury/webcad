package diploma.webcad.core.data.templates;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Alexandr
 */

@XmlRootElement(name="templates")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="templates")
public class XmlTemplates {
    
    @XmlElement(name="template")
    private List<XmlTemplate> items = new ArrayList<XmlTemplate>();

    public List<XmlTemplate> getItems() {
            return items;
    }

    public void setItems(List<XmlTemplate> items) {
            this.items = items;
    }
    
}

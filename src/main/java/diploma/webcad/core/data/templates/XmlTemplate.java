package diploma.webcad.core.data.templates;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Alexandr
 */
@XmlType(name="template")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlTemplate {
    
    @XmlAttribute(name="name")
    private String name;
    
    @XmlElement(name="locale")
    private List<XmlLocale> locales;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<XmlLocale> getLocales() {
        return locales;
    }

    public void setLocales(List<XmlLocale> locales) {
        this.locales = locales;
    }
    
}

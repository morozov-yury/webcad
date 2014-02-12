package diploma.webcad.core.data.templates;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Alexandr
 */
@XmlType(name="locale")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlLocale {
    
    @XmlAttribute
    private String language;
    
    @XmlElement(name="title")
    private String title;
    
    @XmlAnyElement(XmlLocaleBodyHandler.class)
    private String body;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

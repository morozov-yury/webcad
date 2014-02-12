package diploma.webcad.core.data.templates;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.annotation.DomHandler;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XmlLocaleBodyHandler implements DomHandler<String, StreamResult> {

	private static final String BODY_START_TAG = "<body>";
    private static final String BODY_END_TAG = "</body>";
    private static final String EMPTY_BODY_TAG = "<body/>";
 
    private StringWriter xmlWriter = new StringWriter(); 
 
    @Override
    public StreamResult createUnmarshaller(ValidationEventHandler errorHandler) {
        xmlWriter.getBuffer().setLength(0);
        return new StreamResult(xmlWriter);
    }
 
    @Override
    public String getElement(StreamResult rt) {
        String xml = rt.getWriter().toString();
        int emptyBodyTagIndex = xml.indexOf(EMPTY_BODY_TAG);
        if(emptyBodyTagIndex > 0) return "";
        int beginIndex = xml.indexOf(BODY_START_TAG) + BODY_START_TAG.length();
        int endIndex = xml.indexOf(BODY_END_TAG);
        return xml.substring(beginIndex, endIndex).trim();
    }
 
    @Override
    public Source marshal(String n, ValidationEventHandler errorHandler) {
        try {
            String str = (String) n;
            String xml = BODY_START_TAG + str.trim() + BODY_END_TAG;
            StringReader xmlReader = new StringReader(xml);
            return new StreamSource(xmlReader);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
	
}

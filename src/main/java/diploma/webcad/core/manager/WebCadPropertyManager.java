package diploma.webcad.core.manager;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class WebCadPropertyManager {

    @Autowired(required = false)
    private PropertiesFactoryBean quizProperties;
    
    public String getProperty (String propertyName) {
    	if (getQuizProperties() != null) {
    		return getQuizProperties().getProperty(propertyName);
    	} 
    	return null;
    }
    
    private Properties getQuizProperties() {
        try {
            return quizProperties.getObject();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    

}

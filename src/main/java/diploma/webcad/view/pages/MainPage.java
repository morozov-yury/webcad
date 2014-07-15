package diploma.webcad.view.pages;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ru.xpoft.vaadin.VaadinView;

import com.jcraft.jsch.JSchException;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

import diploma.webcad.core.service.SshService;
import diploma.webcad.view.WebCadUI;
import diploma.webcad.view.service.ViewFactory;

@org.springframework.stereotype.Component
@Scope("prototype")
@VaadinView(MainPage.NAME)
public class MainPage extends AbstractPage {

	private static final long serialVersionUID = 1762911330007195607L;

	public static final String NAME = "";

	private static Logger log = LoggerFactory.getLogger(MainPage.class);
	
	@Autowired
	private ViewFactory viewFactory;
	
	@Autowired
	private SshService sshService;

	public MainPage () {
		super("Welcome to WebCad");
		
		setContent(new Button("Test", new Button.ClickListener() {
			private static final long serialVersionUID = 2101274756535965486L;

			@Override
			public void buttonClick(ClickEvent event) {
				
				
				try {
					sshService.test();
				} catch (JSchException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				
				//WebCadUI.getCurrent().getNotificator().showError("Error", "Error");
			}
		}));
	}


	@Override
	public void enter() {
		
	}
	
}
 
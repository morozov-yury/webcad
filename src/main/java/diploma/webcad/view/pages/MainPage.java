package diploma.webcad.view.pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.core.dao.FileResourceDao;
import diploma.webcad.core.model.resource.FSResource;
import diploma.webcad.core.service.SshService;
import diploma.webcad.core.util.date.DateUtils;
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
	
	@Autowired
	private FileResourceDao fileResourceDao;

	public MainPage () {
		super("Welcome to WebCad");
	}


	@Override
	public void enter() {
		VerticalLayout content = new VerticalLayout();
		
		for (final FSResource resource : fileResourceDao.list()) {
			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(true);
			hl.addComponent(new Label(resource.getId().toString()));
			hl.addComponent(new Label(DateUtils.formatDateTime(resource.getCreationDate())));
			hl.addComponent(new Label(resource.getFsResourceType().toString()));
			hl.addComponent(new Label(resource.getPlacement().toString()));
			hl.addComponent(new Label(resource.getUser().getId().toString()));
			hl.addComponent(new Button("Transfer", new Button.ClickListener() {
				private static final long serialVersionUID = 2101274756535965486L;
				@Override
				public void buttonClick(ClickEvent event) {
					try {
						sshService.transferResToNeclus(resource);
					} catch (Exception e) {
						WebCadUI.getCurrent().getNotificator().showError("", e.getMessage());
						log.info(e.getMessage());
					}
				}
			}));
			content.addComponent(hl);
		}
		
		setContent(content);
	}
	
}
 
package diploma.webcad.view.client.component;


import org.hibernate.Session;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;

import diploma.webcad.core.service.SessionState;
import diploma.webcad.view.WebCadUI;

@JavaScript({"updatable_label.js"})
public abstract class UpdatableLabel extends AbstractJavaScriptComponent {

	private static final long serialVersionUID = -5021110068452141558L;

	private Thread thread;
	
	private boolean stopped = false;

	private SessionState sessionState;

	public UpdatableLabel(String text, final long inteval) {
		getState().text = text;
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!stopped) {
					Session openSession = sessionState.openSession();
			    	try {
			    			getState().text = getUpdatedText();
			    			Thread.sleep(inteval);	
			    	} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						sessionState.closeSession(openSession);;
					}
				}
			}
		});

	}

	@Override
	public void attach() {
		sessionState = WebCadUI.getCurrent().getSessionState();
		thread.start();
		stopped = false;
		super.attach();
	}

	@Override
	public void detach() {
		stopped = true;
		super.detach();
	}

	@Override
	protected UpdatableLabelState getState() {
		return (UpdatableLabelState) super.getState();
	}
	
	abstract public String getUpdatedText();

}

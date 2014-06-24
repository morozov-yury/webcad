package diploma.webcad.view.client.component;


import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;

import diploma.webcad.core.init.SpringContext;
import diploma.webcad.view.WebCadUI;

@JavaScript({"updatable_label.js"})
public abstract class UpdatableLabel extends AbstractJavaScriptComponent {

	private static final long serialVersionUID = -5021110068452141558L;
	
	private TransactionTemplate transactionTemplate;
	
	private Thread thread;
	
	private boolean stopped = false;

	public UpdatableLabel(String text, final long inteval) {
		getState().text = text;
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!stopped) {
					transactionTemplate.execute(new TransactionCallback<Void>() {
						@Override
						public Void doInTransaction(TransactionStatus status) {
							try {
					    		getState().text = getUpdatedText();
					    		Thread.sleep(inteval);
							} catch (RuntimeException e) {
								status.setRollbackOnly();
								throw e;
							} catch (InterruptedException e) {
								status.setRollbackOnly();
								e.printStackTrace();
							}
							return null;
						}
					});
				}
			}
		});

	}

	@Override
	public void attach() {
		SpringContext context = WebCadUI.getCurrent().getSessionState().getContext();
		transactionTemplate = context.getBean(TransactionTemplate.class);
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

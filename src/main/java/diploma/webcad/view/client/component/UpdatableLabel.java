package diploma.webcad.view.client.component;


import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;

@JavaScript({"updatable_label.js"})
public abstract class UpdatableLabel extends AbstractJavaScriptComponent {

	private static final long serialVersionUID = -5021110068452141558L;

	private Thread thread;
	
	private boolean stopped = false;

	public UpdatableLabel(String text, final long inteval) {
		getState().text = text;
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!stopped) {
					try {
						getState().text = getUpdatedText();
						Thread.sleep(inteval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	@Override
	public void attach() {
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

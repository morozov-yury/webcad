package diploma.webcad.view.pages;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import com.vaadin.data.Container;
import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import diploma.webcad.core.exception.RedirectException;
import diploma.webcad.view.tiles.AbstractContentTile;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
@VaadinView(Main.NAME)
public class Main extends AbstractContentTile {

	public static final String NAME = "";

	private static Logger log = LoggerFactory.getLogger(Main.class);

	private VerticalLayout mainVertLayout;

	public Main() {
		super();
		this.mainVertLayout = new VerticalLayout();
		this.mainVertLayout.setSpacing(true);
		setContent(this.mainVertLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		this.mainVertLayout.removeAllComponents();
		this.mainVertLayout.addComponent(new Label("Welcome to WebCad"));
		
		final TextField commandTextField = new TextField("Command");
		commandTextField.setValue("pwd");
		this.mainVertLayout.addComponent(commandTextField);
		
		Button testButton = new Button("Test buttom");
		testButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent arg0) {
				//Notification.show("Test notification", Type.TRAY_NOTIFICATION);
				
				JSch jsch = new JSch();
				try {
					Session session = jsch.getSession("miroshkin", "neclus.donntu.edu.ua", 22);
					UserInfo ui = new MyUserInfo();
					session.setUserInfo(ui);
					session.connect();
					Channel channel = session.openChannel("exec");
					((ChannelExec) channel).setCommand(commandTextField.getValue());
					// X Forwarding
					// channel.setXForwarding(true);

					// channel.setInputStream(System.in);
					channel.setInputStream(null);
					// channel.setOutputStream(System.out);

					// FileOutputStream fos=new FileOutputStream("/tmp/stderr");
					// ((ChannelExec)channel).setErrStream(fos);
					((ChannelExec) channel).setErrStream(System.err);

					InputStream in = channel.getInputStream();

					channel.connect();

					byte[] tmp = new byte[1024];
					while (true) {
						while (in.available() > 0) {
							int i = in.read(tmp, 0, 1024);
							if (i < 0) {
								break;
							}
							String message = new String(tmp, 0, i, "UTF-8");
							Notification.show(commandTextField.getValue() + ": " + message, Type.HUMANIZED_MESSAGE);
						}
						if (channel.isClosed()) {
							System.out.println("exit-status: " + channel.getExitStatus());
							break;
						}
						try {
							Thread.sleep(1000);
						} catch (Exception ee) {
							
						}
					}
					channel.disconnect();
					session.disconnect();

				} catch (JSchException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		mainVertLayout.addComponent(testButton);


	}

	@Override
	public boolean validate(ViewChangeEvent event) throws RedirectException {
		return true;
	}

	public class MyUserInfo implements UserInfo {

		public MyUserInfo() {
		}

		@Override
		public String getPassphrase() {
			log.info("");
			return null;
		}

		@Override
		public String getPassword() {
			return "1q2w3e";
		}

		@Override
		public boolean promptPassphrase(String arg0) {
			log.info(arg0);
			return true;
		}

		@Override
		public boolean promptPassword(String arg0) {
			log.info(arg0);
			return true;
		}

		@Override
		public boolean promptYesNo(String arg0) {
			log.info(arg0);
			return true;
		}

		@Override
		public void showMessage(String arg0) {
			log.info(arg0);
		}

	}

}

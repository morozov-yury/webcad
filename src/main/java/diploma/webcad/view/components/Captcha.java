package diploma.webcad.view.components;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;

import com.octo.captcha.service.image.ImageCaptchaService;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.core.init.SpringContext;
import diploma.webcad.core.service.ContentService;

@SuppressWarnings("serial")
public class Captcha extends VerticalLayout {

	private static final HashSet<String> ids = new HashSet<String>();
	
	@Autowired
	private ImageCaptchaService captchaService;

	private String id;
	private TextField userInput;
	private Image img;
	private String imageType;
	private Locale locale;
	final private static Random rand = new Random();

	private ContentService contentService;
	
	public Captcha(SpringContext helper) {
		this(helper, Locale.ENGLISH, "png");
	}
	
	public Captcha(SpringContext helper, Locale locale, String imageType) {
		this.imageType = imageType;
		this.locale = locale;
		this.captchaService = helper.getBean(ImageCaptchaService.class);
		this.contentService = helper.getBean(ContentService.class);
		
		userInput = new TextField();
		userInput.setWidth(100, Unit.PERCENTAGE);
		refresh();
		HorizontalLayout topLayout = new HorizontalLayout();
		
		Label capchaLabel = new Label(this.contentService.getAppResource(this, "signup.captcha"));
		capchaLabel.setStyleName("capcha");
		topLayout.addComponent(capchaLabel);
		topLayout.addComponent(img);
		img.setStyleName("capcha-image");
		Button refreshButton = new Button("\u27F2", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				refresh();
			}
		});
		refreshButton.setStyleName("link");
		topLayout.addComponent(refreshButton);
		addComponent(topLayout);
		addComponent(userInput);
		setWidth(100, Unit.PERCENTAGE);
	}
	
	public void refresh() {
		synchronized (ids) {
			ids.remove(id);
			id = Long.toString(rand.nextLong());
			while(ids.contains(id)) {
				id = Long.toString(rand.nextLong());
			}
			ids.add(id);
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedImage challengeImage = captchaService.getImageChallengeForID(id, locale);
		try {
			ImageIO.write(challengeImage, imageType, baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		final byte[] captchaBytes = baos.toByteArray();
		img = new Image("", new StreamResource(new StreamSource() {
			
			@Override
			public InputStream getStream() {
				return new ByteArrayInputStream(captchaBytes);
			}
		}, id));
		img.setWidth(100, Unit.PIXELS);
		img.setHeight(30, Unit.PIXELS);
		userInput.setValue("");
	}
	
	public boolean isValid() {
		String usrStr = (String) userInput.getValue();
		return captchaService.validateResponseForID(
				id, usrStr);
	}
	
	public boolean validate() {
		final boolean res = isValid();
		refresh();
		return res;
	}
	
}

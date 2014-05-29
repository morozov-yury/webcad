package diploma.webcad.core.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("session")
public class SessionInterlayer {

	private SessionState sessionState;
	
	private ContentService contentService;
	
	private RuntimeRegistrator runtimeRegistrator;

	public SessionState getSessionState() {
		return sessionState;
	}

	public void setSessionState(SessionState sessionState) {
		this.sessionState = sessionState;
	}

	public RuntimeRegistrator getRuntimeRegistrator() {
		return runtimeRegistrator;
	}

	public void setRuntimeRegistrator(RuntimeRegistrator runtimeRegistrator) {
		this.runtimeRegistrator = runtimeRegistrator;
	}

	public ContentService getContentManager() {
		return contentService;
	}

	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}

}

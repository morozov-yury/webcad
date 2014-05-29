package diploma.webcad.core.model.resource;

public enum AppCategoryPrefix {

	SERVICE_CONTEST_CATEGORY("s.c.c."),
	SERVICE_PUBLICATION_BLOG("s.p.b."),
	CONTENT_PAGE_CAPTION("cpg.c."),
	CONTENT_PAGE_BODY("cpg.b.");
	
	private String prefix ;

	private AppCategoryPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public String getPrefix() {
		return prefix;
	}
}
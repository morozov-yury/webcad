package diploma.webcad.core.genaconfigs;

public abstract class GenaParameters {
	
	private String sourceFilePath;

	public GenaParameters(String sourceFilePath) {
		this.setSourceFilePath(sourceFilePath);
	}

	public String getSourceFilePath() {
		return sourceFilePath;
	}

	public void setSourceFilePath(String sourceFilePath) {
		this.sourceFilePath = sourceFilePath;
	}
	
	public abstract String toString();

}

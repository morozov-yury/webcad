package diploma.webcad.core.genaconfigs;

public class MooreMealyParameters extends GenaParameters {

	private int promjTerms;
	private int codingVershGSA;

	public MooreMealyParameters(String sourceFilePath, int codingVershGSA, int promjTerms) {
		super(sourceFilePath);
		this.setCodingVershGSA(codingVershGSA);
		this.setPromjTerms(promjTerms);
	}

	public int getPromjTerms() {
		return promjTerms;
	}

	public void setPromjTerms(int promjTerms) {
		this.promjTerms = promjTerms;
	}

	public int getCodingVershGSA() {
		return codingVershGSA;
	}

	public void setCodingVershGSA(int codingVershGSA) {
		this.codingVershGSA = codingVershGSA;
	}

	@Override
	public String toString() {
		return super.getSourceFilePath() + " " + "-v " + this.promjTerms + " -t" + codingVershGSA;
	}

}

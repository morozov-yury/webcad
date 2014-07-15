package diploma.webcad.core.model;


public class ExecResult {

	private int exitStatus;
	
	private byte[] data;

	public ExecResult() {
		
	}

	public ExecResult(int exitStatus, byte[] data) {
		super();
		this.setExitStatus(exitStatus);
		this.setData(data);
	}

	public int getExitStatus() {
		return exitStatus;
	}

	public void setExitStatus(int exitStatus) {
		this.exitStatus = exitStatus;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ExecResult [exitStatus=" + exitStatus + ", data="
				+ new String(data) + "]";
	}

	
}

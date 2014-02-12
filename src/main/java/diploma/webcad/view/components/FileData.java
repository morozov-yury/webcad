package diploma.webcad.view.components;

public class FileData {

	private byte[] data;
	private String mimeType;
	private String name;
	private long length;
	
	public FileData(byte[] data, String mimeType, String name, long length) {
		super();
		this.data = data;
		this.mimeType = mimeType;
		this.name = name;
		this.length = length;
	}
	
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getLength() {
		return length;
	}
	public void setLength(long length) {
		this.length = length;
	}
	
	
}

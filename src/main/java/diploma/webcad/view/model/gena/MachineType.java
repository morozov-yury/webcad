package diploma.webcad.view.model.gena;

public enum MachineType {
	
	MOORE("Moore"),
	
	MEALY("Meely"),
	
	CM("Common memory"),
	
	CS("Code sharing");

	private String name;

	MachineType (String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}

package diploma.webcad.view.model.gena;

public abstract class GenaParam {

	private MachineType paramType;

	public GenaParam (MachineType paramType) {
		this.setMachineType(paramType);
		
	}

	public MachineType getMachineType () {
		return paramType;
	}

	public void setMachineType (MachineType paramType) {
		this.paramType = paramType;
	}

}

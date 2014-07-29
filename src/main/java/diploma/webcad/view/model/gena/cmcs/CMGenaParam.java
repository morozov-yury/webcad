package diploma.webcad.view.model.gena.cmcs;

import diploma.webcad.view.model.gena.MachineType;

public class CMGenaParam extends AddrMicroinstParam {

	public CMGenaParam() {
		super(MachineType.CM);
	}

	@Override
	public String toString() {
		return "--CM " + super.toString() + " -c 0";
	}

}

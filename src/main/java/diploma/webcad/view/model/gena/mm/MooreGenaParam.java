package diploma.webcad.view.model.gena.mm;

import diploma.webcad.view.model.gena.MachineType;

public class MooreGenaParam extends MMGenaParam {

	public MooreGenaParam() {
		super(MachineType.MOORE);
	}
	
	@Override
	public String toString() {
		return "--Moore " + super.toString();
	}

}

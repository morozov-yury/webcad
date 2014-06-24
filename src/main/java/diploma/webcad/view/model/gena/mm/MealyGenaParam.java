package diploma.webcad.view.model.gena.mm;

import diploma.webcad.view.model.gena.MachineType;

public class MealyGenaParam extends MMGenaParam {

	public MealyGenaParam() {
		super(MachineType.MEALY);
	}

	@Override
	public String toString() {
		return "--Mealy " + super.toString();
	}
	
}

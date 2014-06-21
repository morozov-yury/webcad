package diploma.webcad.view.components.gena.mm;

import diploma.webcad.view.model.gena.MachineType;
import diploma.webcad.view.model.gena.mm.MealyGenaParam;

public class MealyMParamSelector extends MMParamSelector {

	private static final long serialVersionUID = 6550298127403252511L;
	
	public MealyMParamSelector(MealyGenaParam genaParam) {
		super(genaParam);
	}
	
	@Override
	public MachineType getMachineType() {
		return MachineType.MEALY;
	}

}

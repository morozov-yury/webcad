package diploma.webcad.view.components.gena.mm;

import diploma.webcad.view.model.gena.MachineType;
import diploma.webcad.view.model.gena.mm.MooreGenaParam;

public class MooreMParamSelector extends MMParamSelector {

	private static final long serialVersionUID = -4660840766396481943L;
	
	public MooreMParamSelector(MooreGenaParam genaParam) {
		super(genaParam);
	}

	@Override
	public MachineType getMachineType() {
		return MachineType.MOORE;
	}

}

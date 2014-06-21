package diploma.webcad.view.components.gena;

import diploma.webcad.view.components.gena.cmcs.CMParamSelector;
import diploma.webcad.view.components.gena.cmcs.CSParamSelector;
import diploma.webcad.view.components.gena.mm.MealyMParamSelector;
import diploma.webcad.view.components.gena.mm.MooreMParamSelector;
import diploma.webcad.view.model.gena.GenaParam;
import diploma.webcad.view.model.gena.MachineType;
import diploma.webcad.view.model.gena.cmcs.CMGenaParam;
import diploma.webcad.view.model.gena.cmcs.CSGenaParam;
import diploma.webcad.view.model.gena.mm.MealyGenaParam;
import diploma.webcad.view.model.gena.mm.MooreGenaParam;

public class MachineParamsFactory {
	
	public static MachineParamsSelector getSelector (GenaParam genaParam) {
		switch (genaParam.getMachineType()) {
		case CM:
			return new CMParamSelector((CMGenaParam) genaParam);
		case CS:
			return new CSParamSelector((CSGenaParam) genaParam);
		case MEALY:
			return new MealyMParamSelector((MealyGenaParam) genaParam);
		case MOORE:
		default:
			return new MooreMParamSelector((MooreGenaParam) genaParam);
		}
	}
	
	public static MachineParamsSelector getSelector (MachineType machineType) {
		switch (machineType) {
		case CM:
			return new CMParamSelector(new CMGenaParam());
		case CS:
			return new CSParamSelector(new CSGenaParam());
		case MEALY:
			return new MealyMParamSelector(new MealyGenaParam());
		case MOORE:
		default:
			return new MooreMParamSelector(new MooreGenaParam());
		}
	}

}

package diploma.webcad.view.components.gena;

import diploma.webcad.view.components.gena.cmcs.CMParamSelector;
import diploma.webcad.view.components.gena.cmcs.CSParamSelector;
import diploma.webcad.view.components.gena.mm.MealyMParamSelector;
import diploma.webcad.view.components.gena.mm.MooreMParamSelector;
import diploma.webcad.view.model.gena.GenaParam;
import diploma.webcad.view.model.gena.MachineType;
import diploma.webcad.view.model.gena.cmcs.CMGenaParam;
import diploma.webcad.view.model.gena.cmcs.CSGenaParam;
import diploma.webcad.view.model.gena.mm.MMGenaParam;
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
	
	public static GenaParam getGenaParamByToken (String token) {
		if (token.startsWith("--Mealy") || token.startsWith("--Moore")) {
			return parseMMParams(token);
		}
		if (token.startsWith("--CM")) {
			return parseCMParams(token);
		}
		if (token.startsWith("--CS")) {
			return parseCSParams(token);
		}
		throw new IllegalStateException("Token [" + token + "] is inccorect.");
	}
	
	private static GenaParam parseMMParams (String tpken) {
		MMGenaParam mmGenaParam = null;
		
		if (tpken.startsWith("--Mealy")) {
			mmGenaParam = new MealyGenaParam();
		} else if (tpken.startsWith("--Moore")) {
			mmGenaParam = new MooreGenaParam();
		}

		return mmGenaParam;
	}
	
	private static GenaParam parseCMParams (String tpken) {
		MMGenaParam mmGenaParam = null;


		return mmGenaParam;
	}
	
	private static GenaParam parseCSParams (String tpken) {
		MMGenaParam mmGenaParam = null;


		return mmGenaParam;
	}

}

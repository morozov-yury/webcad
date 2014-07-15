package diploma.webcad.view.components.gena;

import org.mortbay.log.Log;

import diploma.webcad.view.components.gena.cmcs.CMParamSelector;
import diploma.webcad.view.components.gena.cmcs.CSParamSelector;
import diploma.webcad.view.components.gena.mm.MealyMParamSelector;
import diploma.webcad.view.components.gena.mm.MooreMParamSelector;
import diploma.webcad.view.model.gena.GenaParam;
import diploma.webcad.view.model.gena.MachineType;
import diploma.webcad.view.model.gena.cmcs.AddrMicroinstParam;
import diploma.webcad.view.model.gena.cmcs.AddrMicroinstParam.EncodingMC;
import diploma.webcad.view.model.gena.cmcs.CMGenaParam;
import diploma.webcad.view.model.gena.cmcs.CSGenaParam;
import diploma.webcad.view.model.gena.cmcs.CSGenaParam.CodePlacement;
import diploma.webcad.view.model.gena.cmcs.CSGenaParam.ExtCodeConverter;
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
	
	private static GenaParam parseMMParams (String token) {
		MMGenaParam mmGenaParam = null;
		
		if (token.startsWith("--Mealy")) {
			mmGenaParam = new MealyGenaParam();
		} else if (token.startsWith("--Moore")) {
			mmGenaParam = new MooreGenaParam();
		}
		
		token = token.replaceFirst("\\-\\-Mealy", "");
		token = token.replaceFirst("\\-\\-Moore", "");
		token = token.replaceAll("\\s+", " ").trim();
		String[] strings = token.split(" ");

		for (int i = 0; i < strings.length; i = i + 2) {
			String s = strings[i];
			if (s.equals("-v")) {
				mmGenaParam.setCodVerGSA(Integer.valueOf(strings[i + 1]));
			}
			if (s.equals("-t")) {
				mmGenaParam.setIspPromTermov(Integer.valueOf(strings[i + 1]));
			}
		}

		return mmGenaParam;
	}
	
	private static GenaParam parseCMParams (String token) {
		CMGenaParam cmGenaParam = new CMGenaParam();
		
		cmGenaParam = (CMGenaParam) paseAddrMicroinstParam(token, cmGenaParam);

		return cmGenaParam;
	}
	
	private static GenaParam parseCSParams (String token) {
		CSGenaParam csGenaParam = new CSGenaParam();

		csGenaParam = (CSGenaParam) paseAddrMicroinstParam(token, csGenaParam);

		token = token.replaceFirst("\\-\\-CS", "");
		token = token.replaceAll("\\s+", " ").trim();
		String[] strings = token.split(" ");
		
		for (int i = 0; i < strings.length; i = i + 2) {
			String s = strings[i];
			if (s.equals("-e")) {
				String e = strings[i + 1];
				if (e.equals("0")) {
					csGenaParam.setElementarizatsiya(false);
				} else if (e.equals("1")) {
					csGenaParam.setElementarizatsiya(true);
				}
			}
			if (s.equals("-p")) {
				int p = Integer.valueOf(strings[i + 1]);
				Log.info("{} {}", p, CodePlacement.THIS_MO.ordinal());
				if (p == CodePlacement.THIS_MO.ordinal()) {
					csGenaParam.setCodePlacemet(CodePlacement.THIS_MO);
				} else if (p == CodePlacement.NEXT_MO.ordinal()) {
					csGenaParam.setCodePlacemet(CodePlacement.NEXT_MO);
				}
			}
			if (s.equals("-c")) {
				String c = strings[i + 1];
				if (c.equals("0")) {
					csGenaParam.setExtCodeConverter(ExtCodeConverter.MUST_NOT);
				} else if (c.equals("1")) {
					csGenaParam.setExtCodeConverter(ExtCodeConverter.IN_NEED);
				} else if (c.equals("2")) {
					csGenaParam.setExtCodeConverter(ExtCodeConverter.NECESSARY);
				}
			}
		}

		return csGenaParam;
	}
	
	private static AddrMicroinstParam paseAddrMicroinstParam (String token, 
			AddrMicroinstParam addrMicroinstParam) {

		token = token.replaceFirst("\\-\\-CM", "");
		token = token.replaceFirst("\\-\\-CS", "");
		token = token.replaceAll("\\s+", " ").trim();
		String[] strings = token.split(" ");
		
		for (int i = 0; i < strings.length; i = i + 2) {
			String s = strings[i];
			if (s.equals("-x")) {
				String x = strings[i + 1];
				if (x.equals("0")) {
					addrMicroinstParam.setCodLogConditions(false);
				} else if (x.endsWith("1")) {
					addrMicroinstParam.setCodLogConditions(true);
				}
			}
			if (s.equals("-y")) {
				String y = strings[i + 1];
				if (y.equals("0")) {
					addrMicroinstParam.setEncodingMC(EncodingMC.UNITARY);
				} else if (y.equals("1")) {
					addrMicroinstParam.setEncodingMC(EncodingMC.COMPATIBLE);
				} else if (y.equals("2")) {
					addrMicroinstParam.setEncodingMC(EncodingMC.MAXIMUM);
				}
			}
			if (s.equals("-w")) {
				String w = strings[i + 1];
				addrMicroinstParam.setWordWodthRestriction(Integer.valueOf(w));
			}
		}
		
		return addrMicroinstParam;
	}

}

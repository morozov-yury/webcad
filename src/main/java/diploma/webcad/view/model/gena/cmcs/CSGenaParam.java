package diploma.webcad.view.model.gena.cmcs;

import diploma.webcad.view.model.gena.MachineType;

public class CSGenaParam extends AddrMicroinstParam {
	
	public enum CodePlacement {
		
		THIS_MO("This microoperation"),
		
		NEXT_MO("Next microoperation");
		
		String name;
		
		private CodePlacement(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return this.name;
		}
	}
	
	public enum ExtCodeConverter {
		
		MUST_NOT("Must not"),
		
		IN_NEED("In need"),
		
		NECESSARY("Necessary");
		
		private String name;

		private ExtCodeConverter(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return this.name;
		}
		
	}
	
	private Boolean elementarizatsiya;
	
	private CodePlacement codePlacemet;
	
	private ExtCodeConverter extCodeConverter;

	public CSGenaParam() {
		super(MachineType.CS);
	}

	public Boolean getElementarizatsiya() {
		return elementarizatsiya;
	}

	public void setElementarizatsiya(Boolean elementarizatsiya) {
		this.elementarizatsiya = elementarizatsiya;
	}

	public CodePlacement getCodePlacemet() {
		return codePlacemet;
	}

	public void setCodePlacemet(CodePlacement codePlacemet) {
		this.codePlacemet = codePlacemet;
	}

	public ExtCodeConverter getExtCodeConverter() {
		return extCodeConverter;
	}

	public void setExtCodeConverter(ExtCodeConverter extCodeConverter) {
		this.extCodeConverter = extCodeConverter;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder().append("--CS ").append(super.toString());
		if (elementarizatsiya != null) {
			sb.append("-e").append((elementarizatsiya) ? " 1 " : " 0 ");
		}
		if (codePlacemet != null) {
			switch (codePlacemet) {
			case NEXT_MO:
				sb.append("-p 1 ");
				break;
			case THIS_MO:
				sb.append("-p 0 ");
				break;
			}
		}
		if (extCodeConverter != null) {
			switch (extCodeConverter) {
			case IN_NEED:
				sb.append("-c 1 ");
				break;
			case MUST_NOT:
				sb.append("-c 0 ");
				break;
			case NECESSARY:
				sb.append("-c 2 ");
				break;
			}
		}
		return sb.toString();
	}
	
}

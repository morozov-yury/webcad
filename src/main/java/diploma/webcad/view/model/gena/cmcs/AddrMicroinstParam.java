package diploma.webcad.view.model.gena.cmcs;

import javax.validation.constraints.Min;

import diploma.webcad.view.model.gena.GenaParam;
import diploma.webcad.view.model.gena.MachineType;

public class AddrMicroinstParam extends GenaParam {
	
	public enum EncodingMC {
		
		UNITARY("Unitary"),
		
		COMPATIBLE("Compatible"),
		
		MAXIMUM("Maximum");
		
		private String name;
		
		EncodingMC (String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}

	}
	
	private Boolean codLogConditions;
	
	private EncodingMC encodingMC;
	
	@Min(0)
	private Integer wordWodthRestriction;

	public AddrMicroinstParam(MachineType paramType) {
		super(paramType);
	}

	public Boolean getCodLogConditions() {
		return codLogConditions;
	}

	public void setCodLogConditions(Boolean codLogConditions) {
		this.codLogConditions = codLogConditions;
	}

	public EncodingMC getEncodingMC() {
		return encodingMC;
	}

	public void setEncodingMC(EncodingMC encodingMC) {
		this.encodingMC = encodingMC;
	}

	public Integer getWordWodthRestriction() {
		return wordWodthRestriction;
	}

	public void setWordWodthRestriction(Integer wordWodthRestriction) {
		this.wordWodthRestriction = wordWodthRestriction;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (codLogConditions != null) {
			sb.append("-x ").append((codLogConditions) ? " 1 " : " 0 ");
		}
		if (encodingMC != null) {
			switch (encodingMC) {
			case COMPATIBLE:
				sb.append("-y 1 ");
				break;
			case MAXIMUM:
				sb.append("-y 2 ");
				break;
			case UNITARY:
				sb.append("-y 3 ");
				break;
			}
		}
		if (wordWodthRestriction != null) {
			sb.append("-w ").append(wordWodthRestriction).append(" ");
		}
		return sb.toString();
	}

}

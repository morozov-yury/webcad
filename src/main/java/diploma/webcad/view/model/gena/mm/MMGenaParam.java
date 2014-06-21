package diploma.webcad.view.model.gena.mm;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import diploma.webcad.view.model.gena.GenaParam;
import diploma.webcad.view.model.gena.MachineType;

public abstract class MMGenaParam extends GenaParam {
	
	@Min(0) 
	@Max(3)
	private Integer codVerGSA;
	
	@Min(0) 
	@Max(1)
	private Integer ispPromTermov;

	public MMGenaParam(MachineType paramType) {
		super(paramType);
	}

	public Integer getCodVerGSA() {
		return codVerGSA;
	}

	public void setCodVerGSA(Integer codVerGSA) {
		this.codVerGSA = codVerGSA;
	}

	public Integer getIspPromTermov() {
		return ispPromTermov;
	}

	public void setIspPromTermov(Integer ispPromTermov) {
		this.ispPromTermov = ispPromTermov;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		//.append(codVerGSA).append(" ").append(ispPromTermov);
		if (codVerGSA != null) {
			sb.append("-v ").append(codVerGSA).append(" ");
		}
		if (ispPromTermov != null) {
			sb.append("-t ").append(ispPromTermov).append(" ");
		}
		return sb.toString();
	}
	
	

}

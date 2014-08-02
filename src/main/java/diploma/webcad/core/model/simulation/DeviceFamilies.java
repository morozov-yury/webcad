package diploma.webcad.core.model.modelling;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "families")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "families", propOrder = { })
public class DeviceFamilies {
	
	@XmlElement(name = "family")
	private List<DeviceFamily> deviceFamilies;
	
	public DeviceFamilies () {
		setDeviceFamilies(new ArrayList<DeviceFamily>());
	}

	public List<DeviceFamily> getDeviceFamilies() {
		return deviceFamilies;
	}

	public void setDeviceFamilies(List<DeviceFamily> deviceFamilies) {
		this.deviceFamilies = deviceFamilies;
	}

}

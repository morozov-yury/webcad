package diploma.webcad.view.components.gena;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import diploma.webcad.view.model.gena.GenaParam;
import diploma.webcad.view.model.gena.MachineType;
import diploma.webcad.view.model.gena.cmcs.AddrMicroinstParam.EncodingMC;

public abstract class MachineParamsSelector extends VerticalLayout implements Component {

	private static final long serialVersionUID = -8421014793833784947L;

	public abstract MachineType getMachineType ();
	
	public abstract GenaParam getGenaParam ();

	protected BeanItemContainer<EncodingMC> getEncodingMCContainer () {
		BeanItemContainer<EncodingMC> types = new BeanItemContainer<EncodingMC>(
				EncodingMC.class);
		types.addBean(EncodingMC.UNITARY);
		types.addBean(EncodingMC.COMPATIBLE);
		types.addBean(EncodingMC.MAXIMUM);
		return types;
	}
	
}

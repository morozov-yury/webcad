package diploma.webcad.trigger;

import java.io.Serializable;

public interface TriggerJob extends Serializable {

	public void performTriggerLogic() throws Exception;

}

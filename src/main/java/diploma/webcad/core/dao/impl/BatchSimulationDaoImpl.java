package diploma.webcad.core.dao.impl;

import org.springframework.stereotype.Repository;

import diploma.webcad.core.dao.BatchSimulationDao;
import diploma.webcad.core.model.simulation.BatchSimulation;

@Repository
public class BatchSimulationDaoImpl extends BaseDaoImpl<BatchSimulation, Long> implements
		BatchSimulationDao {

	public BatchSimulationDaoImpl() {
		super(BatchSimulation.class);
	}

}

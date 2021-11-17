package ci.gouv.dgbf.system.cloture.server.impl.business;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.cyk.utility.business.server.AbstractSpecificBusinessImpl;

import ci.gouv.dgbf.system.cloture.server.api.business.OperationBusiness;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Operation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationPersistence;

@ApplicationScoped
public class OperationBusinessImpl extends AbstractSpecificBusinessImpl<Operation> implements OperationBusiness,Serializable {

	@Inject OperationPersistence operationPersistence;

	@Override @Transactional
	public void execute(String identifier, String trigger) {
		operationPersistence.executeProcedure(identifier);
	}
	
}
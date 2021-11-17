package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.cyk.utility.persistence.server.hibernate.annotation.Hibernate;
import org.cyk.utility.persistence.server.procedure.ProcedureExecutor;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Operation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationPersistence;

@ApplicationScoped
public class OperationPersistenceImpl extends org.cyk.quarkus.extension.hibernate.orm.AbstractSpecificPersistenceImpl<Operation>  implements OperationPersistence,Serializable {

	@Inject @Hibernate ProcedureExecutor procedureExecutor;
	
	public OperationPersistenceImpl() {
		entityClass = Operation.class;
		entityImplClass = OperationImpl.class;
	}

	@Override
	public void executeProcedure(String name) {
		procedureExecutor.execute(OperationImpl.STORED_PROCEDURE_QUERY_PROCEDURE_NAME_EXECUTE_PROCEDURE
				, OperationImpl.STORED_PROCEDURE_QUERY_PARAMETER_NAME_PROCEDURE_NAME,name
			);
	}
}
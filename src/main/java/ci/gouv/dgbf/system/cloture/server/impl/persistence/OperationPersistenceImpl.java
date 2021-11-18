package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.cyk.utility.persistence.server.hibernate.annotation.Hibernate;
import org.cyk.utility.persistence.server.procedure.ProcedureExecutor;
import org.cyk.utility.persistence.server.procedure.ProcedureExecutorArguments;

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
	public String executeProcedure(String name) {
		ProcedureExecutorArguments arguments = new ProcedureExecutorArguments();
		arguments.setName(OperationImpl.STORED_PROCEDURE_QUERY_PROCEDURE_NAME_EXECUTE_PROCEDURE);
		arguments.setParameters(Map.of(OperationImpl.STORED_PROCEDURE_QUERY_PARAMETER_NAME_PROCEDURE_NAME,name));
		//String resultIdentifier = "";
		//arguments.setParameters(Map.of(OperationImpl.STORED_PROCEDURE_QUERY_PARAMETER_NAME_RESULT_IDENTIFIER,resultIdentifier));
		procedureExecutor.execute(arguments);
		//System.out.println("OperationPersistenceImpl.executeProcedure() ::: "+resultIdentifier);
		return null;
	}
}
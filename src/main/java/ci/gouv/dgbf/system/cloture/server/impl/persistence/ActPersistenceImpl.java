package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.cyk.utility.persistence.server.hibernate.annotation.Hibernate;
import org.cyk.utility.persistence.server.procedure.ProcedureExecutor;
import org.cyk.utility.persistence.server.procedure.ProcedureExecutorArguments;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Act;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActPersistence;

@ApplicationScoped
public class ActPersistenceImpl extends org.cyk.quarkus.extension.hibernate.orm.AbstractSpecificPersistenceImpl<Act>  implements ActPersistence,Serializable {

	@Inject @Hibernate ProcedureExecutor procedureExecutor;
	
	public ActPersistenceImpl() {
		entityClass = Act.class;
		entityImplClass = ActImpl.class;
	}
	
	@Override
	public void lock(String identifier) {
		ProcedureExecutorArguments arguments = new ProcedureExecutorArguments();
		arguments.setName(ActImpl.STORED_PROCEDURE_QUERY_PROCEDURE_NAME_LOCK);
		arguments.setParameters(Map.of(ActImpl.STORED_PROCEDURE_QUERY_PARAMETER_NAME_IDENTIFIER,identifier));
		procedureExecutor.execute(arguments);
	}
	
	@Override
	public void unlock(String identifier) {
		ProcedureExecutorArguments arguments = new ProcedureExecutorArguments();
		arguments.setName(ActImpl.STORED_PROCEDURE_QUERY_PROCEDURE_NAME_UNLOCK);
		arguments.setParameters(Map.of(ActImpl.STORED_PROCEDURE_QUERY_PARAMETER_NAME_IDENTIFIER,identifier));
		procedureExecutor.execute(arguments);
	}
}
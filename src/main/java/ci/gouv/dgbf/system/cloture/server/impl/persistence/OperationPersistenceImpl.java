package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Operation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationPersistence;

@ApplicationScoped
public class OperationPersistenceImpl extends org.cyk.quarkus.extension.hibernate.orm.AbstractSpecificPersistenceImpl<Operation>  implements OperationPersistence,Serializable {

	public OperationPersistenceImpl() {
		entityClass = Operation.class;
		entityImplClass = OperationImpl.class;
	}
	
}
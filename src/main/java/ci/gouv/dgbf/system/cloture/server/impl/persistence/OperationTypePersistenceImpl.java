package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationType;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationTypePersistence;

@ApplicationScoped
public class OperationTypePersistenceImpl extends org.cyk.quarkus.extension.hibernate.orm.AbstractSpecificPersistenceImpl<OperationType>  implements OperationTypePersistence,Serializable {

	public OperationTypePersistenceImpl() {
		entityClass = OperationType.class;
		entityImplClass = OperationTypeImpl.class;
	}
	
}
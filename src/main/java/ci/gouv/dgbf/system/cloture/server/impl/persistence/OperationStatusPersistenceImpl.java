package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationStatus;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationStatusPersistence;

@ApplicationScoped
public class OperationStatusPersistenceImpl extends org.cyk.quarkus.extension.hibernate.orm.AbstractSpecificPersistenceImpl<OperationStatus>  implements OperationStatusPersistence,Serializable {

	public OperationStatusPersistenceImpl() {
		entityClass = OperationStatus.class;
		entityImplClass = OperationStatusImpl.class;
	}
	
}
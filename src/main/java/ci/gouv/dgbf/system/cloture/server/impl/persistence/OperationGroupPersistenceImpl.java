package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationGroup;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationGroupPersistence;

@ApplicationScoped
public class OperationGroupPersistenceImpl extends org.cyk.quarkus.extension.hibernate.orm.AbstractSpecificPersistenceImpl<OperationGroup>  implements OperationGroupPersistence,Serializable {

	public OperationGroupPersistenceImpl() {
		entityClass = OperationGroup.class;
		entityImplClass = OperationGroupImpl.class;
	}
}
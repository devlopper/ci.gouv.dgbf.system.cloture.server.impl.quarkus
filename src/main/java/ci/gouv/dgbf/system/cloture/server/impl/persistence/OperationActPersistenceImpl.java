package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationAct;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationActPersistence;

@ApplicationScoped
public class OperationActPersistenceImpl extends org.cyk.quarkus.extension.hibernate.orm.AbstractSpecificPersistenceImpl<OperationAct>  implements OperationActPersistence,Serializable {

	public OperationActPersistenceImpl() {
		entityClass = OperationAct.class;
		entityImplClass = OperationActImpl.class;
	}
	
}
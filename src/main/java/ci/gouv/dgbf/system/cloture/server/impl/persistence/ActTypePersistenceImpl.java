package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import ci.gouv.dgbf.system.cloture.server.api.persistence.ActType;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActTypePersistence;

@ApplicationScoped
public class ActTypePersistenceImpl extends org.cyk.quarkus.extension.hibernate.orm.AbstractSpecificPersistenceImpl<ActType>  implements ActTypePersistence,Serializable {

	public ActTypePersistenceImpl() {
		entityClass = ActType.class;
		entityImplClass = ActTypeImpl.class;
	}
	
}
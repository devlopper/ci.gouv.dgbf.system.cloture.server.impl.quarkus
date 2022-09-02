package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Act;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActPersistence;

@ApplicationScoped
public class ActPersistenceImpl extends org.cyk.quarkus.extension.hibernate.orm.AbstractSpecificPersistenceImpl<Act>  implements ActPersistence,Serializable {

	public ActPersistenceImpl() {
		entityClass = Act.class;
		entityImplClass = ActImpl.class;
	}
}
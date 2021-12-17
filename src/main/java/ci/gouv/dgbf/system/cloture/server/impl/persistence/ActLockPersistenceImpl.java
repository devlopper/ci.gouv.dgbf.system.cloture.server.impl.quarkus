package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import ci.gouv.dgbf.system.cloture.server.api.persistence.ActLock;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActLockPersistence;

@ApplicationScoped
public class ActLockPersistenceImpl extends org.cyk.quarkus.extension.hibernate.orm.AbstractSpecificPersistenceImpl<ActLock>  implements ActLockPersistence,Serializable {

	@Inject EntityManager entityManager;
	
	public ActLockPersistenceImpl() {
		entityClass = ActLock.class;
		entityImplClass = ActLockImpl.class;
	}

	@Override
	public Collection<Object[]> readWhereEnabledIsTrueByActIdentifiersForOperation(Collection<String> actsIdentifiers) {
		return entityManager.createQuery(String.format("SELECT t.%s,t.%s,t.%s,t.%s FROM %s t WHERE t.enabled IS TRUE AND t.actIdentifier IN :actsIdentifiers"
				,ActLockImpl.FIELD_ACT_IDENTIFIER,ActLockImpl.FIELD_ACT_REFERENCE,ActLockImpl.FIELD_ACT_TYPE,ActLockImpl.FIELD_LOCK_TYPE,ActLockImpl.ENTITY_NAME))
				.setParameter("actsIdentifiers", actsIdentifiers)
				.getResultList();
	}
}
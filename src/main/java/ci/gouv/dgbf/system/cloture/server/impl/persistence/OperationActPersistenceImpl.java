package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;

import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationAct;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationActPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Parameters;

@ApplicationScoped
public class OperationActPersistenceImpl extends org.cyk.quarkus.extension.hibernate.orm.AbstractSpecificPersistenceImpl<OperationAct>  implements OperationActPersistence,Serializable {

	@Inject EntityManager entityManager;
	
	public OperationActPersistenceImpl() {
		entityClass = OperationAct.class;
		entityImplClass = OperationActImpl.class;
	}

	@Override
	public Collection<OperationAct> readByOperationIdentifierByNotActsIdentifiers(String operationIdentifier,Collection<String> actsIdentifiers) {
		if(StringHelper.isBlank(operationIdentifier) || CollectionHelper.isEmpty(actsIdentifiers))
			return null;
		return CollectionHelper.cast(OperationAct.class, entityManager.createNamedQuery(OperationActImpl.QUERY_READ_BY_OPERATION_IDENTIFIER_BY_NOT_ACTS_IDENTIFIERS, OperationActImpl.class)
				.setParameter(Parameters.OPERATION_IDENTIFIER, operationIdentifier).setParameter(Parameters.ACTS_IDENTIFIERS, actsIdentifiers)
				.getResultList());
	}
	
	@Override
	public Collection<OperationAct> readByOperationIdentifier(String operationIdentifier) {
		if(StringHelper.isBlank(operationIdentifier))
			return null;
		return CollectionHelper.cast(OperationAct.class, entityManager.createNamedQuery(OperationActImpl.QUERY_READ_BY_OPERATION_IDENTIFIER, OperationActImpl.class)
				.setParameter(Parameters.OPERATION_IDENTIFIER, operationIdentifier)
				.getResultList());
	}
	
}
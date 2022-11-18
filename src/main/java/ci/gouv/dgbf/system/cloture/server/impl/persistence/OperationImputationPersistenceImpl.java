package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;

import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationImputation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationImputationPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Parameters;

@ApplicationScoped
public class OperationImputationPersistenceImpl extends org.cyk.quarkus.extension.hibernate.orm.AbstractSpecificPersistenceImpl<OperationImputation>  implements OperationImputationPersistence,Serializable {

	@Inject EntityManager entityManager;
	
	public OperationImputationPersistenceImpl() {
		entityClass = OperationImputation.class;
		entityImplClass = OperationImputationImpl.class;
	}

	@Override
	public Collection<OperationImputation> readByOperationIdentifierByNotImputationsIdentifiers(String operationIdentifier,Collection<String> imputationsIdentifiers) {
		if(StringHelper.isBlank(operationIdentifier) || CollectionHelper.isEmpty(imputationsIdentifiers))
			return null;
		return CollectionHelper.cast(OperationImputation.class, entityManager.createNamedQuery(OperationImputationImpl.QUERY_READ_BY_OPERATION_IDENTIFIER_BY_NOT_IMPUTATIONS_IDENTIFIERS, OperationImputationImpl.class)
				.setParameter(Parameters.OPERATION_IDENTIFIER, operationIdentifier).setParameter(Parameters.ACTS_IDENTIFIERS, imputationsIdentifiers)
				.getResultList());
	}
	
	@Override
	public Collection<OperationImputation> readByOperationIdentifier(String operationIdentifier) {
		if(StringHelper.isBlank(operationIdentifier))
			return null;
		return CollectionHelper.cast(OperationImputation.class, entityManager.createNamedQuery(OperationImputationImpl.QUERY_READ_BY_OPERATION_IDENTIFIER, OperationImputationImpl.class)
				.setParameter(Parameters.OPERATION_IDENTIFIER, operationIdentifier)
				.getResultList());
	}
	
}
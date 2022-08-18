package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.persistence.EntityManagerGetter;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Act;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Parameters;

@ApplicationScoped
public class ActPersistenceImpl extends org.cyk.quarkus.extension.hibernate.orm.AbstractSpecificPersistenceImpl<Act>  implements ActPersistence,Serializable {

	@Inject EntityManager entityManager;
	
	public ActPersistenceImpl() {
		entityClass = Act.class;
		entityImplClass = ActImpl.class;
	}
	
	@Override
	public Collection<String> readIdentifiersWhereNotExistByOperationIdentifier(String operationIdentifier) {
		if(StringHelper.isBlank(operationIdentifier))
			return null;
		return entityManager.createNamedQuery(ActImpl.QUERY_READ_IDENTIFIERS_WHERE_NOT_EXIST_BY_OPERATION_IDENTIFIER, String.class).setParameter(Parameters.OPERATION_IDENTIFIER, operationIdentifier).getResultList();
	}
	
	@Override
	public void lock(String identifier,String lockType,String targetTable, EntityManager entityManager) {
		
	}
	
	@Override
	public void lock(String identifier, String lockType, String targetTable) {
		lock(identifier, lockType, targetTable, EntityManagerGetter.getInstance().get());
	}
	
	@Override
	public void unlock(String identifier, String lockType, String targetTable, EntityManager entityManager) {
		
	}
	
	@Override
	public void unlock(String identifier,String lockType,String targetTable) {
		unlock(identifier, lockType, targetTable, EntityManagerGetter.getInstance().get());
	}
}
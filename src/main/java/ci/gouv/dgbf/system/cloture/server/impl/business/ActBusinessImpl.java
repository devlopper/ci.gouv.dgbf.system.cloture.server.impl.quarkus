package ci.gouv.dgbf.system.cloture.server.impl.business;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.business.RequestException;
import org.cyk.utility.business.server.AbstractSpecificBusinessImpl;
import org.cyk.utility.persistence.EntityManagerGetter;

import ci.gouv.dgbf.system.cloture.server.api.business.ActBusiness;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Act;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActLockPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActOperationType;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActPersistence;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ActOperationImpl;

@ApplicationScoped
public class ActBusinessImpl extends AbstractSpecificBusinessImpl<Act> implements ActBusiness,Serializable {

	@Inject ActPersistence actPersistence;
	@Inject ActLockPersistence actLockPersistence;
	
	private Integer operate(Collection<String> identifiers,ActOperationType operation, String trigger,Boolean processedIgnorable) {
		ValidatorImpl.Act.validate(identifiers, operation,trigger,processedIgnorable);
		Collection<Object[]> locks = actLockPersistence.readWhereEnabledIsTrueByActIdentifiersForOperation(identifiers);
		if(CollectionHelper.isEmpty(locks) && !Boolean.TRUE.equals(processedIgnorable))
			throw new RequestException(String.format("Aucun verrou trouvé pour les actes suivants : %s",identifiers));
		Collection<Object[]> referenceOrTargetTableIsNullArrays = locks.stream().filter(array -> StringHelper.isBlank((String)array[1]) || array[2] == null || StringHelper.isBlank((String)array[3])).collect(Collectors.toList());
		if(CollectionHelper.isNotEmpty(referenceOrTargetTableIsNullArrays))
			throw new RequestException(String.format("La référence , le type de verrou et la table cible des actes suivants sont obligatoire : %s",referenceOrTargetTableIsNullArrays.stream().map(array -> (String)array[0])
					.collect(Collectors.joining(","))));
		EntityManager entityManager = EntityManagerGetter.getInstance().get();
		locks.forEach(lock -> {
			String reference = (String) lock[1];
			String targetTable = "T_"+lock[2];
			String lockType = (String) lock[3];			
			if(ActOperationType.DEVERROUILLAGE.equals(operation))
				actPersistence.unlock(reference,lockType,targetTable,entityManager);
			else
				actPersistence.lock(reference,lockType,targetTable,entityManager);
		});
		
		//if(Boolean.TRUE.equals(PRODUCTION) && ActOperationType.DEVERROUILLAGE.equals(operation))
		//	ValidatorImpl.Act.validateNoLockFound(identifiers,entityManager);
		
		createOperations(identifiers, operation, trigger,entityManager);
		return locks.size();
	}
	
	@Override @Transactional
	public void lock(Collection<String> identifiers, String trigger) {
		throw new RequestException("La fonctionnalité de verouillage n'est pas encore implémentée");
		//operate(identifiers, ActOperationType.VERROUILLAGE, trigger);
	}
	
	@Override
	public void lock(String trigger, String... identifiers) {
		lock(CollectionHelper.listOf(Boolean.TRUE, identifiers), trigger);
	}

	@Override @Transactional
	public Integer unlock(Collection<String> identifiers, String trigger,Boolean processedIgnorable) {
		return operate(identifiers, ActOperationType.DEVERROUILLAGE, trigger,processedIgnorable);
	}
	
	@Override
	public Integer unlock(String trigger,Boolean processedIgnorable, String... identifiers) {
		return unlock(CollectionHelper.listOf(Boolean.TRUE, identifiers), trigger,processedIgnorable);
	}
	
	private void createOperations(Collection<String> identifiers,ActOperationType operation, String trigger,EntityManager entityManager) {
		LocalDateTime date = LocalDateTime.now();
		identifiers.forEach(identifier -> {
			createOperation(entityManager,identifier,operation,date,trigger);
		});
	}
	
	private void createOperation(EntityManager entityManager,String identifier,ActOperationType operationType,LocalDateTime date,String trigger) {
		entityManager.persist(new ActOperationImpl().setIdentifier(UUID.randomUUID().toString()).setActIdentifier(identifier).setOperationType(operationType)
				.setOperationDate(date).setTrigger(trigger));
	}
	
	public static Boolean PRODUCTION = Boolean.TRUE;
}
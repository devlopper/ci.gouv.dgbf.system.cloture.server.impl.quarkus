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
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActOperationType;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActPersistence;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ActImplCodeOperationTypeReader;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ActOperationImpl;

@ApplicationScoped
public class ActBusinessImpl extends AbstractSpecificBusinessImpl<Act> implements ActBusiness,Serializable {

	@Inject ActPersistence actPersistence;
	
	@Override @Transactional
	public void lock(Collection<String> identifiers, String trigger) {
		validate(identifiers, trigger, ActOperationType.VERROUILLAGE);
		identifiers.forEach(identifier -> {
			actPersistence.lock(identifier);
		});
		createOperations(identifiers, ActOperationType.VERROUILLAGE, trigger);
	}
	
	@Override
	public void lock(String trigger, String... identifiers) {
		lock(CollectionHelper.listOf(Boolean.TRUE, identifiers), trigger);
	}

	@Override @Transactional
	public void unlock(Collection<String> identifiers, String trigger) {
		validate(identifiers, trigger, ActOperationType.DEVERROUILLAGE);
		identifiers.forEach(identifier -> {
			actPersistence.unlock(identifier);
		});
		createOperations(identifiers, ActOperationType.DEVERROUILLAGE, trigger);
	}
	
	@Override
	public void unlock(String trigger, String... identifiers) {
		unlock(CollectionHelper.listOf(Boolean.TRUE, identifiers), trigger);
	}
	
	private void createOperations(Collection<String> identifiers,ActOperationType operation, String trigger) {
		EntityManager entityManager = EntityManagerGetter.getInstance().get();
		LocalDateTime date = LocalDateTime.now();
		identifiers.forEach(identifier -> {
			createOperation(entityManager,identifier,operation,date,trigger);
		});
	}
	
	private void createOperation(EntityManager entityManager,String identifier,ActOperationType operationType,LocalDateTime date,String trigger) {
		entityManager.persist(new ActOperationImpl().setIdentifier(UUID.randomUUID().toString()).setActIdentifier(identifier).setOperationType(operationType)
				.setOperationDate(date).setTrigger(trigger));
	}
	
	private static void validate(Collection<String> identifiers, String trigger,ActOperationType operationType) {
		String prefix = ActOperationType.VERROUILLAGE.equals(operationType) ? "" : "dé";
		if(CollectionHelper.isEmpty(identifiers))
			throw new RequestException(String.format("L'identifiant d'un acte à %sverrouiller est obligatoire",prefix));
		if(StringHelper.isBlank(trigger))
			throw new RequestException("Le déclencheur de l'acte est obligatoire");
		
		Collection<Object[]> arrays = new ActImplCodeOperationTypeReader().readByIdentifiers(identifiers, null);
		if(CollectionHelper.isNotEmpty(arrays)) {
			Collection<String> alreadyLockedCodes = arrays.stream().filter(array -> operationType.equals(array[2])).map(array -> (String)array[1])
					.collect(Collectors.toList());
			if(CollectionHelper.isNotEmpty(alreadyLockedCodes))
				throw new RequestException(String.format("Les actes suivants sont déja %sverouillés : %s", prefix,alreadyLockedCodes.stream().collect(Collectors.joining(","))));
		}
	}
}
package ci.gouv.dgbf.system.cloture.server.impl.business;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowablesMessages;
import org.cyk.utility.__kernel__.time.TimeHelper;
import org.cyk.utility.business.RequestException;
import org.cyk.utility.business.Validator;

import ci.gouv.dgbf.system.cloture.server.api.persistence.ActOperationType;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Operation;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ActImplCodeNameReader;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ActImplNumberOfLocksEnabledReader;
import io.quarkus.arc.Unremovable;

@ApplicationScoped @ci.gouv.dgbf.system.cloture.server.api.System @Unremovable
public class ValidatorImpl extends Validator.AbstractImpl implements Serializable {

	@Override
	protected <T> void __validate__(Class<T> klass, T entity, Object actionIdentifier,ThrowablesMessages throwablesMessages) {
		super.__validate__(klass, entity, actionIdentifier, throwablesMessages);
		if(Boolean.TRUE.equals(ClassHelper.isInstanceOf(klass, Operation.class)))
			validate(actionIdentifier, (Operation) entity, throwablesMessages);
	}
	
	/**/
	
	private void validate(Object actionIdentifier,Operation operation,ThrowablesMessages throwablesMessages) {
		if(OperationBusinessImpl.EXECUTE.equals(actionIdentifier))
			validateOperationBeginExecution(operation, throwablesMessages);
	}
	
	public static void validateOperationBeginExecution(Operation operation,ThrowablesMessages throwablesMessages) {
		if(operation == null)
			throw new RuntimeException("L'opération à démarrer est obligatoire");
		validateOperationBeginExecutionEndDate(operation, throwablesMessages);
		validateOperationBeginExecutionBeginDate(operation, throwablesMessages);
		validateOperationBeginExecutionStartDate(operation, throwablesMessages);
		validateOperationBeginExecutionProcedureName(operation, throwablesMessages);
		validateOperationBeginExecutionTrigger(operation, throwablesMessages);
	}
	
	public static void validateOperationBeginExecutionBeginDate(Operation operation,ThrowablesMessages throwablesMessages) {
		if(operation.getExecutionBeginDate() != null && operation.getExecutionEndDate() == null)
			throwablesMessages.add(String.format("L'opération [%s] démarrée le %s par [%s] est en cours d'exécution",operation.getName()
					,TimeHelper.formatLocalDateTime(operation.getExecutionBeginDate()),TimeHelper.formatLocalDateTime(operation.getExecutionEndDate())
					,operation.getTrigger()));
	}
	
	public static void validateOperationBeginExecutionEndDate(Operation operation,ThrowablesMessages throwablesMessages) {
		if(operation.getExecutionEndDate() != null && operation.getExecutionBeginDate() != null)
			throwablesMessages.add(String.format("L'opération [%s] à déja été exécutée le %s par [%s] et s'est terminée le %s",operation.getName()
					,TimeHelper.formatLocalDateTime(operation.getExecutionBeginDate()),operation.getTrigger(),TimeHelper.formatLocalDateTime(operation.getExecutionEndDate())));
	}
	
	public static void validateOperationBeginExecutionStartDate(Operation operation,ThrowablesMessages throwablesMessages) {
		if(operation.getStartDate() == null)
			throwablesMessages.add(String.format("La date de début de l'opération [%s] n'a pas été défini",operation.getName()));
		else if(TimeHelper.toMillisecond(operation.getStartDate()) > System.currentTimeMillis())
			throwablesMessages.add(String.format("L'opération [%s] ne peut être démarrée qu'à partir du %s",operation.getName()
					,TimeHelper.formatLocalDateTime(operation.getStartDate())));
	}
	
	public static void validateOperationBeginExecutionProcedureName(Operation operation,ThrowablesMessages throwablesMessages) {
		if(StringHelper.isBlank(operation.getProcedureName()))
			throwablesMessages.add(String.format("Le nom de la procédure de l'opération [%s] est obligatoire",operation.getName()));
	}
	
	public static void validateOperationBeginExecutionTrigger(Operation operation,ThrowablesMessages throwablesMessages) {
		if(StringHelper.isBlank(operation.getTrigger()))
			throwablesMessages.add(String.format("Le nom d'utilisateur devant démarrer l'opération [%s] est obligatoire",operation.getName()));
	}

	/**/
	
	public static interface Act {
		static void validate(Collection<String> identifiers,ActOperationType operationType, String trigger) {
			String prefix = ActOperationType.VERROUILLAGE.equals(operationType) ? "" : "dé";
			if(CollectionHelper.isEmpty(identifiers))
				throw new RequestException(String.format("L'identifiant d'un acte à %sverrouiller est obligatoire",prefix));
			if(StringHelper.isBlank(trigger))
				throw new RequestException("Le déclencheur est obligatoire");
			
			if(ActOperationType.VERROUILLAGE.equals(operationType)) {
				
			}else if(ActOperationType.DEVERROUILLAGE.equals(operationType)){
				Collection<Object[]> arrays = new ActImplNumberOfLocksEnabledReader().readByIdentifiers(identifiers, null);
				if(CollectionHelper.isNotEmpty(arrays)) {
					Collection<String> identifiersHavingZeroLock = arrays.stream().filter(array -> NumberHelper.isEqualToZero(NumberHelper.getLong(array[1], 0l))).map(array -> (String)array[0])
							.collect(Collectors.toList());
					if(CollectionHelper.isNotEmpty(identifiersHavingZeroLock)) {
						Collection<String> codesNames = new ActImplCodeNameReader().readByIdentifiers(identifiers, null).stream().map(array -> array[1]+" "+array[2]).collect(Collectors.toList());
						if(CollectionHelper.isEmpty(codesNames))
							throw new RequestException("Certains actes ne sont pas verouillés");
						throw new RequestException(String.format("Les actes suivants ne sont pas verouillés : %s", codesNames.stream().collect(Collectors.joining(","))));
					}
				}	
			}
		}
		
		static void validateNoLockFound(Collection<String> identifiers,EntityManager entityManager) {
			if(CollectionHelper.isEmpty(new ActImplNumberOfLocksEnabledReader().setEntityManager(entityManager).readByIdentifiers(identifiers, null)))
				return;
			throw new RequestException("Certains actes n'ont pas pu être déverouillés");
		}
	}	
}
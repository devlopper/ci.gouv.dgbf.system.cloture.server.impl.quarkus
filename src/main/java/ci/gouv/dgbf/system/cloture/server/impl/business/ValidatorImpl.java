package ci.gouv.dgbf.system.cloture.server.impl.business;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
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
import org.cyk.utility.persistence.query.Filter;
import org.cyk.utility.persistence.query.QueryExecutorArguments;

import ci.gouv.dgbf.system.cloture.server.api.persistence.ActOperationType;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationActPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationStatus;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationStatusPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationType;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationTypePersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Parameters;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Script;
import ci.gouv.dgbf.system.cloture.server.impl.Configuration;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationImpl;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationStatusImpl;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationTypeImpl;
import io.quarkus.arc.Unremovable;

@ApplicationScoped @ci.gouv.dgbf.system.cloture.server.api.System @Unremovable
public class ValidatorImpl extends Validator.AbstractImpl implements Serializable {

	@Override
	protected <T> void __validate__(Class<T> klass, T entity, Object actionIdentifier,ThrowablesMessages throwablesMessages) {
		super.__validate__(klass, entity, actionIdentifier, throwablesMessages);
		if(Boolean.TRUE.equals(ClassHelper.isInstanceOf(klass, Script.class)))
			validate(actionIdentifier, (Script) entity, throwablesMessages);
	}
	
	/**/
	
	private void validate(Object actionIdentifier,Script operation,ThrowablesMessages throwablesMessages) {
		if(ScriptBusinessImpl.EXECUTE.equals(actionIdentifier))
			validateOperationBeginExecution(operation, throwablesMessages);
	}
	
	public static void validateOperationBeginExecution(Script operation,ThrowablesMessages throwablesMessages) {
		if(operation == null)
			throw new RuntimeException("L'opération à démarrer est obligatoire");
		validateOperationBeginExecutionEndDate(operation, throwablesMessages);
		validateOperationBeginExecutionBeginDate(operation, throwablesMessages);
		validateOperationBeginExecutionStartDate(operation, throwablesMessages);
		validateOperationBeginExecutionProcedureName(operation, throwablesMessages);
		validateOperationBeginExecutionTrigger(operation, throwablesMessages);
	}
	
	public static void validateOperationBeginExecutionBeginDate(Script operation,ThrowablesMessages throwablesMessages) {
		if(operation.getExecutionBeginDate() != null && operation.getExecutionEndDate() == null)
			throwablesMessages.add(String.format("L'opération [%s] démarrée le %s par [%s] est en cours d'exécution",operation.getName()
					,TimeHelper.formatLocalDateTime(operation.getExecutionBeginDate()),TimeHelper.formatLocalDateTime(operation.getExecutionEndDate())
					,operation.getTrigger()));
	}
	
	public static void validateOperationBeginExecutionEndDate(Script operation,ThrowablesMessages throwablesMessages) {
		if(operation.getExecutionEndDate() != null && operation.getExecutionBeginDate() != null)
			throwablesMessages.add(String.format("L'opération [%s] à déja été exécutée le %s par [%s] et s'est terminée le %s",operation.getName()
					,TimeHelper.formatLocalDateTime(operation.getExecutionBeginDate()),operation.getTrigger(),TimeHelper.formatLocalDateTime(operation.getExecutionEndDate())));
	}
	
	public static void validateOperationBeginExecutionStartDate(Script operation,ThrowablesMessages throwablesMessages) {
		if(operation.getStartDate() == null)
			throwablesMessages.add(String.format("La date de début de l'opération [%s] n'a pas été défini",operation.getName()));
		else if(TimeHelper.toMillisecond(operation.getStartDate()) > System.currentTimeMillis())
			throwablesMessages.add(String.format("L'opération [%s] ne peut être démarrée qu'à partir du %s",operation.getName()
					,TimeHelper.formatLocalDateTime(operation.getStartDate())));
	}
	
	public static void validateOperationBeginExecutionProcedureName(Script operation,ThrowablesMessages throwablesMessages) {
		if(StringHelper.isBlank(operation.getProcedureName()))
			throwablesMessages.add(String.format("Le nom de la procédure de l'opération [%s] est obligatoire",operation.getName()));
	}
	
	public static void validateOperationBeginExecutionTrigger(Script operation,ThrowablesMessages throwablesMessages) {
		if(StringHelper.isBlank(operation.getTrigger()))
			throwablesMessages.add(String.format("Le nom d'utilisateur devant démarrer l'opération [%s] est obligatoire",operation.getName()));
	}

	/**/
	
	public static interface Act {
		static void validate(Collection<String> identifiers,ActOperationType operationType, String trigger,Boolean processedIgnorable) {
			String prefix = ActOperationType.VERROUILLAGE.equals(operationType) ? "" : "dé";
			if(CollectionHelper.isEmpty(identifiers))
				throw new RequestException(String.format("L'identifiant d'un acte à %sverrouiller est obligatoire",prefix));
			if(StringHelper.isBlank(trigger))
				throw new RequestException("Le déclencheur est obligatoire");
			
			if(ActOperationType.VERROUILLAGE.equals(operationType)) {
				
			}else if(ActOperationType.DEVERROUILLAGE.equals(operationType)){
				/*if(processedIgnorable == null || Boolean.FALSE.equals(processedIgnorable)) {
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
				}*/				
			}
		}
		
		static void validateNoLockFound(Collection<String> identifiers,EntityManager entityManager) {
			//if(CollectionHelper.isEmpty(new ActImplNumberOfLocksEnabledReader().setEntityManager(entityManager).readByIdentifiers(identifiers, null)))
			//	return;
			throw new RequestException("Certains actes n'ont pas pu être déverouillés");
		}
	}
	
	public static interface Operation {
		static Object[] validateCreateInputs(String typeIdentifier,String name,String reason,String auditWho,ThrowablesMessages throwablesMessages) {
			validateIdentifier(typeIdentifier,ci.gouv.dgbf.system.cloture.server.api.persistence.OperationType.NAME, throwablesMessages);
			OperationTypeImpl type = StringHelper.isBlank(typeIdentifier) ? null : (OperationTypeImpl) validateExistenceAndReturn(OperationType.class, typeIdentifier,List.of(OperationTypeImpl.FIELD_IDENTIFIER,OperationTypeImpl.FIELD_NAME)
					, __inject__(OperationTypePersistence.class), throwablesMessages);
			throwablesMessages.addIfTrue("Le motif est requis", StringHelper.isBlank(reason));
			Validator.getInstance().validateAuditWho(auditWho, throwablesMessages);
			return new Object[] {type};
		}
		
		static OperationStatus validateCreate(OperationType type,Boolean sequentialExecution,ThrowablesMessages throwablesMessages) {
			if(sequentialExecution == null)
				sequentialExecution = Boolean.TRUE.equals(__inject__(Configuration.class).operation().execution().sequential());
			if(sequentialExecution) {
				Collection<ci.gouv.dgbf.system.cloture.server.api.persistence.Operation> operations = __inject__(OperationPersistence.class).readMany(new QueryExecutorArguments()
						.addProjectionsFromStrings(OperationImpl.FIELD_IDENTIFIER,OperationImpl.FIELD_CODE,OperationImpl.FIELD_NAME).addFilterField(Parameters.OPERATION_EXECUTED, Boolean.FALSE));
				if(CollectionHelper.getSize(operations) > 0)
					throwablesMessages.add("Des opérations non encore exécutées ont été trouvées : "+operations.stream().map(o -> o.toString()).collect(Collectors.joining(",")));
			}
			Configuration configuration = __inject__(Configuration.class);
			OperationStatus status = __inject__(OperationStatusPersistence.class).readOne(new QueryExecutorArguments().addProjectionsFromStrings(OperationStatusImpl.FIELD_IDENTIFIER)
					.addFilterField(Parameters.CODE, configuration.operation().status().createdCode()));
			throwablesMessages.addIfTrue(String.format("Le statut %s est introuvable.",configuration.operation().status().createdCode()), status == null);
			return status;
		}
		
		static void validateAddOrRemoveToOperationInputs(String identifier,Collection<String> actsIdentifiers,Boolean areActsIdentifiersRequired, Boolean comprehensively,String auditWho,ThrowablesMessages throwablesMessages) {
			throwablesMessages.addIfTrue(String.format("L'identifiant de %s est requis",ci.gouv.dgbf.system.cloture.server.api.persistence.Operation.NAME), StringHelper.isBlank(identifier));
			if((areActsIdentifiersRequired == null || areActsIdentifiersRequired) && !Boolean.TRUE.equals(comprehensively))
				throwablesMessages.addIfTrue(String.format("Les identifiants des %s sont requis",ci.gouv.dgbf.system.cloture.server.api.persistence.Act.NAME_PLURAL), CollectionHelper.isEmpty(actsIdentifiers));
			Validator.getInstance().validateAuditWho(auditWho, throwablesMessages);
		}
		/*
		static void validateAddOrRemoveToOperationInputs(String identifier,Collection<String> actsIdentifiers, String auditWho,ThrowablesMessages throwablesMessages) {
			validateAddOrRemoveToOperationInputs(identifier,actsIdentifiers,  Boolean.FALSE, auditWho, throwablesMessages);
		}
		
		static void validateSetAsIncludedOrExcludedInputs(String identifier,Collection<String> actsIdentifiers, String auditWho,ThrowablesMessages throwablesMessages) {
			validateAddOrRemoveToOperationInputs(identifier,actsIdentifiers,  Boolean.TRUE, auditWho, throwablesMessages);
		}*/
		
		static void validateAddOrRemoveToOperation(Collection<Object[]> arrays,Collection<String> actsIdentifiers,Boolean add,Boolean existingIgnorable,ci.gouv.dgbf.system.cloture.server.api.persistence.Operation operation,ThrowablesMessages throwablesMessages) {
			if(CollectionHelper.isEmpty(arrays) || Boolean.TRUE.equals(existingIgnorable))
				return;
			Collection<Object[]> existingArrays = arrays.stream().filter(array -> Boolean.TRUE.equals(add) ? array[2] != null : array[2] == null).collect(Collectors.toList());
			if(CollectionHelper.isEmpty(existingArrays))
				return;
			throwablesMessages.add(String.format("Les %s suivants %s : %s",ci.gouv.dgbf.system.cloture.server.api.persistence.Act.NAME_PLURAL,Boolean.TRUE.equals(add) ? "ont déja été ajoutés" : "ne sont pas ajoutés"
				, existingArrays.stream().map(array -> (String)array[1]).collect(Collectors.joining(","))));
		}
		
		static void validateAddOrRemoveToOperationByFilterInputs(String identifier,Filter filter, Boolean comprehensively,String auditWho,ThrowablesMessages throwablesMessages) {
			validateIdentifier(identifier, ci.gouv.dgbf.system.cloture.server.api.persistence.Operation.NAME, throwablesMessages);
			throwablesMessages.addIfTrue("Le filtre est requis", Filter.isEmpty(filter));
			Validator.getInstance().validateAuditWho(auditWho, throwablesMessages);
		}
		
		static Object[] validateStartInputs(String identifier,String auditWho,ThrowablesMessages throwablesMessages) {
			validateIdentifier(identifier,ci.gouv.dgbf.system.cloture.server.api.persistence.Operation.NAME, throwablesMessages);
			OperationImpl operation = StringHelper.isBlank(identifier) ? null : (OperationImpl) validateExistenceAndReturn(ci.gouv.dgbf.system.cloture.server.api.persistence.Operation.class, identifier
					,List.of(OperationImpl.FIELD_IDENTIFIER,OperationImpl.FIELD_STATUS,OperationTypeImpl.FIELD_NAME)
					, __inject__(OperationPersistence.class), throwablesMessages);
			Validator.getInstance().validateAuditWho(auditWho, throwablesMessages);
			return new Object[] {operation};
		}
		
		static void validateStart(ci.gouv.dgbf.system.cloture.server.api.persistence.Operation operation,ThrowablesMessages throwablesMessages) {
			Configuration configuration = __inject__(Configuration.class);
			OperationStatus startedStatus = __inject__(OperationStatusPersistence.class).readOne(new QueryExecutorArguments().addProjectionsFromStrings(OperationStatusImpl.FIELD_IDENTIFIER,OperationStatusImpl.FIELD_ORDER_NUMBER)
					.addFilterField(Parameters.CODE, configuration.operation().status().startedCode()));
			throwablesMessages.addIfTrue(String.format("Le statut démarrage ayant pour code %s est introuvable.",configuration.operation().status().startedCode()),startedStatus == null);
			if(startedStatus != null && operation.getStatus().getOrderNumber() >= startedStatus.getOrderNumber()) {
				throwablesMessages.add(String.format("%s %s à déja été démarrée",ci.gouv.dgbf.system.cloture.server.api.persistence.Operation.NAME,operation.getName()));
			}else {
				throwablesMessages.addIfTrue(String.format("%s %s doit contenir au moins un acte",ci.gouv.dgbf.system.cloture.server.api.persistence.Operation.NAME,operation.getName())
						, NumberHelper.isLessThanOrEqualZero(__inject__(OperationActPersistence.class).count(new QueryExecutorArguments().addFilterField(Parameters.OPERATION_IDENTIFIER, operation.getIdentifier()))));
			}
		}
	}
}
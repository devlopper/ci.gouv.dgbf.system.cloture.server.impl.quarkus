package ci.gouv.dgbf.system.cloture.server.impl.business;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.marker.IdentifiableSystem;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowablesMessages;
import org.cyk.utility.business.Result;
import org.cyk.utility.business.server.AbstractSpecificBusinessImpl;
import org.cyk.utility.persistence.EntityManagerGetter;
import org.cyk.utility.persistence.query.Field;
import org.cyk.utility.persistence.query.Filter;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.server.procedure.ProcedureExecutorGetter;
import org.cyk.utility.persistence.server.query.executor.field.CodeExecutor;

import ci.gouv.dgbf.system.cloture.server.api.business.OperationActBusiness;
import ci.gouv.dgbf.system.cloture.server.api.business.OperationBusiness;
import ci.gouv.dgbf.system.cloture.server.api.business.OperationImputationBusiness;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Act;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Imputation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ImputationPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Operation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationActPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationImputationPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationStatus;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationStatusPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationType;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Parameters;
import ci.gouv.dgbf.system.cloture.server.impl.Configuration;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ActImpl;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ActImplIdentifierCodeOperationIdentifierReader;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ImputationImpl;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ImputationImplIdentifierActivityCodeEconomicNatureCodeOperationIdentifierReader;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationActImpl;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationImpl;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationImputationImpl;
import io.quarkus.vertx.ConsumeEvent;
import io.vertx.core.eventbus.EventBus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@ApplicationScoped
public class OperationBusinessImpl extends AbstractSpecificBusinessImpl<Operation> implements OperationBusiness,Serializable {

	@Inject OperationPersistence persistence;
	@Inject OperationStatusPersistence statusPersistence;
	@Inject ActPersistence actPersistence;
	@Inject ImputationPersistence imputationPersistence;
	@Inject OperationActPersistence operationActPersistence;
	@Inject OperationImputationPersistence operationImputationPersistence;
	@Inject OperationActBusiness operationActBusiness;
	@Inject OperationImputationBusiness operationImputationBusiness;
	@Inject EntityManager entityManager;
	@Inject Configuration configuration;
	
	@Inject CodeExecutor codeExecutor;
	@Inject ProcedureExecutorGetter procedureExecutorGetter;
	
	@Inject EventBus eventBus;
	
	/* create */
	
	@Override @Transactional
	public Result create(String typeIdentifier,String code,String name, String reason,String auditWho,Boolean sequentialExecution) {
		Result result = new Result().open();
		ThrowablesMessages throwablesMessages = new ThrowablesMessages();
		// Validation of inputs
		Object[] array = ValidatorImpl.Operation.validateCreateInputs(typeIdentifier,code,name, reason, auditWho, throwablesMessages);
		throwablesMessages.throwIfNotEmpty();
		OperationType type = (OperationType) array[0];
		OperationStatus status = ValidatorImpl.Operation.validateCreate(type,sequentialExecution, throwablesMessages);
		throwablesMessages.throwIfNotEmpty();
		OperationImpl operation = new OperationImpl();
		operation.setType(type).setCode(code).setName(name).setReason(reason);
		if(StringHelper.isBlank(operation.getCode()))
			operation.setCode(buildCode(operation.getType()));
		if(StringHelper.isBlank(operation.getIdentifier()))
			operation.setIdentifier(operation.getCode());
		if(StringHelper.isBlank(operation.getIdentifier()))
			operation.setIdentifier(IdentifiableSystem.generateRandomly());
		if(StringHelper.isBlank(operation.getName()))
			operation.setName(buildName(operation.getCode(),operation.getReason()));
		operation.setStatus(status);
		
		audit(operation, generateAuditIdentifier(), CREATE_AUDIT_IDENTIFIER, auditWho, LocalDateTime.now());
		entityManager.persist(operation);
		// Return of message
		String operationLabel = String.format("%s de type %s",Operation.NAME,operation.getType().getName());
		result.close().setName(String.format("Création %s par %s",operationLabel,auditWho)).log(getClass());
		result.addMessages(String.format("%s créée", operationLabel));
		result.getMap(Boolean.TRUE).put(Parameters.OPERATION_IDENTIFIER, operation.getIdentifier());
		return result;
	}
	
	String buildCode(OperationType type) {
		Collection<String> codes = codeExecutor.getValues(OperationImpl.class);
		String code = null;
		Integer index = CollectionHelper.getSize(codes)+1;
		do {
			code = String.format(configuration.operation().code().format(),type.getCode().substring(0, 1),index++);
		}while(codes != null && codes.contains(code));
		return code;
	}
	
	String buildName(String code,String reason) {
		String name = null;
		if(StringHelper.isBlank(reason))
			name = code;
		else
			name = StringUtils.substring(reason, 0, configuration.operation().name().numberOfCharactersExtractedFromReason());
		return name;
	}
	
	@Override
	public Result create(String typeIdentifier, String code, String name, String reason, String auditWho) {
		return create(typeIdentifier, code, name, reason, auditWho, null);
	}
	
	/* add acts */

	@Transactional
	Object[] addActInTransaction(String identifier, Collection<String> actsIdentifiers,Boolean areActsIdentifiersRequired,Boolean existingIgnorable, String auditWho,String auditIdentifier,String auditFunctionality,LocalDateTime auditDate,EntityManager entityManager) {
		Object[] array = validateActs(identifier, actsIdentifiers,areActsIdentifiersRequired, existingIgnorable, Boolean.TRUE, Boolean.FALSE, auditWho);
		OperationImpl operation = (OperationImpl) array[1];
		Collection<Act> acts = (Collection<Act>) array[2];
		if(CollectionHelper.isNotEmpty(acts))
			((OperationActBusinessImpl)operationActBusiness).create(operation, acts, auditIdentifier, auditFunctionality, auditWho, auditDate, entityManager);
		return array;
	}
	
	@Override
	public Result addAct(String identifier, Collection<String> actsIdentifiers,Boolean existingIgnorable, String auditWho) {
		Result result = openAddOrRemoveActResult(Boolean.TRUE);
		Object[] array = addActInTransaction(identifier, actsIdentifiers,Boolean.TRUE, existingIgnorable, auditWho, generateAuditIdentifier(), ADD_ACT_AUDIT_IDENTIFIER, LocalDateTime.now(), entityManager);
		OperationImpl operation = (OperationImpl) array[1];
		Collection<Act> acts = (Collection<Act>) array[2];
		closeAddOrRemoveActResult(result, "%s %s dans %s","Ajout de %s par %s","%s ajouté(s)", operation, acts, auditWho);
		return result;
	}

	@Override
	public Result addAct(String identifier,Boolean existingIgnorable, String auditWho, String... actsIdentifiers) {
		return addAct(identifier,CollectionHelper.listOf(Boolean.TRUE, actsIdentifiers),existingIgnorable,auditWho);
	}

	@Override @Transactional
	public Result addActComprehensively(String identifier, Collection<String> actsIdentifiers, String auditWho) {
		Object[] array = validateActs(identifier, actsIdentifiers,Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, auditWho);
		OperationImpl operation = (OperationImpl) array[1];
		Collection<Act> acts = (Collection<Act>) array[2];
		Result result = openAddOrRemoveActResult(Boolean.TRUE);
		
		String auditIdentifier = generateAuditIdentifier();
		LocalDateTime auditDate = LocalDateTime.now();
		
		// deletions
		Collection<OperationActImpl> deletableOperationActs = CollectionHelper.cast(OperationActImpl.class, CollectionHelper.isEmpty(actsIdentifiers) 
				? operationActPersistence.readByOperationIdentifier(identifier) : operationActPersistence.readByOperationIdentifierByNotActsIdentifiers(identifier, actsIdentifiers));
		if(CollectionHelper.isNotEmpty(deletableOperationActs))
			((OperationActBusinessImpl)operationActBusiness).delete(deletableOperationActs.stream().map(operationAct -> entityManager.merge(operationAct)).collect(Collectors.toList())
					, auditIdentifier, REMOVE_ACT_AUDIT_IDENTIFIER, auditWho, auditDate, entityManager);
		
		// creations
		if(CollectionHelper.isNotEmpty(acts))
			((OperationActBusinessImpl)operationActBusiness).create(operation, acts, auditIdentifier, ADD_ACT_AUDIT_IDENTIFIER, auditWho, auditDate, entityManager);
		
		// Return of message
		String actsLabel = String.format("%s %s dans %s",CollectionHelper.getSize(acts),Act.NAME_PLURAL,operation.getName());
		result.close().setName(String.format("Ajout exhaustif de %s par %s",actsLabel,auditWho)).log(getClass());
		result.addMessages(String.format("%s ajouté(s) exhaustivement", actsLabel));
		return result;
	}

	@Override @Transactional
	public Result addActComprehensively(String identifier, String auditWho, String... actsIdentifiers) {
		return addActComprehensively(identifier,CollectionHelper.listOf(Boolean.TRUE, actsIdentifiers),auditWho);
	}
	
	@Override
	public Result addActByFilter(String identifier, Filter filter, Boolean existingIgnorable, String auditWho) {
		ThrowablesMessages throwablesMessages = new ThrowablesMessages();
		Object[] objects = ValidatorImpl.Operation.validateAddOrRemoveToOperationByFilterInputs(identifier, filter, existingIgnorable, auditWho, throwablesMessages);
		throwablesMessages.throwIfNotEmpty();
		Operation operation = (Operation) objects[0];
		ValidatorImpl.Operation.validateAddOrRemoveToOperationByFilter(operation,throwablesMessages);
		throwablesMessages.throwIfNotEmpty();
		
		normalizeFilterForAddOrRemoveActByFilter(filter, Boolean.TRUE);
		String auditIdentifier = generateAuditIdentifier();
		LocalDateTime auditDate = LocalDateTime.now();
		String auditFunctionality = ADD_ACT_BY_FILTER_AUDIT_IDENTIFIER;
		Result result = openAddOrRemoveActResult(Boolean.TRUE);
		List<String> actsIdentifiers = (List<String>) actPersistence.readIdentifiersAsStringsByFilter(filter);
		Object[] array = addOrRemoveActInBatch(identifier, existingIgnorable, auditWho, auditIdentifier, auditFunctionality, auditDate, entityManager, result, actsIdentifiers, Boolean.TRUE);
		
		Collection<Act> acts = (Collection<Act>) array[1];
		closeAddOrRemoveActResult(result, "%s %s dans %s","Ajout par filtre de %s par %s","%s ajouté(s) par filtre", operation, acts, auditWho);
		return result;
	}
	
	void normalizeFilterForAddOrRemoveByFilter(Filter filter,Boolean add,String parameterName) {
		if(filter == null)
			return;
		add = !add;
		Field field = filter.getField(parameterName);
		if(field == null)
			filter.addField(parameterName, add);
		else
			field.setValue(add);
	}
	
	void normalizeFilterForAddOrRemoveActByFilter(Filter filter,Boolean add) {
		normalizeFilterForAddOrRemoveByFilter(filter, add, Parameters.ADDED_TO_SPECIFIED_OPERATION);
	}
	
	Object[] addOrRemoveActInBatch(String identifier, Boolean existingIgnorable, String auditWho,String auditIdentifier,String auditFunctionality,LocalDateTime auditDate,EntityManager entityManager,Result result,List<String> actsIdentifiers,Boolean add) {
		List<List<String>> batches = CollectionHelper.getBatches(actsIdentifiers, 1000);
		if(CollectionHelper.isEmpty(batches)) {
			if(batches == null)
				batches = new ArrayList<>();
			batches.add(List.of());
		}
		Operation[] operation = {null};
		Collection<Act> acts = new ArrayList<>();
		batches.parallelStream().forEach(batch -> {
			Object[] array;
			try {
				if(Boolean.TRUE.equals(add))
					array = addActInTransaction(identifier,batch ,Boolean.FALSE, existingIgnorable, auditWho, auditIdentifier, auditFunctionality, auditDate, entityManager);
				else
					array = removeActInTransaction(identifier,batch ,Boolean.FALSE, existingIgnorable, auditWho, auditIdentifier, auditFunctionality, auditDate, entityManager);
			} catch (Exception exception) {
				result.addMessages(exception.getMessage());
				return;
			}
			if(array == null)
				return;
			if(operation[0] == null)
				operation[0] = (Operation) array[1];
			if(CollectionHelper.isNotEmpty((Collection<Act>) array[2]))
				acts.addAll((Collection<Act>) array[2]);
		});
		return new Object[]{operation[0],acts};
	}
	
	/* remove acts */
	
	@Transactional
	Object[] removeActInTransaction(String identifier, Collection<String> actsIdentifiers,Boolean areActsIdentifiersRequired, Boolean existingIgnorable,String auditWho,String auditIdentifier,String auditFunctionality,LocalDateTime auditDate
			,EntityManager entityManager) {
		Object[] array = validateActs(identifier, actsIdentifiers,areActsIdentifiersRequired, existingIgnorable, Boolean.FALSE, Boolean.FALSE, auditWho);
		Collection<Act> acts = (Collection<Act>) array[2];
		Collection<OperationActImpl> operationActs = null;
		if(CollectionHelper.isNotEmpty(acts))
			for(List<Act> batch : CollectionHelper.getBatches((List<Act>)acts, 999)) {
				Collection<OperationActImpl> r = CollectionHelper.cast(OperationActImpl.class, operationActPersistence.readMany(new QueryExecutorArguments()
						.addFilterFieldsValues(Parameters.OPERATION_IDENTIFIER,identifier,Parameters.ACTS_IDENTIFIERS,FieldHelper.readSystemIdentifiersAsStrings(batch))));
				if(CollectionHelper.isEmpty(r))
					continue;
				if(operationActs == null)
					operationActs = new ArrayList<>();
				operationActs.addAll(r);
			}
		if(CollectionHelper.isNotEmpty(operationActs))
			((OperationActBusinessImpl)operationActBusiness).delete(operationActs.stream().map(operationAct -> entityManager.merge(operationAct)).collect(Collectors.toList())
					, auditIdentifier, REMOVE_ACT_AUDIT_IDENTIFIER, auditWho, auditDate, entityManager);
		return array;
	}
	
	@Override
	public Result removeAct(String identifier, Collection<String> actsIdentifiers, Boolean existingIgnorable,String auditWho) {
		Result result = openAddOrRemoveActResult(Boolean.FALSE);
		Object[] array = removeActInTransaction(identifier, actsIdentifiers,Boolean.TRUE, existingIgnorable, auditWho, generateAuditIdentifier(), REMOVE_ACT_AUDIT_IDENTIFIER, LocalDateTime.now(), entityManager);
		OperationImpl operation = (OperationImpl) array[1];
		Collection<Act> acts = (Collection<Act>) array[2];
		closeAddOrRemoveActResult(result, "%s %s dans %s", "Retrait de %s par %s", "%s retiré(s)", operation, acts, auditWho);
		return result;
	}
	
	@Override
	public Result removeAct(String identifier, Boolean existingIgnorable, String auditWho, String... actsIdentifiers) {
		return removeAct(identifier, CollectionHelper.listOf(Boolean.TRUE, actsIdentifiers), existingIgnorable, auditWho);
	}
	
	@Override
	public Result removeActByFilter(String identifier, Filter filter, Boolean existingIgnorable, String auditWho) {
		ThrowablesMessages throwablesMessages = new ThrowablesMessages();
		Object[] objects = ValidatorImpl.Operation.validateAddOrRemoveToOperationByFilterInputs(identifier, filter, existingIgnorable, auditWho, throwablesMessages);
		throwablesMessages.throwIfNotEmpty();
		Operation operation = (Operation) objects[0];
		ValidatorImpl.Operation.validateAddOrRemoveToOperationByFilter(operation,throwablesMessages);
		throwablesMessages.throwIfNotEmpty();
		
		normalizeFilterForAddOrRemoveActByFilter(filter, Boolean.FALSE);
		String auditIdentifier = generateAuditIdentifier();
		LocalDateTime auditDate = LocalDateTime.now();
		String auditFunctionality = REMOVE_ACT_BY_FILTER_AUDIT_IDENTIFIER;
		Result result = openAddOrRemoveActResult(Boolean.TRUE);
		List<String> actsIdentifiers = (List<String>) actPersistence.readIdentifiersAsStringsByFilter(filter);
		Object[] array = addOrRemoveActInBatch(identifier, existingIgnorable, auditWho, auditIdentifier, auditFunctionality, auditDate, entityManager, result, actsIdentifiers, Boolean.FALSE);
		
		Collection<Act> acts = (Collection<Act>) array[1];
		closeAddOrRemoveActResult(result, "%s %s dans %s","Retrait par filtre de %s par %s","%s retiré(s) par filtre", operation, acts, auditWho);
		return result;
	}
	
	/* add imputations */
	
	@Transactional
	Object[] addImputationInTransaction(String identifier, Collection<String> imputationsIdentifiers,Boolean areImputationsIdentifiersRequired,Boolean existingIgnorable, String auditWho,String auditIdentifier,String auditFunctionality,LocalDateTime auditDate,EntityManager entityManager) {
		Object[] array = validateImputations(identifier, imputationsIdentifiers,areImputationsIdentifiersRequired, existingIgnorable, Boolean.TRUE, Boolean.FALSE, auditWho);
		OperationImpl operation = (OperationImpl) array[1];
		Collection<Imputation> imputations = (Collection<Imputation>) array[2];
		if(CollectionHelper.isNotEmpty(imputations))
			((OperationImputationBusinessImpl)operationImputationBusiness).create(operation, imputations, auditIdentifier, auditFunctionality, auditWho, auditDate, entityManager);
		return array;
	}
	
	@Override
	public Result addImputation(String identifier, Boolean existingIgnorable, String auditWho,String... imputationsIdentifiers) {
		return addImputation(identifier,CollectionHelper.listOf(Boolean.TRUE, imputationsIdentifiers),existingIgnorable,auditWho);
	}
	
	@Override
	public Result addImputation(String identifier, Collection<String> imputationsIdentifiers, Boolean existingIgnorable,
			String auditWho) {
		Result result = openAddOrRemoveImputationResult(Boolean.TRUE);
		Object[] array = addImputationInTransaction(identifier, imputationsIdentifiers,Boolean.TRUE, existingIgnorable, auditWho, generateAuditIdentifier(), ADD_IMPUTATION_AUDIT_IDENTIFIER, LocalDateTime.now(), entityManager);
		OperationImpl operation = (OperationImpl) array[1];
		Collection<Imputation> imputations = (Collection<Imputation>) array[2];
		closeAddOrRemoveImputationResult(result, "%s %s dans %s","Ajout de %s par %s","%s ajoutée(s)", operation, imputations, auditWho);
		return result;
	}
	
	@Override
	public Result addImputationComprehensively(String identifier, Collection<String> imputationsIdentifiers,String auditWho) {
		Object[] array = validateImputations(identifier, imputationsIdentifiers,Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, auditWho);
		OperationImpl operation = (OperationImpl) array[1];
		Collection<Imputation> imputations = (Collection<Imputation>) array[2];
		Result result = openAddOrRemoveImputationResult(Boolean.TRUE);
		
		String auditIdentifier = generateAuditIdentifier();
		LocalDateTime auditDate = LocalDateTime.now();
		
		// deletions
		Collection<OperationImputationImpl> deletableOperationImputations = CollectionHelper.cast(OperationImputationImpl.class, CollectionHelper.isEmpty(imputationsIdentifiers) 
				? operationImputationPersistence.readByOperationIdentifier(identifier) : operationImputationPersistence.readByOperationIdentifierByNotImputationsIdentifiers(identifier, imputationsIdentifiers));
		if(CollectionHelper.isNotEmpty(deletableOperationImputations))
			((OperationImputationBusinessImpl)operationImputationBusiness).delete(deletableOperationImputations.stream().map(operationImputation -> entityManager.merge(operationImputation)).collect(Collectors.toList())
					, auditIdentifier, REMOVE_ACT_AUDIT_IDENTIFIER, auditWho, auditDate, entityManager);
		
		// creations
		if(CollectionHelper.isNotEmpty(imputations))
			((OperationImputationBusinessImpl)operationImputationBusiness).create(operation, imputations, auditIdentifier, ADD_ACT_AUDIT_IDENTIFIER, auditWho, auditDate, entityManager);
		
		// Return of message
		String imputationsLabel = String.format("%s %s dans %s",CollectionHelper.getSize(imputations),Imputation.NAME_PLURAL,operation.getName());
		result.close().setName(String.format("Ajout exhaustif de %s par %s",imputationsLabel,auditWho)).log(getClass());
		result.addMessages(String.format("%s ajoutée(s) exhaustivement", imputationsLabel));
		return result;
	}
	
	@Override
	public Result addImputationComprehensively(String identifier, String auditWho, String... imputationsIdentifiers) {
		return addImputationComprehensively(identifier,CollectionHelper.listOf(Boolean.TRUE, imputationsIdentifiers),auditWho);
	}
	
	@Override
	public Result addImputationByFilter(String identifier, Filter filter, Boolean existingIgnorable, String auditWho) {
		ThrowablesMessages throwablesMessages = new ThrowablesMessages();
		Object[] objects = ValidatorImpl.Operation.validateAddOrRemoveToOperationByFilterInputs(identifier, filter, existingIgnorable, auditWho, throwablesMessages);
		throwablesMessages.throwIfNotEmpty();
		Operation operation = (Operation) objects[0];
		ValidatorImpl.Operation.validateAddOrRemoveToOperationByFilter(operation,throwablesMessages);
		throwablesMessages.throwIfNotEmpty();
		
		normalizeFilterForAddOrRemoveImputationByFilter(filter, Boolean.TRUE);
		String auditIdentifier = generateAuditIdentifier();
		LocalDateTime auditDate = LocalDateTime.now();
		String auditFunctionality = ADD_ACT_BY_FILTER_AUDIT_IDENTIFIER;
		Result result = openAddOrRemoveImputationResult(Boolean.TRUE);
		List<String> imputationsIdentifiers = (List<String>) imputationPersistence.readIdentifiersAsStringsByFilter(filter);
		Object[] array = addOrRemoveImputationInBatch(identifier, existingIgnorable, auditWho, auditIdentifier, auditFunctionality, auditDate, entityManager, result, imputationsIdentifiers, Boolean.TRUE);
		
		Collection<Imputation> imputations = (Collection<Imputation>) array[1];
		closeAddOrRemoveImputationResult(result, "%s %s dans %s","Ajout par filtre de %s par %s","%s ajoutée(s) par filtre", operation, imputations, auditWho);
		return result;
	}
	
	void normalizeFilterForAddOrRemoveImputationByFilter(Filter filter,Boolean add) {
		normalizeFilterForAddOrRemoveByFilter(filter, add, Parameters.ADDED_TO_SPECIFIED_OPERATION);
	}
	
	Object[] addOrRemoveImputationInBatch(String identifier, Boolean existingIgnorable, String auditWho,String auditIdentifier,String auditFunctionality,LocalDateTime auditDate,EntityManager entityManager,Result result,List<String> imputationsIdentifiers,Boolean add) {
		List<List<String>> batches = CollectionHelper.getBatches(imputationsIdentifiers, 1000);
		if(CollectionHelper.isEmpty(batches)) {
			if(batches == null)
				batches = new ArrayList<>();
			batches.add(List.of());
		}
		Operation[] operation = {null};
		Collection<Imputation> imputations = new ArrayList<>();
		batches.parallelStream().forEach(batch -> {
			Object[] array;
			try {
				if(Boolean.TRUE.equals(add))
					array = addImputationInTransaction(identifier,batch ,Boolean.FALSE, existingIgnorable, auditWho, auditIdentifier, auditFunctionality, auditDate, entityManager);
				else
					array = removeImputationInTransaction(identifier,batch ,Boolean.FALSE, existingIgnorable, auditWho, auditIdentifier, auditFunctionality, auditDate, entityManager);
			} catch (Exception exception) {
				result.addMessages(exception.getMessage());
				return;
			}
			if(array == null)
				return;
			if(operation[0] == null)
				operation[0] = (Operation) array[1];
			if(CollectionHelper.isNotEmpty((Collection<Imputation>) array[2]))
				imputations.addAll((Collection<Imputation>) array[2]);
		});
		return new Object[]{operation[0],imputations};
	}
	
	/* remove imputations */
	
	@Transactional
	Object[] removeImputationInTransaction(String identifier, Collection<String> imputationsIdentifiers,Boolean areImputationsIdentifiersRequired, Boolean existingIgnorable,String auditWho,String auditIdentifier,String auditFunctionality,LocalDateTime auditDate
			,EntityManager entityManager) {
		Object[] array = validateImputations(identifier, imputationsIdentifiers,areImputationsIdentifiersRequired, existingIgnorable, Boolean.FALSE, Boolean.FALSE, auditWho);
		Collection<Imputation> imputations = (Collection<Imputation>) array[2];
		Collection<OperationImputationImpl> operationImputations = null;
		if(CollectionHelper.isNotEmpty(imputations))
			for(List<Imputation> batch : CollectionHelper.getBatches((List<Imputation>)imputations, 999)) {
				Collection<OperationImputationImpl> r = CollectionHelper.cast(OperationImputationImpl.class, operationImputationPersistence.readMany(new QueryExecutorArguments()
						.addFilterFieldsValues(Parameters.OPERATION_IDENTIFIER,identifier,Parameters.IMPUTATIONS_IDENTIFIERS,FieldHelper.readSystemIdentifiersAsStrings(batch))));
				if(CollectionHelper.isEmpty(r))
					continue;
				if(operationImputations == null)
					operationImputations = new ArrayList<>();
				operationImputations.addAll(r);
			}
		if(CollectionHelper.isNotEmpty(operationImputations))
			((OperationImputationBusinessImpl)operationImputationBusiness).delete(operationImputations.stream().map(operationImputation -> entityManager.merge(operationImputation)).collect(Collectors.toList())
					, auditIdentifier, REMOVE_IMPUTATION_AUDIT_IDENTIFIER, auditWho, auditDate, entityManager);
		return array;
	}
	
	@Override
	public Result removeImputation(String identifier, Collection<String> imputationsIdentifiers,Boolean existingIgnorable, String auditWho) {
		Result result = openAddOrRemoveImputationResult(Boolean.FALSE);
		Object[] array = removeImputationInTransaction(identifier, imputationsIdentifiers,Boolean.TRUE, existingIgnorable, auditWho, generateAuditIdentifier(), REMOVE_IMPUTATION_AUDIT_IDENTIFIER, LocalDateTime.now(), entityManager);
		OperationImpl operation = (OperationImpl) array[1];
		Collection<Imputation> imputations = (Collection<Imputation>) array[2];
		closeAddOrRemoveImputationResult(result, "%s %s dans %s", "Retrait de %s par %s", "%s retirée(s)", operation, imputations, auditWho);
		return result;
	}
	
	@Override
	public Result removeImputation(String identifier, Boolean existingIgnorable, String auditWho,String... imputationsIdentifiers) {
		return removeImputation(identifier, CollectionHelper.listOf(Boolean.TRUE, imputationsIdentifiers), existingIgnorable, auditWho);
	}
	
	@Override
	public Result removeImputationByFilter(String identifier, Filter filter, Boolean existingIgnorable,String auditWho) {
		ThrowablesMessages throwablesMessages = new ThrowablesMessages();
		Object[] objects = ValidatorImpl.Operation.validateAddOrRemoveToOperationByFilterInputs(identifier, filter, existingIgnorable, auditWho, throwablesMessages);
		throwablesMessages.throwIfNotEmpty();
		Operation operation = (Operation) objects[0];
		ValidatorImpl.Operation.validateAddOrRemoveToOperationByFilter(operation,throwablesMessages);
		throwablesMessages.throwIfNotEmpty();
		
		normalizeFilterForAddOrRemoveImputationByFilter(filter, Boolean.FALSE);
		String auditIdentifier = generateAuditIdentifier();
		LocalDateTime auditDate = LocalDateTime.now();
		String auditFunctionality = REMOVE_IMPUTATION_BY_FILTER_AUDIT_IDENTIFIER;
		Result result = openAddOrRemoveImputationResult(Boolean.TRUE);
		List<String> imputationsIdentifiers = (List<String>) imputationPersistence.readIdentifiersAsStringsByFilter(filter);
		Object[] array = addOrRemoveImputationInBatch(identifier, existingIgnorable, auditWho, auditIdentifier, auditFunctionality, auditDate, entityManager, result, imputationsIdentifiers, Boolean.FALSE);
		
		Collection<Imputation> imputations = (Collection<Imputation>) array[1];
		closeAddOrRemoveImputationResult(result, "%s %s dans %s","Retrait par filtre de %s par %s","%s retirée(s) par filtre", operation, imputations, auditWho);
		return result;
	}
	
	/* Start */
	
	@Transactional
	Result startInTransaction(OperationImpl operation, String auditWho) {
		Result result = new Result().open();
		operation.setStatus(statusPersistence.readOne(new QueryExecutorArguments().addFilterField(Parameters.CODE, configuration.operation().status().startedCode())));
		audit(operation, generateAuditIdentifier(), START_EXECUTION_AUDIT_IDENTIFIER, auditWho, LocalDateTime.now());
		entityManager.merge(operation);
		// Return of message
		String operationLabel = String.format("%s",Operation.NAME,operation.getName());
		result.close().setName(String.format("Démarrage %s par %s",operationLabel,auditWho)).log(getClass());
		result.addMessages(String.format("%s démarrée", operationLabel));
		return result;
	}
	
	@Override
	public Result startExecution(String identifier, String auditWho) {
		ThrowablesMessages throwablesMessages = new ThrowablesMessages();
		// Validation of inputs
		Object[] array = ValidatorImpl.Operation.validateStartInputs(identifier, auditWho, throwablesMessages);
		throwablesMessages.throwIfNotEmpty();
		OperationImpl operation = (OperationImpl) array[0];
		operation = entityManager.find(OperationImpl.class, operation.getIdentifier());
		ValidatorImpl.Operation.validateStart(operation, throwablesMessages);
		throwablesMessages.throwIfNotEmpty();
		Result result = startInTransaction(operation, auditWho);
		eventBus.request(EVENT_CHANNEL_START, new EventMessage(identifier, auditWho));
		return result;
	}
	
	public static final String EVENT_CHANNEL_START = "event_channel_start_operation";
	@ConsumeEvent(EVENT_CHANNEL_START)
    public void listenStart(EventMessage message) {
		execute(message.identifier,message.auditWho);	
    }
	
	void execute(String identifier, String auditWho) {
		if(StringHelper.isBlank(identifier))
			return;
		EntityManager vEntityManager = EntityManagerGetter.getInstance().get();
		OperationImpl operation = vEntityManager.find(OperationImpl.class, identifier);
		if(operation == null) {
			LogHelper.logWarning(String.format("Aucune %s trouvée avec l'identifiant %s",Operation.NAME, identifier), getClass());
			return;
		}
		Collection<Act> acts = actPersistence.readMany(new QueryExecutorArguments().addProjectionsFromStrings(ActImpl.FIELD_IDENTIFIER,ActImpl.FIELD_REFERENCE).addFilterFieldsValues(Parameters.OPERATION_IDENTIFIER, identifier,Parameters.ADDED_TO_SPECIFIED_OPERATION, Boolean.TRUE
				,Parameters.PROCESSED, Boolean.FALSE));
		if(CollectionHelper.isEmpty(acts)) {
			LogHelper.logWarning(String.format("Aucun %s trouvé dans %s avec l'identifiant %s",Act.NAME,Operation.NAME, identifier), getClass());
			return;
		}
		String procedureName = null;
		if(operation.getType().getCode().equals(configuration.operation().type().lockingCode()))
			procedureName = OperationImpl.STORED_PROCEDURE_QUERY_PROCEDURE_NAME_LOCK;
		else if(operation.getType().getCode().equals(configuration.operation().type().unlockingCode()))
			procedureName = OperationImpl.STORED_PROCEDURE_QUERY_PROCEDURE_NAME_UNLOCK;
		if(StringHelper.isBlank(procedureName)) {
			LogHelper.logWarning(String.format("Impossible de déduire le nom de la procédure stockée à exécuter. %s(%s)",Operation.NAME, identifier), getClass());
			return;
		}
		String auditIdentifier = generateAuditIdentifier();
		String auditFunctionnality = EXECUTION_AUDIT_IDENTIFIER;
		LocalDateTime auditWhen = LocalDateTime.now();
		procedureExecutorGetter.getProcedureExecutor().execute(procedureName,OperationImpl.STORED_PROCEDURE_QUERY_PARAMETER_NAME_IDENTIFIERS,acts.stream().map(act -> act.getReference()).collect(Collectors.joining(",")));
		
		Collection<OperationActImpl> operationActs = vEntityManager.createNamedQuery(OperationActImpl.QUERY_READ_BY_OPERATION_IDENTIFIER_BY_ACTS_IDENTIFIERS, OperationActImpl.class).setParameter(Parameters.OPERATION_IDENTIFIER, identifier)
				.setParameter(Parameters.ACTS_IDENTIFIERS, acts.stream().map(act -> act.getIdentifier()).collect(Collectors.toSet())).getResultList();	
		vEntityManager = EntityManagerGetter.getInstance().get();
		vEntityManager.getTransaction().begin();
		for(OperationActImpl operationAct : operationActs) {
			operationAct.setProcessed(Boolean.TRUE);
			audit(operationAct, auditIdentifier, auditFunctionnality, auditWho, auditWhen);
			vEntityManager.merge(operationAct);
		}
		vEntityManager.getTransaction().commit();
		
		operation.setStatus(statusPersistence.readOne(new QueryExecutorArguments().addFilterField(Parameters.CODE, configuration.operation().status().executedCode())));
		audit(operation, auditIdentifier, auditFunctionnality, auditWho, auditWhen);
		vEntityManager = EntityManagerGetter.getInstance().get();
		vEntityManager.getTransaction().begin();
		vEntityManager.merge(operation);
		vEntityManager.getTransaction().commit();
	}
	
	/* Validation */
	
	private Object[] validateActs(String identifier,Collection<String> actsIdentifiers,Boolean areActsIdentifiersRequired,Boolean existingIgnorable,Boolean add,Boolean comprehensively,String auditWho) {
		if(Boolean.TRUE.equals(comprehensively))
			existingIgnorable = Boolean.TRUE;
		ThrowablesMessages throwablesMessages = new ThrowablesMessages();
		Object[] objects = ValidatorImpl.Operation.validateAddOrRemoveActsToOperationInputs(identifier, actsIdentifiers,areActsIdentifiersRequired,comprehensively, auditWho, throwablesMessages);
		throwablesMessages.throwIfNotEmpty();
		
		Collection<Object[]> arrays = new ActImplIdentifierCodeOperationIdentifierReader().readByIdentifiers(actsIdentifiers, Map.of(Parameters.OPERATION_IDENTIFIER,identifier));
		Operation operation = (Operation) objects[0];
		ValidatorImpl.Operation.validateAddOrRemoveActsToOperation(arrays,actsIdentifiers,add,existingIgnorable,operation, throwablesMessages);
		Collection<Act> acts = null;
		if(CollectionHelper.isNotEmpty(actsIdentifiers))
			for(List<String> batch : CollectionHelper.getBatches((List<String>)actsIdentifiers, 999)) {
				Collection<Act> r = actPersistence.readManyByIdentifiers(batch ,List.of(ActImpl.FIELD_IDENTIFIER));
				if(CollectionHelper.isEmpty(r))
					continue;
				if(acts == null)
					acts = new ArrayList<>();
				acts.addAll(r);
			}
		
		ValidatorImpl.validateIdentifiers(List.of(identifier),operation == null ? null : List.of(operation.getIdentifier()), throwablesMessages);
		ValidatorImpl.validateIdentifiers(actsIdentifiers, FieldHelper.readSystemIdentifiersAsStrings(acts), throwablesMessages);
		throwablesMessages.throwIfNotEmpty();
		
		Collection<String> processableIdentifiers = arrays == null ? null : arrays.stream().filter(array -> Boolean.TRUE.equals(add) ? array[2] == null : array[2] != null).map(array -> (String)array[0]).collect(Collectors.toList());
		acts = processableIdentifiers == null ? null : acts.stream().filter(act -> processableIdentifiers.contains(act.getIdentifier())).collect(Collectors.toList());
		return new Object[] {arrays,operation,acts};
	}
	
	Result openAddOrRemoveActResult(Boolean add) {
		return new Result().setName(String.format("%s",Boolean.TRUE.equals(add) ? ADD_ACT_LABEL : REMOVE_ACT_LABEL)).open();
	}
	
	void closeAddOrRemoveActResult(Result result,String labelFormat,String nameFormat,String messageFormat,Operation operation,Collection<Act> acts,String auditWho) {
		String actsLabel = String.format(labelFormat,CollectionHelper.getSize(acts),Act.NAME_PLURAL,operation == null ? "???" : operation.getName());
		result.close().setName(String.format(nameFormat,actsLabel,auditWho)).log(getClass());
		result.addMessages(String.format(messageFormat, actsLabel));
	}
	
	/**/
	
	private Object[] validateImputations(String identifier,Collection<String> imputationsIdentifiers,Boolean areImputationsIdentifiersRequired,Boolean existingIgnorable,Boolean add,Boolean comprehensively,String auditWho) {
		if(Boolean.TRUE.equals(comprehensively))
			existingIgnorable = Boolean.TRUE;
		ThrowablesMessages throwablesMessages = new ThrowablesMessages();
		Object[] objects = ValidatorImpl.Operation.validateAddOrRemoveImputationsToOperationInputs(identifier, imputationsIdentifiers,areImputationsIdentifiersRequired,comprehensively, auditWho, throwablesMessages);
		throwablesMessages.throwIfNotEmpty();
		
		Collection<Object[]> arrays = new ImputationImplIdentifierActivityCodeEconomicNatureCodeOperationIdentifierReader().readByIdentifiers(imputationsIdentifiers, Map.of(Parameters.OPERATION_IDENTIFIER,identifier));
		Operation operation = (Operation) objects[0];
		ValidatorImpl.Operation.validateAddOrRemoveImputationsToOperation(arrays,imputationsIdentifiers,add,existingIgnorable,operation, throwablesMessages);
		Collection<Imputation> imputations = null;
		if(CollectionHelper.isNotEmpty(imputationsIdentifiers))
			for(List<String> batch : CollectionHelper.getBatches((List<String>)imputationsIdentifiers, 999)) {
				Collection<Imputation> r = imputationPersistence.readManyByIdentifiers(batch ,List.of(ImputationImpl.FIELD_IDENTIFIER));
				if(CollectionHelper.isEmpty(r))
					continue;
				if(imputations == null)
					imputations = new ArrayList<>();
				imputations.addAll(r);
			}
		
		ValidatorImpl.validateIdentifiers(List.of(identifier),operation == null ? null : List.of(operation.getIdentifier()), throwablesMessages);
		ValidatorImpl.validateIdentifiers(imputationsIdentifiers, FieldHelper.readSystemIdentifiersAsStrings(imputations), throwablesMessages);
		throwablesMessages.throwIfNotEmpty();
		
		Collection<String> processableIdentifiers = arrays == null ? null : arrays.stream().filter(array -> Boolean.TRUE.equals(add) ? array[3] == null : array[3] != null).map(array -> (String)array[0]).collect(Collectors.toList());
		imputations = processableIdentifiers == null ? null : imputations.stream().filter(imputation -> processableIdentifiers.contains(imputation.getIdentifier())).collect(Collectors.toList());
		return new Object[] {arrays,operation,imputations};
	}
	
	Result openAddOrRemoveImputationResult(Boolean add) {
		return new Result().setName(String.format("%s",Boolean.TRUE.equals(add) ? ADD_IMPUTATION_LABEL : REMOVE_IMPUTATION_LABEL)).open();
	}
	
	void closeAddOrRemoveImputationResult(Result result,String labelFormat,String nameFormat,String messageFormat,Operation operation,Collection<Imputation> imputations,String auditWho) {
		String imputationsLabel = String.format(labelFormat,CollectionHelper.getSize(imputations),Imputation.NAME_PLURAL,operation == null ? "???" : operation.getName());
		result.close().setName(String.format(nameFormat,imputationsLabel,auditWho)).log(getClass());
		result.addMessages(String.format(messageFormat, imputationsLabel));
	}
	
	/**/
	
	@AllArgsConstructor @NoArgsConstructor
	public static class EventMessage {
		String identifier;
		String auditWho;
	}
}
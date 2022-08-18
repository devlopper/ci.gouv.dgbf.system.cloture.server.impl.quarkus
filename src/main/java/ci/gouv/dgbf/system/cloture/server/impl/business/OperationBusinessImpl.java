package ci.gouv.dgbf.system.cloture.server.impl.business;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.object.marker.IdentifiableSystem;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowablesMessages;
import org.cyk.utility.business.Result;
import org.cyk.utility.business.server.AbstractSpecificBusinessImpl;
import org.cyk.utility.persistence.query.QueryExecutorArguments;

import ci.gouv.dgbf.system.cloture.server.api.business.OperationActBusiness;
import ci.gouv.dgbf.system.cloture.server.api.business.OperationBusiness;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Act;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Operation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationActPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationType;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Parameters;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ActImpl;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ActImplIdentifierCodeOperationIdentifierReader;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationActImpl;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationImpl;

@ApplicationScoped
public class OperationBusinessImpl extends AbstractSpecificBusinessImpl<Operation> implements OperationBusiness,Serializable {

	@Inject OperationPersistence persistence;
	@Inject ActPersistence actPersistence;
	@Inject OperationActPersistence operationActPersistence;
	@Inject OperationActBusiness operationActBusiness;
	@Inject EntityManager entityManager;
	
	/* create */
	
	@Override @Transactional
	public Result create(String typeIdentifier,String code,String name, String reason,String auditWho) {
		Result result = new Result().open();
		ThrowablesMessages throwablesMessages = new ThrowablesMessages();
		// Validation of inputs
		Object[] array = ValidatorImpl.Operation.validateCreateInputs(typeIdentifier,name, reason, auditWho, throwablesMessages);
		throwablesMessages.throwIfNotEmpty();
		OperationImpl operation = new OperationImpl();
		operation.setType((OperationType) array[0]).setCode(code).setName(name).setReason(reason);
		if(StringHelper.isBlank(operation.getIdentifier()))
			operation.setIdentifier(operation.getCode());
		if(StringHelper.isBlank(operation.getIdentifier()))
			operation.setIdentifier(IdentifiableSystem.generateRandomly());
		if(StringHelper.isBlank(operation.getName()))
			operation.setName(operation.getCode());
		audit(operation, generateAuditIdentifier(), CREATE_AUDIT_IDENTIFIER, auditWho, LocalDateTime.now());
		entityManager.persist(operation);
		// Return of message
		String operationLabel = String.format("%s de type %s",Operation.NAME,operation.getType().getName());
		result.close().setName(String.format("Création %s par %s",operationLabel,auditWho)).log(getClass());
		result.addMessages(String.format("%s créée", operationLabel));
		return result;
	}
	
	/* add */

	@Override @Transactional
	public Result addAct(String identifier, Collection<String> actsIdentifiers,Boolean existingIgnorable, String auditWho) {
		Object[] array = validate(identifier, actsIdentifiers, existingIgnorable, Boolean.TRUE, Boolean.FALSE, auditWho);
		OperationImpl operation = (OperationImpl) array[1];
		Collection<Act> acts = (Collection<Act>) array[2];
		Result result = (Result) array[3];
		if(CollectionHelper.isNotEmpty(acts)) {
			String auditIdentifier = generateAuditIdentifier();
			LocalDateTime auditDate = LocalDateTime.now();
			((OperationActBusinessImpl)operationActBusiness).create(operation, acts, auditIdentifier, ADD_ACT_AUDIT_IDENTIFIER, auditWho, auditDate, entityManager);
		}
		// Return of message
		String actsLabel = String.format("%s %s dans %s",CollectionHelper.getSize(acts),Act.NAME_PLURAL,operation.getName());
		result.close().setName(String.format("Ajout de %s par %s",actsLabel,auditWho)).log(getClass());
		result.addMessages(String.format("%s ajouté(s)", actsLabel));
		return result;
	}

	@Override @Transactional
	public Result addAct(String identifier,Boolean existingIgnorable, String auditWho, String... actsIdentifiers) {
		return addAct(identifier,CollectionHelper.listOf(Boolean.TRUE, actsIdentifiers),existingIgnorable,auditWho);
	}

	@Override @Transactional
	public Result addActComprehensively(String identifier, Collection<String> actsIdentifiers, String auditWho) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override @Transactional
	public Result addActComprehensively(String identifier, String auditWho, String... actsIdentifiers) {
		return addActComprehensively(identifier,CollectionHelper.listOf(Boolean.TRUE, actsIdentifiers),auditWho);
	}
	
	/* remove */
	
	@Override @Transactional
	public Result removeAct(String identifier, Collection<String> actsIdentifiers, Boolean existingIgnorable,String auditWho) {
		Object[] array = validate(identifier, actsIdentifiers, existingIgnorable, Boolean.FALSE, Boolean.FALSE, auditWho);
		OperationImpl operation = (OperationImpl) array[1];
		Collection<Act> acts = (Collection<Act>) array[2];
		Collection<OperationActImpl> operationActs = CollectionHelper.isEmpty(acts) ? null : CollectionHelper.cast(OperationActImpl.class, operationActPersistence.readMany(new QueryExecutorArguments()
				.addFilterFieldsValues(Parameters.OPERATION_IDENTIFIER,identifier,Parameters.ACTS_IDENTIFIERS,FieldHelper.readSystemIdentifiersAsStrings(acts))));
		Result result = (Result) array[3];
		if(CollectionHelper.isNotEmpty(operationActs)) {
			String auditIdentifier = generateAuditIdentifier();
			LocalDateTime auditDate = LocalDateTime.now();
			((OperationActBusinessImpl)operationActBusiness).delete(operationActs.stream().map(operationAct -> entityManager.merge(operationAct)).collect(Collectors.toList())
					, auditIdentifier, REMOVE_ACT_AUDIT_IDENTIFIER, auditWho, auditDate, entityManager);
		}
		// Return of message
		String operationActsLabel = String.format("%s %s dans %s",CollectionHelper.getSize(operationActs),Act.NAME_PLURAL,operation.getName());
		result.close().setName(String.format("Retrait de %s par %s",operationActsLabel,auditWho)).log(getClass());
		result.addMessages(String.format("%s retiré(s)", operationActsLabel));
		return result;
	}
	
	@Override @Transactional
	public Result removeAct(String identifier, Boolean existingIgnorable, String auditWho, String... actsIdentifiers) {
		return removeAct(identifier, CollectionHelper.listOf(Boolean.TRUE, actsIdentifiers), existingIgnorable, auditWho);
	}
	
	/* Validation */
	
	private Object[] validate(String identifier,Collection<String> actsIdentifiers,Boolean existingIgnorable,Boolean add,Boolean comprehensively,String auditWho) {
		ThrowablesMessages throwablesMessages = new ThrowablesMessages();
		ValidatorImpl.Operation.validateAddOrRemoveToOperationInputs(identifier, actsIdentifiers,comprehensively, auditWho, throwablesMessages);
		throwablesMessages.throwIfNotEmpty();
		
		Collection<Object[]> arrays = new ActImplIdentifierCodeOperationIdentifierReader().readByIdentifiers(actsIdentifiers, Map.of(Parameters.OPERATION_IDENTIFIER,identifier));
		Operation operation = persistence.readOne(identifier,List.of(OperationImpl.FIELD_IDENTIFIER,OperationImpl.FIELD_CODE,OperationImpl.FIELD_NAME));
		ValidatorImpl.Operation.validateAddOrRemoveToOperation(arrays,actsIdentifiers,add,existingIgnorable,operation, throwablesMessages);
		Collection<Act> acts = actPersistence.readManyByIdentifiers(actsIdentifiers ,List.of(ActImpl.FIELD_IDENTIFIER));
		
		ValidatorImpl.validateIdentifiers(List.of(identifier),operation == null ? null : List.of(operation.getIdentifier()), throwablesMessages);
		ValidatorImpl.validateIdentifiers(actsIdentifiers, FieldHelper.readSystemIdentifiersAsStrings(acts), throwablesMessages);
		throwablesMessages.throwIfNotEmpty();
		
		Result result = new Result().setName(String.format("%s",Boolean.TRUE.equals(add) ? ADD_ACT_LABEL : REMOVE_ACT_LABEL)).open();
		Collection<String> processableIdentifiers = arrays.stream().filter(array -> Boolean.TRUE.equals(add) ? array[2] == null : array[2] != null).map(array -> (String)array[0]).collect(Collectors.toList());
		acts = acts.stream().filter(act -> processableIdentifiers.contains(act.getIdentifier())).collect(Collectors.toList());
		return new Object[] {arrays,operation,acts,result};
	}
}
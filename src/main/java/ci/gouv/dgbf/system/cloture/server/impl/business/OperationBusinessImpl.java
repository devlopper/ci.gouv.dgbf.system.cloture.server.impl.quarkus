package ci.gouv.dgbf.system.cloture.server.impl.business;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.cyk.utility.__kernel__.object.marker.IdentifiableSystem;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowablesMessages;
import org.cyk.utility.business.Result;
import org.cyk.utility.business.server.AbstractSpecificBusinessImpl;

import ci.gouv.dgbf.system.cloture.server.api.business.OperationBusiness;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Operation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationType;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationImpl;

@ApplicationScoped
public class OperationBusinessImpl extends AbstractSpecificBusinessImpl<Operation> implements OperationBusiness,Serializable {

	@Inject OperationPersistence persistence;
	@Inject EntityManager entityManager;
	
	@Override @Transactional
	public Result create(String typeIdentifier,String code,String name, String reason,String auditWho) {
		Result result = new Result().open();
		ThrowablesMessages throwablesMessages = new ThrowablesMessages();
		// Validation of inputs
		Object[] array = ValidatorImpl.Operation.validateCreateInputs(typeIdentifier,name, reason, auditWho, throwablesMessages);
		throwablesMessages.throwIfNotEmpty();
		OperationImpl operation = new OperationImpl();
		operation.setIdentifier(IdentifiableSystem.generateRandomly()).setType((OperationType) array[0]).setCode(code).setName(name).setReason(reason);
		if(StringHelper.isBlank(operation.getName()))
			operation.setName(operation.getCode());
		audit(operation, generateAuditIdentifier(), CREATE_AUDIT_IDENTIFIER, auditWho, LocalDateTime.now());
		entityManager.persist(operation);
		// Return of message
		result.close().setName(String.format("Création d'une opération de type %s par %s",operation.getType().getName(),auditWho)).log(getClass());
		result.addMessages(String.format("Opération de type %s créée", operation.getType().getName()));
		return result;
	}
}
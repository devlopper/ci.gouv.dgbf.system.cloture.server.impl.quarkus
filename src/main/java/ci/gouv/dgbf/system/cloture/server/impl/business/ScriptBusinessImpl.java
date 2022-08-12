package ci.gouv.dgbf.system.cloture.server.impl.business;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.business.Validator;
import org.cyk.utility.business.server.AbstractSpecificBusinessImpl;
import org.cyk.utility.persistence.EntityManagerGetter;

import ci.gouv.dgbf.system.cloture.server.api.business.ScriptBusiness;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Script;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationExecutionStatus;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ScriptPersistence;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ScriptImpl;
import io.quarkus.vertx.ConsumeEvent;
import io.vertx.core.eventbus.EventBus;

@ApplicationScoped
public class ScriptBusinessImpl extends AbstractSpecificBusinessImpl<Script> implements ScriptBusiness,Serializable {

	@Inject EntityManager entityManager;
	@Inject ScriptPersistence operationPersistence;
	@Inject @ci.gouv.dgbf.system.cloture.server.api.System Validator validator;
	@Inject EventBus eventBus;
	
	private void executeProcedure(Script operation,EntityManager entityManager,Boolean isUserTransaction) {
		LogHelper.logInfo(String.format("Exécution de l'opération [%s] par [%s] en cours", operation.getName(),operation.getTrigger()), getClass());
		operationPersistence.executeProcedure(operation.getProcedureName());
		operation.setExecutionStatus(OperationExecutionStatus.EXECUTEE);
		operation.setExecutionEndDate(LocalDateTime.now());
		if(Boolean.TRUE.equals(isUserTransaction))
			entityManager.getTransaction().begin();
		entityManager.merge(operation);
		if(Boolean.TRUE.equals(isUserTransaction))
			entityManager.getTransaction().commit();
		LogHelper.logInfo(String.format("Exécution de l'opération [%s] par [%s] terminée", operation.getName(),operation.getTrigger()), getClass());
	}
	
	public static final String EVENT_CHANNEL_EXECUTE_PROCEDURE = "execute_procedure";
	@ConsumeEvent(EVENT_CHANNEL_EXECUTE_PROCEDURE)
    public void listenExecute(String identifier) {
		EntityManager entityManager = EntityManagerGetter.getInstance().get();
		executeProcedure(entityManager.find(ScriptImpl.class, identifier), entityManager,Boolean.TRUE);
    }
	
	public static final String EXECUTE = "EXECUTE";
	@Override @Transactional
	public void execute(String identifier, String trigger,Boolean blocking) {
		if(StringHelper.isBlank(identifier))
			throw new RuntimeException(String.format("L'identifiant de l'opération à exécuter est obligatoire"));
		Script operation = entityManager.find(ScriptImpl.class, identifier);
		if(operation == null)
			throw new RuntimeException(String.format("L'opération à exécuter est obligatoire"));
		operation.setTrigger(trigger);
		throwIfNotEmpty(validator.validate(Script.class,List.of(operation), EXECUTE));
		operation.setExecutionStatus(OperationExecutionStatus.EN_COURS);
		operation.setExecutionBeginDate(LocalDateTime.now());
		operation.setExecutionEndDate(null);
		entityManager.merge(operation);		
		if(Boolean.FALSE.equals(blocking)) {
			entityManager.flush();
			eventBus.request(EVENT_CHANNEL_EXECUTE_PROCEDURE, identifier);
		}else
			executeProcedure(operation,entityManager,null);
	}
	
	@Override @Transactional
	public void execute(String identifier, String trigger) {
		execute(identifier, trigger, Boolean.TRUE);
	}
}
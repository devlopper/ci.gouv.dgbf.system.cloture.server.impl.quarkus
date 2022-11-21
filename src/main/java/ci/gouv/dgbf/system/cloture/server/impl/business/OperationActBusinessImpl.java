package ci.gouv.dgbf.system.cloture.server.impl.business;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;

import org.cyk.utility.business.server.AbstractSpecificBusinessImpl;

import ci.gouv.dgbf.system.cloture.server.api.business.OperationActBusiness;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Act;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Operation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationAct;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationActImpl;

@ApplicationScoped
public class OperationActBusinessImpl extends AbstractSpecificBusinessImpl<OperationAct> implements OperationActBusiness,Serializable {

	/* create */
	
	void create(Operation operation,Collection<Act> acts,String auditIdentifier,String auditFunctionality, String auditWho,LocalDateTime auditDate,EntityManager entityManager) {
		acts.forEach(act -> {
			OperationActImpl operationAct = new OperationActImpl();
			operationAct.setIdentifier(String.format("%s_%s", operation.getIdentifier(),act.getIdentifier())).setAct(act).setOperation(operation);
			audit(operationAct, auditIdentifier, auditFunctionality, auditWho, auditDate);
			entityManager.persist(operationAct);
		});
	}
	
	void create(Operation operation,Act act,String auditIdentifier,String auditFunctionality, String auditWho,LocalDateTime auditDate,EntityManager entityManager) {
		create(operation, List.of(act), auditIdentifier, auditFunctionality, auditWho, auditDate, entityManager);
	}
	
	/* delete */
	
	void delete(Collection<OperationActImpl> operationActs,String auditIdentifier,String auditFunctionality, String auditWho,LocalDateTime auditDate,EntityManager entityManager) {
		operationActs.forEach(operationAct -> {
			audit(operationAct, auditIdentifier, auditFunctionality, auditWho, auditDate);
			entityManager.remove(operationAct);
		});
	}
}
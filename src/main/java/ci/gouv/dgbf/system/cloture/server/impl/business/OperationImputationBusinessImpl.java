package ci.gouv.dgbf.system.cloture.server.impl.business;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;

import org.cyk.utility.business.server.AbstractSpecificBusinessImpl;

import ci.gouv.dgbf.system.cloture.server.api.business.OperationImputationBusiness;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Imputation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Operation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationImputation;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationImputationImpl;

@ApplicationScoped
public class OperationImputationBusinessImpl extends AbstractSpecificBusinessImpl<OperationImputation> implements OperationImputationBusiness,Serializable {

	/* create */
	
	void create(Operation operation,Collection<Imputation> imputations,String auditIdentifier,String auditFunctionality, String auditWho,LocalDateTime auditDate,EntityManager entityManager) {
		imputations.forEach(act -> {
			OperationImputationImpl operationImputation = new OperationImputationImpl();
			operationImputation.setIdentifier(String.format("%s_%s", operation.getIdentifier(),act.getIdentifier())).setImputation(act).setOperation(operation);
			audit(operationImputation, auditIdentifier, auditFunctionality, auditWho, auditDate);
			entityManager.persist(operationImputation);
		});
	}
	
	void create(Operation operation,Imputation imputation,String auditIdentifier,String auditFunctionality, String auditWho,LocalDateTime auditDate,EntityManager entityManager) {
		create(operation, List.of(imputation), auditIdentifier, auditFunctionality, auditWho, auditDate, entityManager);
	}
	
	/* delete */
	
	void delete(Collection<OperationImputationImpl> operationImputations,String auditIdentifier,String auditFunctionality, String auditWho,LocalDateTime auditDate,EntityManager entityManager) {
		operationImputations.forEach(operationImputation -> {
			audit(operationImputation, auditIdentifier, auditFunctionality, auditWho, auditDate);
			entityManager.remove(operationImputation);
		});
	}
}
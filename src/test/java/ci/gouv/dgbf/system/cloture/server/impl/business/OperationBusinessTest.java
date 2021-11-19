package ci.gouv.dgbf.system.cloture.server.impl.business;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.time.TimeHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ci.gouv.dgbf.system.cloture.server.api.business.OperationBusiness;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Operation;
import ci.gouv.dgbf.system.cloture.server.impl.Profiles;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationImpl;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(Profiles.Business.Default.class)
public class OperationBusinessTest {

	@Inject ci.gouv.dgbf.system.cloture.server.impl.Assertor assertor;
	@Inject OperationBusiness operationBusiness;
	@Inject EntityManager entityManager;
	
	@Test
	void execute_startDateIsPassed_blocking() {
		String identifier = "OPassedBlocking";
		Operation operation = entityManager.find(OperationImpl.class, identifier);
		assertThat(operation.getExecutionBeginDate()).as("date début exécution est nulle").isNull();
		operationBusiness.execute(identifier, "user01");
		entityManager.clear();
		operation = entityManager.find(OperationImpl.class, identifier);
		assertThat(operation.getExecutionBeginDate()).as("date début exécution est non nulle").isNotNull();
	}
	
	@Test
	void execute_startDateIsPassed_nonBlocking() {
		String identifier = "OPassedNonBlocking";
		Operation operation = entityManager.find(OperationImpl.class, identifier);
		assertThat(operation.getExecutionBeginDate()).as("date début exécution est nulle").isNull();
		operationBusiness.execute(identifier, "user01",Boolean.FALSE);
		entityManager.clear();
		operation = entityManager.find(OperationImpl.class, identifier);
		assertThat(operation.getExecutionEndDate()).as("date fin exécution est nulle").isNull();
		
		TimeHelper.pause(9 * 1000l);
		
		entityManager.clear();
		operation = entityManager.find(OperationImpl.class, identifier);
		assertThat(operation.getExecutionEndDate()).as("date fin exécution est non nulle").isNotNull();
	}
	
	@Test
	void execute_startDateIsFuture() {
		RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
			operationBusiness.execute("OFuture", "user01");
		});
		assertThat(exception.getMessage()).isEqualTo("L'opération [Verrouiller] ne peut être démarrée qu'à partir du 01/01/2099 à 00:00");
	}
}
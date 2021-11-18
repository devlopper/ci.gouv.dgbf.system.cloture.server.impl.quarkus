package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import org.junit.jupiter.api.Test;

import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationGroupPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationPersistence;
import ci.gouv.dgbf.system.cloture.server.impl.Profiles;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(Profiles.Persistence.Default.class)
public class PersistenceTest {

	@Inject ci.gouv.dgbf.system.cloture.server.impl.Assertor assertor;
	@Inject OperationGroupPersistence operationGroupPersistence;
	@Inject OperationPersistence operationPersistence;
	@Inject UserTransaction userTransaction;
	@Inject EntityManager entityManager;
	
	@Test
	void readOperationGroupMany() {
		assertor.assertIdentifiers(operationGroupPersistence.readMany(null, null, null), List.of("E","L","O","PC"));
	}
	
	@Test
	void readOperationMany() {
		assertor.assertIdentifiers(operationPersistence.readMany(null, null, null), List.of("V01","V02","V03"));
	}
}
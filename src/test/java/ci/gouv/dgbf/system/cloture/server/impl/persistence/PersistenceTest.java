package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.junit.jupiter.api.Test;

import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationGroupPersistence;
import ci.gouv.dgbf.system.cloture.server.impl.Profiles;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(Profiles.Persistence.Default.class)
public class PersistenceTest {

	@Inject ci.gouv.dgbf.system.cloture.server.impl.Assertor assertor;
	@Inject OperationGroupPersistence operationGroupPersistence;
	@Inject UserTransaction userTransaction;
	
	@Test
	void readOperationGroupMany() {
		assertor.assertIdentifiers(operationGroupPersistence.readMany(null, null, null), List.of("E","L","O","PC"));
	}

}
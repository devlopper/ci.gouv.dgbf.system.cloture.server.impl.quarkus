package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.junit.jupiter.api.Test;

import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationPersistence;
import ci.gouv.dgbf.system.cloture.server.impl.Profiles;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(Profiles.Persistence.Default.class)
public class OperationPersistenceTest {

	@Inject ci.gouv.dgbf.system.cloture.server.impl.Assertor assertor;
	@Inject OperationPersistence operationPersistence;
	@Inject UserTransaction userTransaction;
	
	@Test
	void executeProcedure() {
		try {
			userTransaction.begin();
			operationPersistence.executeProcedure("p01");
			userTransaction.commit();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static void dbmsProcedure(String name) {
		System.out.println("OperationPersistenceTest.dbmsProcedure() : "+name);
	}
}
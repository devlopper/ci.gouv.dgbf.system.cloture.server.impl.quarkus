package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.junit.jupiter.api.Test;

import ci.gouv.dgbf.system.cloture.server.api.persistence.ScriptPersistence;
import ci.gouv.dgbf.system.cloture.server.impl.Procedures;
import ci.gouv.dgbf.system.cloture.server.impl.Profiles;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(Profiles.Persistence.Default.class)
public class OperationPersistenceTest {

	@Inject ci.gouv.dgbf.system.cloture.server.impl.Assertor assertor;
	@Inject ScriptPersistence operationPersistence;
	@Inject UserTransaction userTransaction;
	
	@Test
	void executeProcedure() {
		try {
			userTransaction.begin();
			operationPersistence.executeProcedure("p01");
			userTransaction.commit();
			assertThat(Boolean.TRUE).isTrue();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	@Test
	void executeProcedure_unknownName() {
		try {
			userTransaction.begin();
			operationPersistence.executeProcedure(Procedures.UNKNOWN_NAME);
			userTransaction.commit();
		} catch (Exception exception) {
			assertThat(exception.getCause().getCause().getMessage().contains("Cette procédure n'existe pas")).isTrue();
			try {
				userTransaction.rollback();
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
	}
}
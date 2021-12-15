package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.junit.jupiter.api.Test;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Act;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActType;
import ci.gouv.dgbf.system.cloture.server.impl.Profiles;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(Profiles.Persistence.Act.class)
public class ActPersistenceTest {

	@Inject ci.gouv.dgbf.system.cloture.server.impl.Assertor assertor;
	@Inject ActPersistence actPersistence;
	@Inject UserTransaction userTransaction;
	
	@Test
	void get_one() {
		Act act = actPersistence.readOne("1", List.of("code","name","type"));
		assertThat(act).isNotNull();
		assertThat(act.getCode()).isEqualTo("1");
		assertThat(act.getName()).isEqualTo("1");
		assertThat(act.getType()).isEqualTo(ActType.ENGAGEMENT);
	}
	
	@Test
	void lock() {
		try {
			userTransaction.begin();
			actPersistence.lock("lockable","CHANGEMENT_%","T_ENGAGEMENT");
			userTransaction.commit();
			assertThat(Boolean.TRUE).isTrue();
		} catch (Exception exception) {
			exception.printStackTrace();
			assertThat(Boolean.TRUE).isFalse();
		}
	}
	
	@Test
	void lock_error() {
		try {
			userTransaction.begin();
			actPersistence.lock("XXX","CHANGEMENT_%","T_ENGAGEMENT");
			userTransaction.commit();
		} catch (Exception exception) {
			exception.printStackTrace();
			try {
				userTransaction.rollback();
			} catch (Exception e) {
				e.printStackTrace();
			}
			assertThat(Boolean.TRUE).isTrue();
		}
	}
	
	@Test
	void unlock() {
		try {
			userTransaction.begin();
			actPersistence.unlock("unlockable","CHANGEMENT_%","T_ENGAGEMENT");
			userTransaction.commit();
			assertThat(Boolean.TRUE).isTrue();
		} catch (Exception exception) {
			exception.printStackTrace();
			assertThat(Boolean.TRUE).isFalse();
		}
	}
	
	@Test
	void unlock_error() {
		try {
			userTransaction.begin();
			actPersistence.unlock("XXX","CHANGEMENT_%","T_ENGAGEMENT");
			userTransaction.commit();
		} catch (Exception exception) {
			exception.printStackTrace();
			try {
				userTransaction.rollback();
			} catch (Exception e) {
				e.printStackTrace();
			}
			assertThat(Boolean.TRUE).isTrue();
		}
	}
}
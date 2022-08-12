package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.junit.jupiter.api.Test;

import ci.gouv.dgbf.system.cloture.server.api.persistence.ActLockPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActPersistence;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
//@TestProfile(Profiles.Persistence.Act.class)
public class ActPersistenceTest {

	@Inject ci.gouv.dgbf.system.cloture.server.impl.Assertor assertor;
	@Inject ActPersistence actPersistence;
	@Inject ActLockPersistence actLockPersistence;
	@Inject UserTransaction userTransaction;
	
	/*@Test
	void get_one() {
		ActImpl act = (ActImpl) actPersistence.readOne("1", List.of("identifier",ActImpl.FIELDS_CODE_NAME_TYPE_STRING_NUMBER_OF_LOCKS_ENABLED_STATUS_STRING_LATEST_OPERATION,ActImpl.FIELD_LOCKED_REASONS));
		assertThat(act).isNotNull();
		assertThat(act.getCode()).isEqualTo("1");
		assertThat(act.getName()).isEqualTo("1");
		assertThat(act.getTypeString()).isEqualTo(ActTypeEnum.ENGAGEMENT.getLabel());
		assertThat(act.getLatestOperationString()).isEqualTo("Déverouillage le 02/01/2000 à 00:00 par christian");
		assertThat(act.getLockedReasons()).containsExactly("Raison 01","Raison 02");
	}*/
	
	@Test
	void get_one_lock() {
		ActLockImpl actLock = (ActLockImpl) actLockPersistence.readOne("1", List.of(ActLockImpl.FIELD_IDENTIFIER
				,ActLockImpl.FIELDS_REASON_ENABLED_ENABLED_AS_STRING_BEGIN_DATE_STRING_END_DATE_STRING_LATEST_OPERATION));
		assertThat(actLock).as("verrou 1 existe").isNotNull();
		assertThat(actLock.getEnabled()).as("verrou actif").isEqualTo(Boolean.TRUE);		
		assertThat(actLock.getEnabledString()).as("verrou actif chaine").isEqualTo("Oui");
		assertThat(actLock.getBeginDateString()).as("date debut").isEqualTo("01/01/2000 à 00:00");
		assertThat(actLock.getEndDateString()).as("date fin").isEqualTo("02/01/2000 à 00:00");
		assertThat(actLock.getLatestOperation()).isEqualTo("Déverouillage le 02/01/2000 à 00:00 par christian");
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
package ci.gouv.dgbf.system.cloture.server.impl.business;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ci.gouv.dgbf.system.cloture.server.api.business.ActBusiness;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Act;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActOperation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActOperationType;
import ci.gouv.dgbf.system.cloture.server.impl.Profiles;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ActImpl;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ActOperationImpl;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(Profiles.Business.Act.class)
public class ActBusinessTest {

	@Inject ci.gouv.dgbf.system.cloture.server.impl.Assertor assertor;
	@Inject ActBusiness actBusiness;
	@Inject EntityManager entityManager;
	
	@BeforeEach
	void listenBeforeEach() {
		ActBusinessImpl.PRODUCTION = Boolean.FALSE;
	}
	
	//@Test
	void lock() {
		String identifier = "not_yet_operated_01";
		Act act = entityManager.find(ActImpl.class, identifier);
		assertThat(act.getOperationType()).as("acte pas encore verouillé").isNull();
		actBusiness.lock("user01",identifier);
		entityManager.clear();
		act = entityManager.find(ActImpl.class, identifier);
		ActOperation actOperation = entityManager.createQuery("SELECT t FROM ActOperationImpl t WHERE t.actIdentifier = :actIdentifier", ActOperationImpl.class)
				.setParameter("actIdentifier", identifier).getSingleResult();
		assertThat(actOperation.getOperationType()).as("acte verrouillé").isEqualTo(ActOperationType.VERROUILLAGE);
	}
	
	@Test
	void unlock() {
		String identifier = "not_yet_operated_02";
		Act act = entityManager.find(ActImpl.class, identifier);
		assertThat(act.getOperationType()).as("acte pas encore déverouillé").isNull();
		actBusiness.unlock("user01",identifier);
		entityManager.clear();
		act = entityManager.find(ActImpl.class, identifier);
		ActOperation actOperation = entityManager.createQuery("SELECT t FROM ActOperationImpl t WHERE t.actIdentifier = :actIdentifier", ActOperationImpl.class)
				.setParameter("actIdentifier", identifier).getSingleResult();
		assertThat(actOperation.getOperationType()).as("acte déverrouillé").isEqualTo(ActOperationType.DEVERROUILLAGE);
	}
	
	@Test
	void unlock_not_yet_operated_g01_() {
		String identifier1 = "not_yet_operated_g01_01";
		Act act1 = entityManager.find(ActImpl.class, identifier1);
		assertThat(act1.getOperationType()).as("acte pas encore déverouillé").isNull();
		
		String identifier2 = "not_yet_operated_g01_02";
		Act act2 = entityManager.find(ActImpl.class, identifier2);
		assertThat(act2.getOperationType()).as("acte pas encore déverouillé").isNull();
		
		actBusiness.unlock("user01",identifier1,identifier2);
		entityManager.clear();
		
		act1 = entityManager.find(ActImpl.class, identifier1);
		ActOperation actOperation1 = entityManager.createQuery("SELECT t FROM ActOperationImpl t WHERE t.actIdentifier = :actIdentifier", ActOperationImpl.class)
				.setParameter("actIdentifier", identifier1).getSingleResult();
		assertThat(actOperation1.getOperationType()).as("acte déverrouillé").isEqualTo(ActOperationType.DEVERROUILLAGE);
		
		act2 = entityManager.find(ActImpl.class, identifier2);
		ActOperation actOperation2 = entityManager.createQuery("SELECT t FROM ActOperationImpl t WHERE t.actIdentifier = :actIdentifier", ActOperationImpl.class)
				.setParameter("actIdentifier", identifier2).getSingleResult();
		assertThat(actOperation2.getOperationType()).as("acte déverrouillé").isEqualTo(ActOperationType.DEVERROUILLAGE);
	}
	
	/*
	@Test
	void unlock() {
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
	*/
	@Test
	void lock_alreadyLocked() {
		String identifier = "locked01";
		RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
			actBusiness.lock("user01", identifier);
		});
		assertThat(exception.getMessage()).isEqualTo("La fonctionnalité de verouillage n'est pas encore implémentée");
	}
	
	@Test
	void unlock_alreadyUnlocked() {
		String identifier = "unlocked01";
		RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
			actBusiness.unlock("user01", identifier);
		});
		assertThat(exception.getMessage()).isEqualTo("Les actes suivants ne sont pas verouillés : unlocked01 1");
	}
}
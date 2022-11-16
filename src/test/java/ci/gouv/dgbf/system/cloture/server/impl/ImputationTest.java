package ci.gouv.dgbf.system.cloture.server.impl;

import java.util.List;

import javax.inject.Inject;

import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.junit.jupiter.api.Test;

import ci.gouv.dgbf.system.cloture.server.api.persistence.ImputationPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Parameters;
import ci.gouv.dgbf.system.cloture.server.client.rest.ImputationController;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(Profiles.Imputation.class)
public class ImputationTest {

	@Inject Assertor assertor;
	@Inject ImputationPersistence persistence;
	@Inject ImputationController controller;
	
	@Test
	void persistence_readMany() {
		assertor.assertIdentifiers(persistence.readMany(new QueryExecutorArguments()), List.of("6","7","8","9","10","11","1","2","3","4","5"));
	}
	
	@Test
	void persistence_readMany_exercise_2021() {
		assertor.assertIdentifiers(persistence.readMany(new QueryExecutorArguments().addFilterField(Parameters.EXERCISE_IDENTIFIER, "2021")), List.of("1","2","3","4","5"));
	}
	
	@Test
	void persistence_readMany_exercise_2021_activity_2() {
		assertor.assertIdentifiers(persistence.readMany(new QueryExecutorArguments().addFilterField(Parameters.EXERCISE_IDENTIFIER, "2021").addFilterField(Parameters.ACTIVITY_IDENTIFIER, "2")), List.of("3","4","5"));
	}
	
	@Test
	void persistence_readMany_exercise_2022() {
		assertor.assertIdentifiers(persistence.readMany(new QueryExecutorArguments().addFilterField(Parameters.EXERCISE_IDENTIFIER, "2022")), List.of("6","7","8","9","10","11"));
	}
	
	@Test
	void persistence_readMany_exercise_2022_activity_2() {
		assertor.assertIdentifiers(persistence.readMany(new QueryExecutorArguments().addFilterField(Parameters.EXERCISE_IDENTIFIER, "2022").addFilterField(Parameters.ACTIVITY_IDENTIFIER, "2")), List.of("8","9","10","11"));
	}
}
package ci.gouv.dgbf.system.cloture.server.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.service.client.Controller;
import org.junit.jupiter.api.Test;

import ci.gouv.dgbf.system.cloture.server.api.persistence.ExercisePersistence;
import ci.gouv.dgbf.system.cloture.server.api.service.ExerciseDto;
import ci.gouv.dgbf.system.cloture.server.client.rest.Exercise;
import ci.gouv.dgbf.system.cloture.server.client.rest.ExerciseController;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ExerciseImpl;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(Profiles.Exercise.class)
public class ExerciseTest {

	@Inject Assertor assertor;
	@Inject ExercisePersistence persistence;
	@Inject ExerciseController controller;
	
	@Test
	void persistence_readMany() {
		Collection<ci.gouv.dgbf.system.cloture.server.api.persistence.Exercise> exercises = persistence.readMany(new QueryExecutorArguments()
				.addProjectionsFromStrings(ExerciseImpl.FIELD_IDENTIFIER,ExerciseImpl.FIELD_CODE,ExerciseImpl.FIELD_NAME,ExerciseImpl.FIELD_YEAR));
		assertThat(exercises).isNotNull();
		assertThat(exercises.stream().map(x -> x.getIdentifier()).collect(Collectors.toList())).containsExactly("2022","2021");
		assertThat(exercises.stream().map(x -> x.getCode()).collect(Collectors.toList())).containsExactly("2022","2021");
		assertThat(exercises.stream().map(x -> x.getName()).collect(Collectors.toList())).containsExactly("2022","2021");
		assertThat(exercises.stream().map(x -> x.getYear()).collect(Collectors.toList())).containsExactly(Short.valueOf("2022"),Short.valueOf("2021"));
	}
	
	@Test
	void controller_readMany() {
		Collection<Exercise> exercises = controller.get(new Controller.GetArguments().projections(ExerciseDto.JSON_IDENTIFIER,ExerciseDto.JSON_YEAR));
		assertThat(exercises).isNotNull();
		assertThat(exercises.stream().map(x -> x.getIdentifier()).collect(Collectors.toList())).containsExactly("2022","2021");
		assertThat(exercises.stream().map(x -> x.getYear()).collect(Collectors.toList())).containsExactly(Short.valueOf("2022"),Short.valueOf("2021"));
	}
}
package ci.gouv.dgbf.system.cloture.server.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.cyk.utility.persistence.query.Filter;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.service.client.Controller;
import org.junit.jupiter.api.Test;

import ci.gouv.dgbf.system.cloture.server.api.persistence.ActivityPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Parameters;
import ci.gouv.dgbf.system.cloture.server.client.rest.Activity;
import ci.gouv.dgbf.system.cloture.server.client.rest.ActivityController;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(Profiles.Activity.class)
public class ActivityTest {

	@Inject Assertor assertor;
	@Inject ActivityPersistence persistence;
	@Inject ActivityController controller;
	
	@Test
	void persistence_readMany() {
		Collection<ci.gouv.dgbf.system.cloture.server.api.persistence.Activity> activities = persistence.readMany(new QueryExecutorArguments());
		assertThat(activities).isNotNull();
		assertThat(activities.stream().map(x -> x.getCode()).collect(Collectors.toList())).containsExactly("1","2","3","4","5","6");
	}
	
	@Test
	void persistence_readMany_search_code() {
		Collection<ci.gouv.dgbf.system.cloture.server.api.persistence.Activity> activities = persistence.readMany(new QueryExecutorArguments().addFilterField(persistence.getParameterNameSearch(), "5"));
		assertThat(activities).isNotNull();
		assertThat(activities.stream().map(x -> x.getCode()).collect(Collectors.toList())).containsExactly("5");
	}
	
	@Test
	void persistence_readMany_search_name() {
		Collection<ci.gouv.dgbf.system.cloture.server.api.persistence.Activity> activities = persistence.readMany(new QueryExecutorArguments().addFilterField(persistence.getParameterNameSearch(), "daloa"));
		assertThat(activities).isNotNull();
		assertThat(activities.stream().map(x -> x.getCode()).collect(Collectors.toList())).containsExactly("4","5");
	}
	
	@Test
	void controller_get_search_code() {
		Collection<Activity> activities = controller.get(new Controller.GetArguments().setFilter(new Filter.Dto().addField(Parameters.SEARCH, "5")));
		assertThat(activities).isNotNull();
		assertThat(activities.stream().map(x -> x.getCode()).collect(Collectors.toList())).containsExactly("5");
	}
	
	@Test
	void controller_get_search_name() {
		Collection<Activity> activities = controller.get(new Controller.GetArguments().setFilter(new Filter.Dto().addField(Parameters.SEARCH, "daloa")));
		assertThat(activities).isNotNull();
		assertThat(activities.stream().map(x -> x.getCode()).collect(Collectors.toList())).containsExactly("4","5");
	}
}
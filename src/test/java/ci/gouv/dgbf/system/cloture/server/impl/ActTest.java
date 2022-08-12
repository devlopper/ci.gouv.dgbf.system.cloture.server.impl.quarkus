package ci.gouv.dgbf.system.cloture.server.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.junit.jupiter.api.Test;

import ci.gouv.dgbf.system.cloture.server.api.persistence.ActType;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActTypePersistence;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(Profiles.Act.class)
public class ActTest {

	@Inject Assertor assertor;
	@Inject ActTypePersistence typePersistence;
	
	@Test
	void persistence_type_readMany() {
		Collection<ActType> budgetaryActs = typePersistence.readMany(new QueryExecutorArguments());
		assertThat(budgetaryActs).isNotNull();
		assertThat(budgetaryActs.stream().map(x -> x.getIdentifier()).collect(Collectors.toList())).containsExactly("ENGAGEMENT","LIQUIDATION","MANDAT","ORDRE_PAIEMENT");
	}
}
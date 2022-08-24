package ci.gouv.dgbf.system.cloture.server.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.junit.jupiter.api.Test;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Act;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActType;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActTypePersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Parameters;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(Profiles.Act.class)
public class ActTest {

	@Inject Assertor assertor;
	@Inject ActTypePersistence typePersistence;
	@Inject ActPersistence persistence;
	
	@Test
	void persistence_type_readMany() {
		Collection<ActType> actTypes = typePersistence.readMany(new QueryExecutorArguments());
		assertThat(actTypes).isNotNull();
		assertThat(actTypes.stream().map(x -> x.getIdentifier()).collect(Collectors.toList())).containsExactly("ENGAGEMENT","LIQUIDATION","MANDAT","ORDRE_PAIEMENT");
	}
	
	@Test
	void persistence_type_getByIdentifierOrDefaultIfIdentifierIsBlank() {
		ActType actType = typePersistence.readDefault();
		assertThat(actType).isNotNull();
		assertThat(actType.getCode()).isEqualTo(ActType.CODE_ENGAGEMENT);
	}
	
	@Test
	void persistence__readMany_type_identifier_ENGAGEMENT() {
		Collection<Act> acts = persistence.readMany(new QueryExecutorArguments().addFilterField(Parameters.ACT_TYPE_IDENTIFIER, "ENGAGEMENT"));
		assertThat(acts).isNotNull();
		assertThat(acts.stream().map(x -> x.getIdentifier()).collect(Collectors.toList())).containsExactly("1","2","3");
	}
	
	@Test
	void persistence__readMany_type_identifier_LIQUIDATION() {
		Collection<Act> acts = persistence.readMany(new QueryExecutorArguments().addFilterField(Parameters.ACT_TYPE_IDENTIFIER, "LIQUIDATION"));
		assertThat(acts).isNotNull();
		assertThat(acts.stream().map(x -> x.getIdentifier()).collect(Collectors.toList())).containsExactly("4","5");
	}
	
	@Test
	void persistence__readMany_type_identifier_MANDAT() {
		Collection<Act> acts = persistence.readMany(new QueryExecutorArguments().addFilterField(Parameters.ACT_TYPE_IDENTIFIER, "MANDAT"));
		assertThat(acts).isNotNull();
		assertThat(acts.stream().map(x -> x.getIdentifier()).collect(Collectors.toList())).containsExactly("6");
	}
	
	@Test
	void persistence__readMany_type_identifier_ORDRE_PAIEMENT() {
		Collection<Act> acts = persistence.readMany(new QueryExecutorArguments().addFilterField(Parameters.ACT_TYPE_IDENTIFIER, "ORDRE_PAIEMENT"));
		assertThat(acts).isNull();
	}
	
	@Test
	void persistence__readMany_operation_identifier_1() {
		Collection<Act> acts = persistence.readMany(new QueryExecutorArguments().addFilterField(Parameters.OPERATION_IDENTIFIER, "1"));
		assertThat(acts).isNull();
	}
	
	@Test
	void persistence__readMany_operation_identifier_1_ACT_ADDED_TO_SPECIFIED_OPERATION_true() {
		Collection<Act> acts = persistence.readMany(new QueryExecutorArguments().addFilterFieldsValues(Parameters.OPERATION_IDENTIFIER, "1",Parameters.ACT_ADDED_TO_SPECIFIED_OPERATION,Boolean.TRUE));
		assertThat(acts).isNull();
	}
	
	@Test
	void persistence__readMany_operation_identifier_1_ACT_ADDED_TO_SPECIFIED_OPERATION_false() {
		Collection<Act> acts = persistence.readMany(new QueryExecutorArguments().addFilterFieldsValues(Parameters.OPERATION_IDENTIFIER, "1",Parameters.ACT_ADDED_TO_SPECIFIED_OPERATION,Boolean.FALSE));
		assertThat(acts).isNotNull();
		assertThat(acts.stream().map(x -> x.getIdentifier()).collect(Collectors.toList())).containsExactly("1","2","3","4","5","6");
	}
	
	@Test
	void persistence__readMany_operation_identifier_2() {
		Collection<Act> acts = persistence.readMany(new QueryExecutorArguments().addFilterField(Parameters.OPERATION_IDENTIFIER, "2"));
		assertThat(acts).isNotNull();
		assertThat(acts.stream().map(x -> x.getIdentifier()).collect(Collectors.toList())).containsExactly("1");
	}
	
	@Test
	void persistence__readMany_operation_identifier_3() {
		Collection<Act> acts = persistence.readMany(new QueryExecutorArguments().addFilterField(Parameters.OPERATION_IDENTIFIER, "3"));
		assertThat(acts).isNotNull();
		assertThat(acts.stream().map(x -> x.getIdentifier()).collect(Collectors.toList())).containsExactly("3","6");
	}
}
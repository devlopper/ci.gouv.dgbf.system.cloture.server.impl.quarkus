package ci.gouv.dgbf.system.cloture.server.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import ci.gouv.dgbf.system.cloture.server.api.business.OperationBusiness;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Operation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationType;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationTypePersistence;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(Profiles.Operation.class)
public class OperationTest {

	@Inject Assertor assertor;
	@Inject OperationTypePersistence typePersistence;
	@Inject OperationPersistence persistence;
	@Inject OperationBusiness business;
	
	@Test @Order(1)
	void persistence_type_readMany() {
		Collection<OperationType> types = typePersistence.readMany(new QueryExecutorArguments());
		assertThat(types).isNotNull();
		assertThat(types.stream().map(x -> x.getIdentifier()).collect(Collectors.toList())).containsExactly("DEVERROUILLAGE","VERROUILLAGE");
	}
	
	@Test @Order(1)
	void persistence_readMany() {
		Collection<Operation> operations = persistence.readMany(new QueryExecutorArguments());
		assertThat(operations).isNotNull();
		assertThat(operations.stream().map(x -> x.getIdentifier()).collect(Collectors.toList())).containsExactly("1");
	}
	
	@Test @Order(1)
	void business_create_all_null() {
		Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
			business.create((String)null,(String)null,(String)null,(String)null,(String)null);
	    });
		assertThat(exception.getMessage()).isEqualTo("L'identifiant de Type d'opération est requis\r\nLe motif est requis\r\nLe nom d'utilisateur est requis");
	}
	
	@Test @Order(2)
	void business_create() {
		Long count = persistence.count();
		business.create(OperationType.CODE_DEVERROUILLAGE,"D001", null, "Instruction 001", "christian");
		assertThat(persistence.count()).isEqualTo(count+1l);
	}
}
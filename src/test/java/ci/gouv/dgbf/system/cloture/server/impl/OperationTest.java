package ci.gouv.dgbf.system.cloture.server.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import ci.gouv.dgbf.system.cloture.server.api.business.OperationBusiness;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Operation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationType;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationTypePersistence;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(Profiles.Operation.class)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
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
	
	@Test @Order(3)
	void business_addAct_1() {
		assertor.assertOperationActs("D001", (String[]) null);
		//Long count = persistence.count();
		business.addAct("D001", List.of("1"), null, "meliane");
		assertor.assertOperationActs("D001", "1");
		//assertThat(persistence.count()).isEqualTo(count+1l);
	}
	
	@Test @Order(4)
	void business_addAct_3() {
		assertor.assertOperationActs("D001", "1");
		business.addAct("D001", List.of("3"), null, "meliane");
		assertor.assertOperationActs("D001", "1","3");
	}
	
	@Test @Order(5)
	void business_addAct_4() {
		assertor.assertOperationActs("D001", "1","3");
		business.addAct("D001", List.of("4"), null, "meliane");
		assertor.assertOperationActs("D001", "1","3","4");
	}
	
	@Test @Order(6)
	void business_addAct_1_2() {
		assertor.assertOperationActs("D001", "1","3","4");
		Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
			business.addAct("D001", List.of("1","2"), null, "meliane");
	    });
		assertThat(exception.getMessage()).isEqualTo("Les Actes suivants ont déja été ajoutés : 1");
		assertor.assertOperationActs("D001", "1","3","4");
	}
	
	@Test @Order(7)
	void business_addAct_1_2_ignoreExisting() {
		assertor.assertOperationActs("D001", "1","3","4");
		business.addAct("D001", List.of("1","2"), Boolean.TRUE, "meliane");
		assertor.assertOperationActs("D001", "1","2","3","4");
	}
	
	@Test @Order(8)
	void business_removeAct_2() {
		assertor.assertOperationActs("D001", "1","2","3","4");
		business.removeAct("D001", List.of("2"), null, "meliane");
		assertor.assertOperationActs("D001", "1","3","4");
	}
}
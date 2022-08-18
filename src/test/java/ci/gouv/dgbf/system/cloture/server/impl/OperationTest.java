package ci.gouv.dgbf.system.cloture.server.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.junit.jupiter.api.Assertions;
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
	
	@Test
	void persistence_type_readMany() {
		Collection<OperationType> types = typePersistence.readMany(new QueryExecutorArguments());
		assertThat(types).isNotNull();
		assertThat(types.stream().map(x -> x.getIdentifier()).collect(Collectors.toList())).containsExactly("DEVERROUILLAGE","VERROUILLAGE");
	}
	
	@Test
	void persistence_readMany() {
		Collection<Operation> operations = persistence.readMany(new QueryExecutorArguments());
		assertThat(operations).isNotNull();
		assertThat(operations.stream().map(x -> x.getIdentifier()).collect(Collectors.toList())).contains("1");
	}
	
	@Test
	void business_create_all_null() {
		Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
			business.create((String)null,(String)null,(String)null,(String)null,(String)null);
	    });
		assertThat(exception.getMessage()).isEqualTo("L'identifiant de Type d'opération est requis\r\nLe motif est requis\r\nLe nom d'utilisateur est requis");
	}
	
	@Test
	void business_create() {
		Long count = persistence.count();
		business.create(OperationType.CODE_DEVERROUILLAGE,"D001", null, "Instruction 001", "christian");
		assertThat(persistence.count()).isEqualTo(count+1l);
	}
	
	@Test
	void business_addAct___add_empty_1() {
		assertor.assertOperationActs("add_empty", (String[]) null);
		business.addAct("add_empty", List.of("1"), null, "meliane");
		assertor.assertOperationActs("add_empty", "1");
	}
	
	@Test
	void business_addAct__exception_already_added__add_notempty_1() {
		assertor.assertOperationActs("add_notempty_exception", "add_notempty_exception_1");
		Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
			business.addAct("add_notempty_exception", List.of("add_notempty_exception_1"), null, "meliane");
	    });
		assertThat(exception.getMessage()).isEqualTo("Les Actes suivants ont déja été ajoutés : add_notempty_exception_1");
		assertor.assertOperationActs("add_notempty_exception", "add_notempty_exception_1");
	}
	
	@Test
	void business_addAct__exception_already_added__add_notempty_2_ignoreExisting_null() {
		assertor.assertOperationActs("add_notempty_exception", "add_notempty_exception_1");
		Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
			business.addAct("add_notempty_exception", List.of("add_notempty_exception_1","add_notempty_exception_2"), null, "meliane");
	    });
		assertThat(exception.getMessage()).isEqualTo("Les Actes suivants ont déja été ajoutés : add_notempty_exception_1");
		assertor.assertOperationActs("add_notempty_exception", "add_notempty_exception_1");
	}
	
	@Test
	void business_addAct_add_notempty_2_ignoreExisting_true() {
		assertor.assertOperationActs("add_notempty", "add_notempty_1");
		business.addAct("add_notempty", List.of("add_notempty_1","add_notempty_2"), Boolean.TRUE, "meliane");
		assertor.assertOperationActs("add_notempty", "add_notempty_1","add_notempty_2");
	}
	
	@Test
	void business_removeAct__exception_not_added___1() {
		assertor.assertOperationActs("remove_notempty_exception", (String[]) null);
		Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
			business.removeAct("remove_notempty_exception", List.of("remove_notempty_exception_1"), null, "meliane");
	    });
		assertThat(exception.getMessage()).isEqualTo("Les Actes suivants ne sont pas ajoutés : remove_notempty_exception_1");
		assertor.assertOperationActs("remove_notempty_exception", (String[]) null);
	}
		
	@Test
	void business_removeAct_not_added__1_ignoreExisting_true() {
		assertor.assertOperationActs("remove_empty", (String[]) null);
		business.removeAct("remove_empty", List.of("1"), Boolean.TRUE, "meliane");
		assertor.assertOperationActs("remove_empty", (String[]) null);
	}
	
	@Test
	void business_addActComprehensively() {
		assertor.assertOperationActs("add_comprehensively_notempty", "add_comprehensively_notempty_1","add_comprehensively_notempty_2");
		business.addActComprehensively("add_comprehensively_notempty", List.of("add_comprehensively_notempty_2","add_comprehensively_notempty_3"), "meliane");
		assertor.assertOperationActs("add_comprehensively_notempty", "add_comprehensively_notempty_2","add_comprehensively_notempty_3");
		business.addActComprehensively("add_comprehensively_notempty", List.of("add_comprehensively_notempty_4"), "meliane");
		assertor.assertOperationActs("add_comprehensively_notempty","add_comprehensively_notempty_4");
		business.addActComprehensively("add_comprehensively_notempty", null, "meliane");
		assertor.assertOperationActs("add_comprehensively_notempty",(String[]) null);
	}
}
package ci.gouv.dgbf.system.cloture.server.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.cyk.utility.__kernel__.time.TimeHelper;
import org.cyk.utility.persistence.query.Filter;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.service.client.Controller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ci.gouv.dgbf.system.cloture.server.api.business.OperationBusiness;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Operation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationStatus;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationStatusPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationType;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationTypePersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Parameters;
import ci.gouv.dgbf.system.cloture.server.api.service.OperationTypeDto;
import ci.gouv.dgbf.system.cloture.server.client.rest.OperationController;
import ci.gouv.dgbf.system.cloture.server.client.rest.OperationTypeController;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(Profiles.Operation.class)
public class OperationTest {

	@Inject Assertor assertor;
	@Inject OperationTypePersistence typePersistence;
	@Inject OperationStatusPersistence statusPersistence;
	@Inject OperationTypeController typeController;
	
	@Inject OperationPersistence persistence;
	@Inject OperationBusiness business;
	@Inject OperationController controller;
	
	@Inject Configuration configuration;
	
	@Test
	void persistence_type_readMany() {
		Collection<OperationType> types = typePersistence.readMany(new QueryExecutorArguments());
		assertThat(types).isNotNull();
		assertThat(types.stream().map(x -> x.getIdentifier()).collect(Collectors.toList())).containsExactly("DEVERROUILLAGE","VERROUILLAGE");
	}
	
	@Test
	void persistence_status_readMany() {
		Collection<OperationStatus> status = statusPersistence.readMany(new QueryExecutorArguments());
		assertThat(status).isNotNull();
		assertThat(status.stream().map(x -> x.getIdentifier()).collect(Collectors.toList())).containsExactly("CREEE","DEMARREE","EXECUTEE");
	}
	
	@Test
	void persistence_type_readDefault() {
		OperationType type = typePersistence.readDefault();
		assertThat(type).isNotNull();
		assertThat(type.getCode()).isEqualTo(Configuration.Operation.Type.CODE_DEVERROUILLAGE);
	}
	
	@Test
	void controller_type_getByIdentifierOrDefaultIfIdentifierIsBlank() {
		ci.gouv.dgbf.system.cloture.server.client.rest.OperationType type = typeController.getByIdentifierOrDefaultIfIdentifierIsBlank(null,new Controller.GetArguments().projections(OperationTypeDto.JSON_IDENTIFIER,OperationTypeDto.JSON_CODE));
		assertThat(type).isNotNull();
		assertThat(type.getCode()).isEqualTo(Configuration.Operation.Type.CODE_DEVERROUILLAGE);
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
		business.create(Configuration.Operation.Type.CODE_DEVERROUILLAGE,"D001", null, "Instruction 001", "christian");
		assertThat(persistence.count()).isEqualTo(count+1l);
	}
	
	@Test
	void business_create_execution_is_sequential() {
		Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
			business.create(Configuration.Operation.Type.CODE_DEVERROUILLAGE,"D001", null, "Instruction 001", "christian",Boolean.TRUE);
	    });
		assertThat(exception.getMessage()).startsWith("Des opérations non encore exécutées ont été trouvées : ");
	}
	
	@Test
	void controller_create() {
		Long count = persistence.count();
		controller.create(Configuration.Operation.Type.CODE_DEVERROUILLAGE,"D002", null, "Instruction 002", "christian");
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
	
	@Test
	void business_addActByFilter_exception_null() {
		Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
			business.addActByFilter(null, null, null, null);
	    });
		assertThat(exception.getMessage()).isEqualTo("L'identifiant de Opération est requis\r\nLe filtre est requis\r\nLe nom d'utilisateur est requis");
	}
	
	@Test
	void business_addActByFilter_code_add_byfilter_1() {
		assertor.assertOperationActs("add_byfilter1",(String[]) null);
		business.addActByFilter("add_byfilter1", new Filter().addField(Parameters.ACTS_CODES, List.of("add_byfilter1_1")),null, "meliane");
		assertor.assertOperationActs("add_byfilter1", "add_byfilter1_1");
	}
	
	@Test
	void business_addActByFilter_type_ADDBYFILTER() {
		assertor.assertOperationActs("add_byfilter2",(String[]) null);
		business.addActByFilter("add_byfilter2", new Filter().addField(Parameters.ACT_TYPE_IDENTIFIER, "ADDBYFILTER"),null, "meliane");
		assertor.assertOperationActs("add_byfilter2", "add_byfilter2_2","add_byfilter2_3");
	}
	
	@Test
	void business_removeActByFilter_exception_null() {
		Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
			business.removeActByFilter(null, null, null, null);
	    });
		assertThat(exception.getMessage()).isEqualTo("L'identifiant de Opération est requis\r\nLe filtre est requis\r\nLe nom d'utilisateur est requis");
	}
	
	@Test
	void business_removeActByFilter_code_remove_byfilter_1() {
		assertor.assertOperationActs("remove_byfilter1", "remove_byfilter1_1");
		business.removeActByFilter("remove_byfilter1", new Filter().addField(Parameters.ACTS_CODES, List.of("remove_byfilter1_1")),null, "meliane");
		assertor.assertOperationActs("remove_byfilter1",(String[]) null);
	}
	
	@Test
	void business_removeActByFilter_type_ADDBYFILTER() {
		assertor.assertOperationActs("remove_byfilter2", "remove_byfilter2_2","remove_byfilter2_3");
		business.removeActByFilter("remove_byfilter2", new Filter().addField(Parameters.ACT_TYPE_IDENTIFIER, "REMOVEBYFILTER"),null, "meliane");
		assertor.assertOperationActs("remove_byfilter2",(String[]) null);
	}
	
	@Test
	void business_start_actsCountIsZero() {
		Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
			business.startExecution("start_actscountiszero", "christian");
	    });
		assertThat(exception.getMessage()).isEqualTo("Opération 1 doit contenir au moins un acte");
	}
	
	@Test
	void business_start_statusIsEqual() {
		Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
			business.startExecution("start_statusisequal", "christian");
	    });
		assertThat(exception.getMessage()).isEqualTo("Opération 1 à déja été démarrée");
	}
	
	@Test
	void business_start_statusIsGreater() {
		Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
			business.startExecution("start_statusisgreater", "christian");
	    });
		assertThat(exception.getMessage()).isEqualTo("Opération 1 à déja été démarrée");
	}
	
	@Test
	void business_start_01() {
		assertor.assertOperationStatusCode("start_01",configuration.operation().status().createdCode());
		business.startExecution("start_01", "christian");
		assertor.assertOperationStatusCode("start_01",configuration.operation().status().startedCode());
		TimeHelper.pause(1000l * 1);
		assertor.assertOperationStatusCode("start_01",configuration.operation().status().startedCode());
		TimeHelper.pause(1000l * 3);
		assertor.assertOperationStatusCode("start_01",configuration.operation().status().executedCode());
	}
	
	@Test
	void business_start_01_processedIsTrue() {
		assertor.assertOperationStatusCode("start_01_processedIsTrue",configuration.operation().status().createdCode());
		business.startExecution("start_01_processedIsTrue", "christian");
		assertor.assertOperationStatusCode("start_01_processedIsTrue",configuration.operation().status().startedCode());
	}
	
	public static String PA_VERROUILLER(String identifiers) {
		TimeHelper.pause(1000l * 5);
		return null;
	}
	
	public static String PA_DEVERROUILLER(String identifiers) {
		TimeHelper.pause(1000l * 5);
		return null;
	}
}
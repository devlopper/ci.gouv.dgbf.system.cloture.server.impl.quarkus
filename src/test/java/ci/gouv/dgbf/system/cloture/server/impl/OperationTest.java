package ci.gouv.dgbf.system.cloture.server.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityManager;

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
import ci.gouv.dgbf.system.cloture.server.api.service.OperationDto;
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
	@Inject EntityManager entityManager;
	
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
	void controller_readOne_color_created() {
		ci.gouv.dgbf.system.cloture.server.client.rest.Operation operation = controller.getByIdentifier("color_created",new Controller.GetArguments().projections(OperationDto.JSON_IDENTIFIER,OperationDto.JSON_COLOR));
		assertThat(operation).isNotNull();
		assertThat(operation.getColor()).isNotNull();
		assertThat(operation.getColor().getHexadecimal()).isEqualTo(Configuration.Operation.Colors.CREATED);
	}
	
	@Test
	void controller_readOne_color_started() {
		ci.gouv.dgbf.system.cloture.server.client.rest.Operation operation = controller.getByIdentifier("color_started",new Controller.GetArguments().projections(OperationDto.JSON_IDENTIFIER,OperationDto.JSON_COLOR));
		assertThat(operation).isNotNull();
		assertThat(operation.getColor()).isNotNull();
		assertThat(operation.getColor().getHexadecimal()).isEqualTo(Configuration.Operation.Colors.STARTED);
	}
	
	@Test
	void controller_readOne_color_executed() {
		ci.gouv.dgbf.system.cloture.server.client.rest.Operation operation = controller.getByIdentifier("color_executed",new Controller.GetArguments().projections(OperationDto.JSON_IDENTIFIER,OperationDto.JSON_COLOR));
		assertThat(operation).isNotNull();
		assertThat(operation.getColor()).isNotNull();
		assertThat(operation.getColor().getHexadecimal()).isEqualTo(Configuration.Operation.Colors.EXECUTED);
	}
	
	@Test
	void controller_readOne_statuses_created() {
		ci.gouv.dgbf.system.cloture.server.client.rest.Operation operation = controller.getByIdentifier("color_created",new Controller.GetArguments().projections(OperationDto.JSON_IDENTIFIER,OperationDto.JSONS_STATUSES));
		assertThat(operation).isNotNull();
		assertThat(operation.getCreated()).isTrue();
		assertThat(operation.getStarted()).isNull();
		assertThat(operation.getExecuted()).isNull();
	}
	
	@Test
	void controller_readOne_statues_started() {
		ci.gouv.dgbf.system.cloture.server.client.rest.Operation operation = controller.getByIdentifier("color_started",new Controller.GetArguments().projections(OperationDto.JSON_IDENTIFIER,OperationDto.JSONS_STATUSES));
		assertThat(operation).isNotNull();
		assertThat(operation.getCreated()).isNull();
		assertThat(operation.getStarted()).isTrue();
		assertThat(operation.getExecuted()).isNull();
	}
	
	@Test
	void controller_readOne_statues_executed() {
		ci.gouv.dgbf.system.cloture.server.client.rest.Operation operation = controller.getByIdentifier("color_executed",new Controller.GetArguments().projections(OperationDto.JSON_IDENTIFIER,OperationDto.JSONS_STATUSES));
		assertThat(operation).isNotNull();
		assertThat(operation.getCreated()).isNull();
		assertThat(operation.getStarted()).isNull();
		assertThat(operation.getExecuted()).isTrue();
	}
	
	@Test
	void controller_readOne_numberOfActs() {
		ci.gouv.dgbf.system.cloture.server.client.rest.Operation operation = controller.getByIdentifier("has_3_acts",new Controller.GetArguments().projections(OperationDto.JSON_IDENTIFIER,OperationDto.JSON_NUMBER_OF_ACTS));
		assertThat(operation).isNotNull();
		assertThat(operation.getNumberOfActs()).isEqualTo(3l);
	}
	
	@Test
	void controller_readOne_numberOfImputations() {
		ci.gouv.dgbf.system.cloture.server.client.rest.Operation operation = controller.getByIdentifier("has_2_imputations",new Controller.GetArguments().projections(OperationDto.JSON_IDENTIFIER,OperationDto.JSON_NUMBER_OF_IMPUTATIONS));
		assertThat(operation).isNotNull();
		assertThat(operation.getNumberOfImputations()).isEqualTo(2l);
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
		Long auditCount = ((BigInteger) entityManager.createNativeQuery("SELECT COUNT(identifiant) FROM TA_OPERATION_AUD").getResultList().get(0)).longValue();
		business.create(Configuration.Operation.Type.CODE_DEVERROUILLAGE,null, null, "Instruction 001", "christian");
		assertThat(persistence.count()).isEqualTo(count+1l);
		assertThat(((BigInteger) entityManager.createNativeQuery("SELECT COUNT(identifiant) FROM TA_OPERATION_AUD").getResultList().get(0)).longValue()).isEqualTo(auditCount+1l);
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
	
	/* Add act */
	
	@Test
	void business_addAct___add_empty_1() {
		Long auditCount = ((BigInteger) entityManager.createNativeQuery("SELECT COUNT(identifiant) FROM TA_OPERATION_ACTE_AUD").getResultList().get(0)).longValue();
		assertor.assertOperationActs("add_empty", (String[]) null);
		business.addAct("add_empty", List.of("1"), null, "meliane");
		assertor.assertOperationActs("add_empty", "1");
		assertThat(((BigInteger) entityManager.createNativeQuery("SELECT COUNT(identifiant) FROM TA_OPERATION_ACTE_AUD").getResultList().get(0)).longValue()).isEqualTo(auditCount+1l);
	}
	
	@Test
	void business_addAct__exception_already_added__add_notempty_1() {
		assertor.assertOperationActs("add_notempty_exception", "add_notempty_exception_1");
		Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
			business.addAct("add_notempty_exception", List.of("add_notempty_exception_1"), null, "meliane");
	    });
		assertThat(exception.getMessage()).isEqualTo("Les Actes suivants ont déjà été ajoutés : add_notempty_exception_1");
		assertor.assertOperationActs("add_notempty_exception", "add_notempty_exception_1");
	}
	
	@Test
	void business_addAct__exception_already_added__add_notempty_2_ignoreExisting_null() {
		assertor.assertOperationActs("add_notempty_exception", "add_notempty_exception_1");
		Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
			business.addAct("add_notempty_exception", List.of("add_notempty_exception_1","add_notempty_exception_2"), null, "meliane");
	    });
		assertThat(exception.getMessage()).isEqualTo("Les Actes suivants ont déjà été ajoutés : add_notempty_exception_1");
		assertor.assertOperationActs("add_notempty_exception", "add_notempty_exception_1");
	}
	
	@Test
	void business_addAct_add_notempty_2_ignoreExisting_true() {
		assertor.assertOperationActs("add_notempty", "add_notempty_1");
		business.addAct("add_notempty", List.of("add_notempty_1","add_notempty_2"), Boolean.TRUE, "meliane");
		assertor.assertOperationActs("add_notempty", "add_notempty_1","add_notempty_2");
	}
	
	@Test
	void business_addAct___whenStarted_exception() {
		assertor.assertOperationActs("add_whenStarted", (String[]) null);
		Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
			business.addAct("add_whenStarted", List.of("add_whenNotCreated"), null, "meliane");
	    });
		assertThat(exception.getMessage()).isEqualTo("Opération 1 est déjà DEMARREE");
		assertor.assertOperationActs("add_whenStarted", (String[]) null);
	}
	
	@Test
	void business_addAct___whenCreated_exception() {
		assertor.assertOperationActs("add_whenExecuted", (String[]) null);
		Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
			business.addAct("add_whenExecuted", List.of("add_whenNotCreated"), null, "meliane");
	    });
		assertThat(exception.getMessage()).isEqualTo("Opération 1 est déjà EXECUTEE");
		assertor.assertOperationActs("add_whenExecuted", (String[]) null);
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
	void business_addActByFilter_exception_started() {
		Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
			business.addActByFilter("add_byfilter_whenStarted", new Filter().addField(Parameters.ACTS_CODES, List.of("add_whenNotCreated")),null, "meliane");
	    });
		assertThat(exception.getMessage()).isEqualTo("Opération 1 est déjà DEMARREE");
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
	
	/* Remove Act */
	
	@Test
	void business_removeAct___remove_notempty_1() {
		assertor.assertOperationActs("remove_notempty", "remove_notempty_1");
		business.removeAct("remove_notempty", List.of("remove_notempty_1"), null, "meliane");
		assertor.assertOperationActs("removenot_empty", (String[]) null);
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
	void business_removeAct___whenStarted_exception() {
		assertor.assertOperationActs("remove_whenStarted", (String[]) null);
		Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
			business.removeAct("remove_whenStarted", List.of("remove_whenNotCreated"), null, "meliane");
	    });
		assertThat(exception.getMessage()).isEqualTo("Opération 1 est déjà DEMARREE\r\nLes Actes suivants ne sont pas ajoutés : remove_whenNotCreated");
		assertor.assertOperationActs("remove_whenStarted", (String[]) null);
	}
	
	@Test
	void business_removeAct___whenExecuted_exception() {
		assertor.assertOperationActs("remove_whenExecuted", (String[]) null);
		Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
			business.addAct("remove_whenExecuted", List.of("remove_whenNotCreated"), null, "meliane");
	    });
		assertThat(exception.getMessage()).isEqualTo("Opération 1 est déjà EXECUTEE");
		assertor.assertOperationActs("remove_whenExecuted", (String[]) null);
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
	
	/* Add Imputation */
	
	@Test
	void business_addImputation___add_empty_1() {
		Long auditCount = ((BigInteger) entityManager.createNativeQuery("SELECT COUNT(identifiant) FROM TA_OPERATION_IMPUTATION_AUD").getResultList().get(0)).longValue();
		assertor.assertOperationImputations("add_empty", (String[]) null);
		business.addImputation("add_empty", List.of("1"), null, "meliane");
		assertor.assertOperationImputations("add_empty", "1");
		assertThat(((BigInteger) entityManager.createNativeQuery("SELECT COUNT(identifiant) FROM TA_OPERATION_IMPUTATION_AUD").getResultList().get(0)).longValue()).isEqualTo(auditCount+1l);
	}
	
	@Test
	void business_removeImputation___remove_not_empty_1() {
		assertor.assertOperationImputations("remove_notempty", "remove_notempty_1");
		business.removeImputation("remove_notempty", List.of("remove_notempty_1"), null, "meliane");
		assertor.assertOperationImputations("remove_notempty", (String[]) null);
	}
	
	/* Start */
	
	@Test
	void business_start_actsCountIsZero() {
		Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
			business.startExecution("start_actscountiszero", "christian");
	    });
		assertThat(exception.getMessage()).isEqualTo("Opération 1 doit contenir au moins un acte ou une imputation");
	}
	
	@Test
	void business_start_statusIsEqual() {
		Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
			business.startExecution("start_statusisequal", "christian");
	    });
		assertThat(exception.getMessage()).isEqualTo("Opération 1 est déjà DEMARREE");
	}
	
	@Test
	void business_start_statusIsGreater() {
		Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
			business.startExecution("start_statusisgreater", "christian");
	    });
		assertThat(exception.getMessage()).isEqualTo("Opération 1 est déjà EXECUTEE");
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
	
	public static String PA_EXECUTER_OPERATION(String identifier) {
		TimeHelper.pause(1000l * 5);
		return null;
	}
}
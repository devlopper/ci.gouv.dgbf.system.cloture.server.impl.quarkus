package ci.gouv.dgbf.system.cloture.server.impl.service;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import ci.gouv.dgbf.system.cloture.server.api.service.OperationDto;
import ci.gouv.dgbf.system.cloture.server.impl.Profiles;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(Profiles.Service.Integration.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OperationServiceTest {

	@Test
    public void get_one() {
		io.restassured.response.Response response = given().param("projections", OperationDto.JSON_IDENTIFIER, OperationDto.JSON_CODE, OperationDto.JSON_NAME
				, OperationDto.JSON_START_DATE_NUMBER_OF_MILLISECOND, OperationDto.JSON_EXECUTION_BEGIN_DATE_NUMBER_OF_MILLISECOND, OperationDto.JSON_EXECUTION_END_DATE_NUMBER_OF_MILLISECOND
				, OperationDto.JSON_EXECUTION_STATUS)
				//.log().all()
				.when().get("/api/operations/V01");
		response.then()
		//.log().all()
        	.statusCode(Response.Status.OK.getStatusCode())
        	.body(OperationDto.JSON_IDENTIFIER, equalTo("V01"))
        	;
    }
	
	@Test
    public void execute_passed_blocking() {
		io.restassured.response.Response response = given().queryParam("identifiant", "OPassedBlocking").queryParam("declencheur", "test")
				//.log().all()
				.when().post("/api/operations/execution");
		response.then()
		//.log().all()
        	.statusCode(Response.Status.OK.getStatusCode())
        	;
    }
	
	@Test
    public void execute_future() {
		io.restassured.response.Response response = given().queryParam("identifiant", "OFuture").queryParam("declencheur", "test")
				//.log().all()
				.when().post("/api/operations/execution");
		response.then()
		//.log().all()
        	.statusCode(Response.Status.BAD_REQUEST.getStatusCode())
        	;
    }
}
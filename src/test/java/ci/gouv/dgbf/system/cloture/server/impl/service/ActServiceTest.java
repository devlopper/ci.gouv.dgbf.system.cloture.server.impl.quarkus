package ci.gouv.dgbf.system.cloture.server.impl.service;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import ci.gouv.dgbf.system.cloture.server.api.service.ActDto;
import ci.gouv.dgbf.system.cloture.server.api.service.OperationDto;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
//@TestProfile(Profiles.Service.Integration.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ActServiceTest {

	@Test
    public void get_one() {
		io.restassured.response.Response response = given().param("projections", ActDto.JSON_IDENTIFIER,ActDto.JSON_CODE,ActDto.JSON_NAME
				,ActDto.JSON_TYPE,ActDto.JSON_STATUS_STRING,ActDto.JSON_LATEST_OPERATION_STRING,ActDto.JSON_NUMBER_OF_LOCKS_ENABLED,ActDto.JSON_LOCKED_REASONS)
				//.log().all()
				.when().get("/api/actes/1");
		response.then()
		//.log().all()
        	.statusCode(Response.Status.OK.getStatusCode())
        	.body(OperationDto.JSON_IDENTIFIER, equalTo("1"))
        	;
    }
}
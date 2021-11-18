package ci.gouv.dgbf.system.cloture.server.client;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.rest.ResponseHelper;
import org.cyk.utility.service.client.SpecificServiceGetter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ci.gouv.dgbf.system.cloture.server.api.service.OperationService;
import ci.gouv.dgbf.system.cloture.server.client.rest.Operation;
import ci.gouv.dgbf.system.cloture.server.impl.Assertor;
import ci.gouv.dgbf.system.cloture.server.impl.Profiles;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(Profiles.Client.class)
public class OperationClientTest {

	@Inject Assertor assertor;
	
	@Test
    public void get_many() {
		Response response = DependencyInjection.inject(SpecificServiceGetter.class).get(Operation.class).get(null,null, null, null, null, null, null);
		assertThat(response).isNotNull();
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
		assertThat(response.getHeaderString(ResponseHelper.HEADER_X_TOTAL_COUNT)).isEqualTo("6");
		assertThat(response.getHeaders().entrySet().stream().map(entry -> entry.getKey()).collect(Collectors.toList()))
		.contains(ResponseHelper.HEADER_PROCESSING_START_TIME,ResponseHelper.HEADER_PROCESSING_END_TIME,ResponseHelper.HEADER_PROCESSING_DURATION);
		
		List<Operation> operations = ResponseHelper.getEntityAsListFromJson(Operation.class,response);
		assertThat(operations).hasSize(6);
    }
	
	@Test
    public void execute() {
		OperationService operationService = (OperationService) DependencyInjection.inject(SpecificServiceGetter.class).get(Operation.class);
		WebApplicationException exception = Assertions.assertThrows(WebApplicationException.class, () -> {
			operationService.execute("OFuture", "test", Boolean.TRUE);
		});	
		Response response = exception.getResponse();
		assertThat(response).isNotNull();
		assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());		
    }
		
}
package ci.gouv.dgbf.system.cloture.server.impl.persistence;
import java.io.Serializable;

import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;

import ci.gouv.dgbf.system.cloture.server.impl.Configuration;

public class OperationImplStatusesReader extends AbstractOperationImplReader implements Serializable {

	@Override
	protected QueryStringBuilder.Arguments instantiateQueryStringBuilderArguments() {
		QueryStringBuilder.Arguments arguments =  super.instantiateQueryStringBuilderArguments();
		arguments.getProjection(Boolean.TRUE).addFromTuple("t", OperationImpl.FIELD_IDENTIFIER,OperationImpl.FIELD_STATUS);
		return arguments;
	}
	
	@Override
	protected void __set__(OperationImpl operation, Object[] array) {
		Configuration configuration = __inject__(Configuration.class);
		Integer index = 0;
		operation.setIdentifier(getAsString(array, index++));
		OperationStatusImpl status = (OperationStatusImpl) array[index++];
		if(status != null) {
			if(status.getCode().equals(configuration.operation().status().createdCode()))
				operation.setCreated(Boolean.TRUE);
			else if(status.getCode().equals(configuration.operation().status().startedCode()))
				operation.setStarted(Boolean.TRUE);
			else if(status.getCode().equals(configuration.operation().status().executedCode()))
				operation.setExecuted(Boolean.TRUE);
		}
	}
}
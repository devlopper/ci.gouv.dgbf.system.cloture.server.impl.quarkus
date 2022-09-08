package ci.gouv.dgbf.system.cloture.server.impl.persistence;
import java.io.Serializable;

import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;

import ci.gouv.dgbf.system.cloture.server.impl.Configuration;

public class OperationImplColorReader extends AbstractOperationImplReader implements Serializable {

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
				operation.getColor(Boolean.TRUE).setHexadecimal(ValueHelper.defaultToIfBlank(configuration.operation().colors().created().hexadecimal(),Configuration.Operation.Colors.CREATED));
			else if(status.getCode().equals(configuration.operation().status().startedCode()))
				operation.getColor(Boolean.TRUE).setHexadecimal(ValueHelper.defaultToIfBlank(configuration.operation().colors().started().hexadecimal(),Configuration.Operation.Colors.STARTED));
			else if(status.getCode().equals(configuration.operation().status().executedCode()))
				operation.getColor(Boolean.TRUE).setHexadecimal(ValueHelper.defaultToIfBlank(configuration.operation().colors().executed().hexadecimal(),Configuration.Operation.Colors.EXECUTED));
		}
	}
}
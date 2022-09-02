package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;

public class OperationImplStringsReader extends AbstractOperationImplReader implements Serializable {

	@Override
	protected QueryStringBuilder.Arguments instantiateQueryStringBuilderArguments() {
		QueryStringBuilder.Arguments arguments =  super.instantiateQueryStringBuilderArguments();
		arguments.getProjection(Boolean.TRUE).addFromTuple("t",OperationImpl.FIELD_IDENTIFIER,OperationImpl.FIELD_CODE,OperationImpl.FIELD_NAME,OperationImpl.FIELD_REASON
				,FieldHelper.join(OperationImpl.FIELD_TYPE,OperationTypeImpl.FIELD_NAME),FieldHelper.join(OperationImpl.FIELD_STATUS,OperationStatusImpl.FIELD_NAME));
		return arguments;
	}
	
	@Override
	protected void __set__(OperationImpl operation, Object[] array) {
		Integer index = 0;
		operation.setIdentifier(getAsString(array, index++));
		operation.setCode(getAsString(array, index++));
		operation.setName(getAsString(array, index++));
		operation.setReason(getAsString(array, index++));
		operation.setTypeAsString(getAsString(array, index++));
		operation.setStatusAsString(getAsString(array, index++));
	}
}
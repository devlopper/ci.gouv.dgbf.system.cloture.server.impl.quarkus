package ci.gouv.dgbf.system.cloture.server.impl.persistence;
import java.io.Serializable;

import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;

public class OperationImplNumberOfActsReader extends AbstractOperationImplReader implements Serializable {

	@Override
	protected QueryStringBuilder.Arguments instantiateQueryStringBuilderArguments() {
		QueryStringBuilder.Arguments arguments =  super.instantiateQueryStringBuilderArguments();
		arguments.getProjection(Boolean.TRUE).addFromTuple("t", OperationImpl.FIELD_IDENTIFIER);
		arguments.getProjection(Boolean.TRUE).add("COUNT(oa)");
		arguments.getTuple(Boolean.TRUE).addJoins(String.format("JOIN %s oa ON oa.%s = t",OperationActImpl.ENTITY_NAME,OperationActImpl.FIELD_OPERATION));
		arguments.getGroup(Boolean.TRUE).add("t.identifier");
		return arguments;
	}
	
	@Override
	protected void __set__(OperationImpl operation, Object[] array) {
		Integer index = 0;
		operation.setIdentifier(getAsString(array, index++));
		operation.setNumberOfActs(getAsLong(array, index++));
	}
}
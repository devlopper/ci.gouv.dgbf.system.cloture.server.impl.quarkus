package ci.gouv.dgbf.system.cloture.server.impl.persistence;
import java.io.Serializable;

import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;

public class OperationImplAuditReader extends AbstractOperationImplReader implements Serializable {

	@Override
	protected QueryStringBuilder.Arguments instantiateQueryStringBuilderArguments() {
		QueryStringBuilder.Arguments arguments =  super.instantiateQueryStringBuilderArguments();
		arguments.getProjection(Boolean.TRUE).addFromTuple("t", OperationImpl.FIELD_IDENTIFIER);
		addAuditProjectionsFromTuple(arguments);
		return arguments;
	}
	
	@Override
	protected void __set__(OperationImpl operation, Object[] array) {
		Integer index = 0;
		operation.setIdentifier(getAsString(array, index++));
		__setAudits__(operation, array, index);
		__setAuditsAsStrings__(operation, array);
		__setAudit__(operation, array);
	}
}
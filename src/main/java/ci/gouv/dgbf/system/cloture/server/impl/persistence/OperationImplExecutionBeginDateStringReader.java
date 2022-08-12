package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.cyk.utility.__kernel__.time.TimeHelper;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;

public class OperationImplExecutionBeginDateStringReader extends AbstractOperationImplReader implements Serializable {

	@Override
	protected QueryStringBuilder.Arguments instantiateQueryStringBuilderArguments() {
		QueryStringBuilder.Arguments arguments =  super.instantiateQueryStringBuilderArguments();
		arguments.getProjection(Boolean.TRUE).addFromTuple("t",ScriptImpl.FIELD_IDENTIFIER,ScriptImpl.FIELD_EXECUTION_BEGIN_DATE);
		return arguments;
	}
	
	@Override
	protected void __set__(ScriptImpl operation, Object[] array) {
		Integer index = 1;
		operation.setExecutionBeginDateString(TimeHelper.formatLocalDateTime((LocalDateTime) array[index++]));		
	}
}
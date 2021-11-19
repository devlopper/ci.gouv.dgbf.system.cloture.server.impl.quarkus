package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.cyk.utility.__kernel__.time.TimeHelper;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;

public class ActImplOperationDateStringReader extends AbstractActImplReader implements Serializable {

	@Override
	protected QueryStringBuilder.Arguments instantiateQueryStringBuilderArguments() {
		QueryStringBuilder.Arguments arguments =  super.instantiateQueryStringBuilderArguments();
		arguments.getProjection(Boolean.TRUE).addFromTuple("t",ActImpl.FIELD_IDENTIFIER,ActImpl.FIELD_OPERATION_DATE);
		return arguments;
	}
	
	@Override
	protected void __set__(ActImpl act, Object[] array) {
		Integer index = 1;
		act.setOperationDateString(TimeHelper.formatLocalDateTime((LocalDateTime) array[index++]));		
	}
}
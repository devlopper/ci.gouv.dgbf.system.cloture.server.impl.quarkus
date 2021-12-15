package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.cyk.utility.__kernel__.time.TimeHelper;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;

import ci.gouv.dgbf.system.cloture.server.api.persistence.ActOperationType;

public class ActImplLatestOperationStringReader extends AbstractActImplReader implements Serializable {

	@Override
	protected QueryStringBuilder.Arguments instantiateQueryStringBuilderArguments() {
		QueryStringBuilder.Arguments arguments =  super.instantiateQueryStringBuilderArguments();
		arguments.getProjection(Boolean.TRUE).addFromTuple("t",ActImpl.FIELD_IDENTIFIER);
		arguments.getProjection(Boolean.TRUE).addFromTuple("p",ActLatestOperationImpl.FIELD_OPERATION_TYPE,ActLatestOperationImpl.FIELD_OPERATION_DATE,ActLatestOperationImpl.FIELD_TRIGGER);
		arguments.getTuple(Boolean.TRUE).addJoins(String.format("LEFT JOIN %s p ON p.%s = t.%s",ActLatestOperationImpl.ENTITY_NAME
				,ActLatestOperationImpl.FIELD_ACT_IDENTIFIER,ActImpl.FIELD_IDENTIFIER));
		return arguments;
	}
	
	@Override
	protected void __set__(ActImpl act, Object[] array) {
		Integer index = 1;
		ActOperationType actOperationType = (ActOperationType) array[index++];
		if(actOperationType == null)
			;
		else
			act.setLatestOperationString(String.format(FORMAT, actOperationType.getLabel(),TimeHelper.formatLocalDateTime((LocalDateTime) array[index++]),array[index++]));		
	}
	
	private static final String FORMAT = "%s le %s par %s";
}
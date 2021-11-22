package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;

import ci.gouv.dgbf.system.cloture.server.api.persistence.ActOperationType;

public class ActImplCodeOperationTypeReader extends AbstractActImplReader implements Serializable {

	@Override
	protected QueryStringBuilder.Arguments instantiateQueryStringBuilderArguments() {
		QueryStringBuilder.Arguments arguments =  super.instantiateQueryStringBuilderArguments();
		arguments.getProjection(Boolean.TRUE).addFromTuple("t",ActImpl.FIELD_IDENTIFIER,ActImpl.FIELD_CODE);
		arguments.getProjection(Boolean.TRUE).addFromTuple("p",ActLatestOperationImpl.FIELD_OPERATION_TYPE);
		arguments.getTuple(Boolean.TRUE).addJoins(String.format("LEFT JOIN %s p ON p.%s = t.%s",ActLatestOperationImpl.ENTITY_NAME
				,ActLatestOperationImpl.FIELD_ACT_IDENTIFIER,ActImpl.FIELD_IDENTIFIER));
		return arguments;
	}
	
	@Override
	protected void __set__(ActImpl act, Object[] array) {
		Integer index = 1;
		act.setCode(getAsString(array, index++));
		act.setOperationType((ActOperationType) array[index++]);		
	}
}
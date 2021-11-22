package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;

public class ActImplOperationTriggerReader extends AbstractActImplReader implements Serializable {

	@Override
	protected QueryStringBuilder.Arguments instantiateQueryStringBuilderArguments() {
		QueryStringBuilder.Arguments arguments =  super.instantiateQueryStringBuilderArguments();
		arguments.getProjection(Boolean.TRUE).addFromTuple("t",ActImpl.FIELD_IDENTIFIER);
		arguments.getProjection(Boolean.TRUE).addFromTuple("p",ActLatestOperationImpl.FIELD_TRIGGER);
		arguments.getTuple(Boolean.TRUE).addJoins(String.format("LEFT JOIN %s p ON p.%s = t.%s",ActLatestOperationImpl.ENTITY_NAME
				,ActLatestOperationImpl.FIELD_ACT_IDENTIFIER,ActImpl.FIELD_IDENTIFIER));
		return arguments;
	}
	
	@Override
	protected void __set__(ActImpl act, Object[] array) {
		Integer index = 1;
		act.setTrigger((String) array[index++]);		
	}
}
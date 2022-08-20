package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;

public class ActImplCodeNameReader extends AbstractActImplReader implements Serializable {

	@Override
	protected QueryStringBuilder.Arguments instantiateQueryStringBuilderArguments() {
		QueryStringBuilder.Arguments arguments =  super.instantiateQueryStringBuilderArguments();
		arguments.getProjection(Boolean.TRUE).addFromTuple("t",ActImpl.FIELD_IDENTIFIER,ActImpl.FIELD_CODE,ActImpl.FIELD_NAME);
		return arguments;
	}
	
	@Override
	protected void __set__(ActImpl act, Object[] array) {
		Integer index = 1;
		act.setCode(getAsString(array, index++));
		act.setName(getAsString(array, index++));
		__set__(act, array, index++);
	}
	
	protected void __set__(ActImpl act, Object[] array,Integer index) {
		
	}
}
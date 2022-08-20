package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;

public class ActImplCodeNameTypeAsStringReader extends ActImplCodeNameReader implements Serializable {

	@Override
	protected QueryStringBuilder.Arguments instantiateQueryStringBuilderArguments() {
		QueryStringBuilder.Arguments arguments =  super.instantiateQueryStringBuilderArguments();
		arguments.getProjection(Boolean.TRUE).addFromTuple("t",FieldHelper.join(ActImpl.FIELD_TYPE,ActTypeImpl.FIELD_NAME));
		return arguments;
	}
	
	@Override
	protected void __set__(ActImpl act, Object[] array, Integer index) {
		super.__set__(act, array, index);
		act.setTypeAsString(getAsString(array, index++));
	}
}
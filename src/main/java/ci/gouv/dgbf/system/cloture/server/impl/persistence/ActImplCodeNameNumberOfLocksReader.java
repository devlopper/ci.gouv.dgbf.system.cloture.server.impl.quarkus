package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;

public class ActImplCodeNameNumberOfLocksReader extends AbstractActImplReader implements Serializable {

	@Override
	protected QueryStringBuilder.Arguments instantiateQueryStringBuilderArguments() {
		QueryStringBuilder.Arguments arguments =  super.instantiateQueryStringBuilderArguments();
		arguments.getProjection(Boolean.TRUE).addFromTuple("t",ActImpl.FIELD_IDENTIFIER,ActImpl.FIELD_CODE,ActImpl.FIELD_NAME).add("COUNT(l.identifier)");
		arguments.getTuple(Boolean.TRUE).addJoins(String.format("LEFT JOIN %s l ON l.%s = t.%s AND l.%s = t.%s",ActLockImpl.ENTITY_NAME
				,ActLockImpl.FIELD_ACT_REFERENCE,ActImpl.FIELD_REFERENCE,ActLockImpl.FIELD_ACT_TYPE,ActImpl.FIELD_TYPE));
		arguments.getGroup(Boolean.TRUE).add("t.identifier");
		return arguments;
	}
	
	@Override
	protected void __set__(ActImpl act, Object[] array) {
		Integer index = 1;
		act.setCode(getAsString(array, index++));
		act.setName(getAsString(array, index++));
		act.setNumberOfLocks(NumberHelper.getInteger(getAsLong(array, index++)));		
	}
}
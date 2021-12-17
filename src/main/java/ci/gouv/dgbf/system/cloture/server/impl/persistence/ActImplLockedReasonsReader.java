package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;

public class ActImplLockedReasonsReader extends AbstractActImplReader implements Serializable {

	@Override
	protected QueryStringBuilder.Arguments instantiateQueryStringBuilderArguments() {
		QueryStringBuilder.Arguments arguments =  super.instantiateQueryStringBuilderArguments();
		arguments.getProjection(Boolean.TRUE).addFromTuple("t",ActImpl.FIELD_IDENTIFIER).addFromTuple("s",ActLockImpl.FIELD_REASON);
		arguments.getTuple(Boolean.TRUE).addJoins(String.format("JOIN %s s ON s.%s = t.%s AND s.enabled = true",ActLockImpl.ENTITY_NAME,ActLockImpl.FIELD_ACT_IDENTIFIER,ActImpl.FIELD_IDENTIFIER));
		return arguments;
	}
	
	@Override
	protected void __set__(ActImpl act, Object[] array) {
		act.addLockedReasons(getAsString(array, 1));
	}
	
	@Override
	protected Boolean isEntityHasOnlyArray(ActImpl act) {
		return Boolean.FALSE;
	}
}
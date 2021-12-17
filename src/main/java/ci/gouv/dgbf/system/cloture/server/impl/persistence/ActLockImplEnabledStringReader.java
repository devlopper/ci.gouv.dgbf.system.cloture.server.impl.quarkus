package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import org.cyk.utility.persistence.server.Helper;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;

public class ActLockImplEnabledStringReader extends AbstractActLockImplReader implements Serializable {

	@Override
	protected QueryStringBuilder.Arguments instantiateQueryStringBuilderArguments() {
		QueryStringBuilder.Arguments arguments =  super.instantiateQueryStringBuilderArguments();
		arguments.getProjection(Boolean.TRUE).addFromTuple("t",ActLockImpl.FIELD_IDENTIFIER,ActLockImpl.FIELD_ENABLED);
		return arguments;
	}
	
	@Override
	protected void __set__(ActLockImpl actLock, Object[] array) {
		Integer index = 1;
		actLock.setEnabledString(Helper.ifTrueYesElseNo((Boolean) array[index++]));
	}
}
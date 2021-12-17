package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.cyk.utility.__kernel__.time.TimeHelper;
import org.cyk.utility.persistence.server.Helper;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;

import ci.gouv.dgbf.system.cloture.server.api.persistence.ActOperationType;

public class ActLockImplReasonEnabledEnabledStringBeginDateStringEndDateStringLatestOperationReader extends AbstractActLockImplReader implements Serializable {

	@Override
	protected QueryStringBuilder.Arguments instantiateQueryStringBuilderArguments() {
		QueryStringBuilder.Arguments arguments =  super.instantiateQueryStringBuilderArguments();
		arguments.getProjection(Boolean.TRUE).addFromTuple("t",ActLockImpl.FIELD_IDENTIFIER,ActLockImpl.FIELD_REASON,ActLockImpl.FIELD_ENABLED
				,ActLockImpl.FIELD_BEGIN_DATE,ActLockImpl.FIELD_END_DATE).addFromTuple("h", ActLockLatestOperationImpl.FIELD_OPERATION_TYPE,ActLockLatestOperationImpl.FIELD_OPERATION_DATE
						,ActLockLatestOperationImpl.FIELD_TRIGGER);
		arguments.getTuple(Boolean.TRUE).addJoins(String.format("LEFT JOIN %s h ON h.%s = t.%s",ActLockLatestOperationImpl.ENTITY_NAME,ActLockLatestOperationImpl.FIELD_IDENTIFIER
				,ActLockImpl.FIELD_IDENTIFIER));
		return arguments;
	}
	
	@Override
	protected void __set__(ActLockImpl actLock, Object[] array) {
		Integer index = 1;
		actLock.setReason(getAsString(array, index++));
		actLock.setEnabled((Boolean) array[index++]);
		actLock.setEnabledString(Helper.ifTrueYesElseNo(actLock.getEnabled()));
		actLock.setBeginDateString(TimeHelper.formatLocalDateTime((LocalDateTime) array[index++]));
		actLock.setEndDateString(TimeHelper.formatLocalDateTime((LocalDateTime) array[index++]));
		actLock.setLatestOperation(ActImplLatestOperationStringReader.format((ActOperationType)array[index++], (LocalDateTime)array[index++], (String)array[index++]));
	}
}
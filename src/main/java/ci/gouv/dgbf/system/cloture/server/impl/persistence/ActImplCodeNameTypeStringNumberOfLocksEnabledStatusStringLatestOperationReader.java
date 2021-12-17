package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;

import ci.gouv.dgbf.system.cloture.server.api.persistence.ActOperationType;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActType;

public class ActImplCodeNameTypeStringNumberOfLocksEnabledStatusStringLatestOperationReader extends AbstractActImplReader implements Serializable {

	@Override
	protected QueryStringBuilder.Arguments instantiateQueryStringBuilderArguments() {
		QueryStringBuilder.Arguments arguments =  super.instantiateQueryStringBuilderArguments();
		arguments.getProjection(Boolean.TRUE).addFromTuple("t",ActImpl.FIELD_IDENTIFIER,ActImpl.FIELD_CODE,ActImpl.FIELD_NAME,ActImpl.FIELD_TYPE)
			.addFromTuple("s",ActStatusImpl.FIELD_NUMBER_OF_LOCKS_ENABLED)
			.addFromTuple("p",ActLatestOperationImpl.FIELD_OPERATION_TYPE,ActLatestOperationImpl.FIELD_OPERATION_DATE,ActLatestOperationImpl.FIELD_TRIGGER)
			;
		arguments.getTuple(Boolean.TRUE)
			.addJoins(String.format("LEFT JOIN %s p ON p.%s = t.%s",ActLatestOperationImpl.ENTITY_NAME,ActLatestOperationImpl.FIELD_ACT_IDENTIFIER,ActImpl.FIELD_IDENTIFIER))
			.addJoins(String.format("LEFT JOIN %s s ON s.%s = t.%s",ActStatusImpl.ENTITY_NAME,ActStatusImpl.FIELD_IDENTIFIER,ActImpl.FIELD_IDENTIFIER));
		return arguments;
	}
	
	@Override
	protected void __set__(ActImpl act, Object[] array) {
		Integer index = 1;
		act.setCode(getAsString(array, index++));
		act.setName(getAsString(array, index++));
		ActType actType = (ActType) array[index++];
		act.setTypeString(actType == null ? null : actType.getLabel());
		Integer numberOfLocksEnabled = NumberHelper.getInteger(array[index++]);
		act.setNumberOfLocksEnabled(numberOfLocksEnabled);
		act.setStatusString(ActImplStatusStringReader.format(act.getNumberOfLocksEnabled()));
		act.setLatestOperationString(ActImplLatestOperationStringReader.format((ActOperationType)array[index++], (LocalDateTime)array[index++], (String)array[index++]));
	}
}
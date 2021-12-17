package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;

public class ActImplStatusStringReader extends AbstractActImplReader implements Serializable {

	@Override
	protected QueryStringBuilder.Arguments instantiateQueryStringBuilderArguments() {
		QueryStringBuilder.Arguments arguments =  super.instantiateQueryStringBuilderArguments();
		arguments.getProjection(Boolean.TRUE).addFromTuple("t",ActImpl.FIELD_IDENTIFIER).addFromTuple("s",ActStatusImpl.FIELD_NUMBER_OF_LOCKS_ENABLED);
		arguments.getTuple(Boolean.TRUE).addJoins(String.format("LEFT JOIN %s s ON s.%s = t.%s",ActStatusImpl.ENTITY_NAME,ActStatusImpl.FIELD_IDENTIFIER,ActImpl.FIELD_IDENTIFIER));
		return arguments;
	}
	
	@Override
	protected void __set__(ActImpl act, Object[] array) {
		Integer index = 1;
		Integer numberOfLocksEnabled = NumberHelper.getInteger(array[index++],0);		
		act.setStatusString(format(numberOfLocksEnabled));
	}
	
	public static String format(Integer numberOfLocksEnabled) {
		return NumberHelper.isEqualToZero(numberOfLocksEnabled) ? "Non verouillé" : String.format("Verouillé(%s)",numberOfLocksEnabled);
	}
}
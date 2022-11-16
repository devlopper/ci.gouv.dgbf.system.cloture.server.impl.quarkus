package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import org.cyk.utility.persistence.query.Language.Select;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;

public class ImputationImplAsStringReader extends AbstractImputationImplReader implements Serializable {

	@Override
	protected QueryStringBuilder.Arguments instantiateQueryStringBuilderArguments() {
		QueryStringBuilder.Arguments arguments =  super.instantiateQueryStringBuilderArguments();
		arguments.getProjection(Boolean.TRUE).addFromTuple("t",ImputationImpl.FIELD_IDENTIFIER,ImputationImpl.FIELD_EXERCISE_YEAR);
		arguments.getProjection(Boolean.TRUE).add(Select.concatCodeName("a","en"));
		arguments.getTuple(Boolean.TRUE).addJoins(String.format("LEFT JOIN %s a ON a.%s = t.%s",ActivityImpl.ENTITY_NAME,ActivityImpl.FIELD_IDENTIFIER
				,ImputationImpl.FIELD_ACTIVITY_IDENTIFIER)
				,String.format("LEFT JOIN %s en ON en.%s = t.%s",EconomicNatureImpl.ENTITY_NAME,EconomicNatureImpl.FIELD_IDENTIFIER,ImputationImpl.FIELD_ECONOMIC_NATURE_IDENTIFIER));
		return arguments;
	}
	
	@Override
	protected void __set__(ImputationImpl imputation, Object[] array) {
		Integer index = 1;
		imputation.setExerciseAsString(String.valueOf(getAsShort(array, index++)));
		imputation.setActivityAsString(getAsString(array, index++));
		imputation.setEconomicNatureAsString(getAsString(array, index++));
	}
}
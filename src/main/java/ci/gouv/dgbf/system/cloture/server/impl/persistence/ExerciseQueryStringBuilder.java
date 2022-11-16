package ci.gouv.dgbf.system.cloture.server.impl.persistence;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.server.query.string.AbstractSpecificQueryStringBuilder;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder.Arguments;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Exercise;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ExercisePersistence;
import lombok.Getter;

@ApplicationScoped
public class ExerciseQueryStringBuilder extends AbstractSpecificQueryStringBuilder<Exercise> implements Serializable {

	@Getter @Inject ExercisePersistence persistence;
	
	@Override
	protected String buildDefaultValuePredicate() {
		return String.format("t.%2$s IN (SELECT MAX(dv.%2$s) FROM %1$s dv)",ExerciseImpl.ENTITY_NAME,ExerciseImpl.FIELD_YEAR);
	}
	
	@Override
	public void setOrder(QueryExecutorArguments queryExecutorArguments, Arguments arguments) {
		if(persistence.getQueryIdentifierReadDynamic().equals(queryExecutorArguments.getQuery().getIdentifier())) {
			if(arguments.getOrder() == null || CollectionHelper.isEmpty(arguments.getOrder().getStrings())) {
				arguments.getOrder(Boolean.TRUE).desc("t", ExerciseImpl.FIELD_YEAR);
			}
		}
	}
}
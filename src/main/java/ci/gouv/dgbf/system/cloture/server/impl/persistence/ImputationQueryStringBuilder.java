package ci.gouv.dgbf.system.cloture.server.impl.persistence;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.persistence.query.Filter;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.server.query.string.AbstractSpecificQueryStringBuilder;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder.Arguments;
import org.cyk.utility.persistence.server.query.string.WhereStringBuilder.Predicate;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Imputation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ImputationPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Parameters;
import lombok.Getter;

@ApplicationScoped
public class ImputationQueryStringBuilder extends AbstractSpecificQueryStringBuilder<Imputation> implements Serializable {

	@Getter @Inject ImputationPersistence persistence;
	
	@Override
	public void populatePredicates(QueryExecutorArguments queryExecutorArguments, Arguments arguments,Predicate predicate, Filter filter) {
		super.populatePredicates(queryExecutorArguments, arguments, predicate, filter);
		
		Boolean processed = queryExecutorArguments.getFilterFieldValueAsBoolean(null,Parameters.PROCESSED);
		Boolean addedToSpecifiedOperation = queryExecutorArguments.getFilterFieldValueAsBoolean(Boolean.TRUE,Parameters.ADDED_TO_SPECIFIED_OPERATION);
		
		populatePredicatesExists(queryExecutorArguments, arguments, predicate, filter, Parameters.OPERATION_IDENTIFIER
				,String.format("SELECT oi FROM %s oi WHERE oi.%s.identifier = :%s AND oi.%s = t%s",OperationImputationImpl.ENTITY_NAME,OperationImputationImpl.FIELD_OPERATION,Parameters.OPERATION_IDENTIFIER,OperationImputationImpl.FIELD_IMPUTATION
						,processed == null ? "" : (processed ? String.format(" AND oi.%s = %s", OperationImputationImpl.FIELD_PROCESSED,Boolean.TRUE.toString()) : String.format(" AND (oi.%1$s IS NULL OR oi.%1$s = %2$s)", OperationImputationImpl.FIELD_PROCESSED,Boolean.FALSE.toString())))
				,!Boolean.TRUE.equals(addedToSpecifiedOperation));
		/*
		if(queryExecutorArguments.getFilterFieldValue(Parameters.ACTS_CODES) != null) {
			predicate.add(String.format("t.%s IN :%s", ImputationImpl.FIELD_CODE,Parameters.ACTS_CODES));
			filter.addField(Parameters.ACTS_CODES, queryExecutorArguments.getFilterFieldValue(Parameters.ACTS_CODES));
		}
		*/
		populatePredicatesEquals(queryExecutorArguments, arguments, predicate, filter, ImputationImpl.FIELD_EXERCISE_IDENTIFIER, Parameters.EXERCISE_IDENTIFIER);
		populatePredicatesEquals(queryExecutorArguments, arguments, predicate, filter, ImputationImpl.FIELD_ACTIVITY_IDENTIFIER, Parameters.ACTIVITY_IDENTIFIER);
		populatePredicatesEquals(queryExecutorArguments, arguments, predicate, filter, ImputationImpl.FIELD_ECONOMIC_NATURE_IDENTIFIER, Parameters.ECONOMIC_NATURE_IDENTIFIER);
	}
	
	@Override
	public void setOrder(QueryExecutorArguments queryExecutorArguments, Arguments arguments) {
		if(persistence.getQueryIdentifierReadDynamic().equals(queryExecutorArguments.getQuery().getIdentifier())) {
			if(arguments.getOrder() == null || CollectionHelper.isEmpty(arguments.getOrder().getStrings())) {
				arguments.getOrder(Boolean.TRUE).desc("t", ImputationImpl.FIELD_EXERCISE_YEAR).asc("t", ImputationImpl.FIELD_ACTIVITY_CODE).asc("t", ImputationImpl.FIELD_ECONOMIC_NATURE_CODE);
			}
		}
	}
}
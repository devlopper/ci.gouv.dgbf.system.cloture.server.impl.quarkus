package ci.gouv.dgbf.system.cloture.server.impl.persistence;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.cyk.utility.persistence.query.Filter;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.server.query.string.AbstractSpecificQueryStringBuilder;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder.Arguments;
import org.cyk.utility.persistence.server.query.string.WhereStringBuilder.Predicate;

import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationImputation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationImputationPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Parameters;
import lombok.Getter;

@ApplicationScoped
public class OperationImputationQueryStringBuilder extends AbstractSpecificQueryStringBuilder<OperationImputation> implements Serializable {

	@Getter @Inject OperationImputationPersistence persistence;
	
	@Override
	public void populatePredicates(QueryExecutorArguments queryExecutorArguments, Arguments arguments,Predicate predicate, Filter filter) {
		super.populatePredicates(queryExecutorArguments, arguments, predicate, filter);
		if(queryExecutorArguments.getFilterFieldValue(Parameters.OPERATION_IDENTIFIER) != null) {
			predicate.add(String.format("t.%s.%s IN :%s", OperationImputationImpl.FIELD_OPERATION,OperationImpl.FIELD_IDENTIFIER,Parameters.OPERATION_IDENTIFIER));
			filter.addField(Parameters.OPERATION_IDENTIFIER, queryExecutorArguments.getFilterFieldValue(Parameters.OPERATION_IDENTIFIER));
		}
		
		if(queryExecutorArguments.getFilterFieldValue(Parameters.IMPUTATIONS_IDENTIFIERS) != null) {
			predicate.add(String.format("t.%s.%s IN :%s", OperationImputationImpl.FIELD_IMPUTATION,ImputationImpl.FIELD_IDENTIFIER,Parameters.IMPUTATIONS_IDENTIFIERS));
			filter.addField(Parameters.IMPUTATIONS_IDENTIFIERS, queryExecutorArguments.getFilterFieldValue(Parameters.IMPUTATIONS_IDENTIFIERS));
		}
	}
}
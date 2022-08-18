package ci.gouv.dgbf.system.cloture.server.impl.persistence;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.cyk.utility.persistence.query.Filter;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.server.query.string.AbstractSpecificQueryStringBuilder;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder.Arguments;
import org.cyk.utility.persistence.server.query.string.WhereStringBuilder.Predicate;

import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationAct;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationActPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Parameters;
import lombok.Getter;

@ApplicationScoped
public class OperationActQueryStringBuilder extends AbstractSpecificQueryStringBuilder<OperationAct> implements Serializable {

	@Getter @Inject OperationActPersistence persistence;
	
	@Override
	protected Class<OperationAct> getPeristenceClass() {
		return OperationAct.class;
	}
	
	@Override
	public void populatePredicates(QueryExecutorArguments queryExecutorArguments, Arguments arguments,Predicate predicate, Filter filter) {
		super.populatePredicates(queryExecutorArguments, arguments, predicate, filter);
		if(queryExecutorArguments.getFilterFieldValue(Parameters.OPERATION_IDENTIFIER) != null) {
			predicate.add(String.format("t.%s.%s IN :%s", OperationActImpl.FIELD_OPERATION,OperationImpl.FIELD_IDENTIFIER,Parameters.OPERATION_IDENTIFIER));
			filter.addField(Parameters.OPERATION_IDENTIFIER, queryExecutorArguments.getFilterFieldValue(Parameters.OPERATION_IDENTIFIER));
		}
		
		if(queryExecutorArguments.getFilterFieldValue(Parameters.ACTS_IDENTIFIERS) != null) {
			predicate.add(String.format("t.%s.%s IN :%s", OperationActImpl.FIELD_ACT,ActImpl.FIELD_IDENTIFIER,Parameters.ACTS_IDENTIFIERS));
			filter.addField(Parameters.ACTS_IDENTIFIERS, queryExecutorArguments.getFilterFieldValue(Parameters.ACTS_IDENTIFIERS));
		}
	}
}
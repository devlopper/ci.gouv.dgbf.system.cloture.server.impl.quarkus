package ci.gouv.dgbf.system.cloture.server.impl.persistence;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.persistence.query.Filter;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.server.query.string.AbstractSpecificQueryStringBuilder;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder.Arguments;
import org.cyk.utility.persistence.server.query.string.WhereStringBuilder.Predicate;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Operation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Parameters;
import lombok.Getter;

@ApplicationScoped
public class OperationQueryStringBuilder extends AbstractSpecificQueryStringBuilder<Operation> implements Serializable {

	@Getter @Inject OperationPersistence persistence;
	
	@Override
	protected Class<Operation> getPeristenceClass() {
		return Operation.class;
	}
	
	/*@Override
	protected String buildDefaultValuePredicate() {
		return String.format("t.%s = '%s'",OperationImpl.FIELD_CODE,ActType.CODE_ENGAGEMENT);
	}*/
	
	@Override
	public void populatePredicates(QueryExecutorArguments queryExecutorArguments, Arguments arguments,Predicate predicate, Filter filter) {
		super.populatePredicates(queryExecutorArguments, arguments, predicate, filter);
		String operationTypeIdentifier = (String) queryExecutorArguments.getFilterFieldValue(Parameters.OPERATION_TYPE_IDENTIFIER);
		if(StringHelper.isNotBlank(operationTypeIdentifier)) {
			predicate.add(String.format("t.%s.identifier = :%s", OperationImpl.FIELD_TYPE,Parameters.OPERATION_TYPE_IDENTIFIER));
			filter.addField(Parameters.OPERATION_TYPE_IDENTIFIER, operationTypeIdentifier);
		}
	}
	
	@Override
	public void setOrder(QueryExecutorArguments queryExecutorArguments, Arguments arguments) {
		if(persistence.getQueryIdentifierReadDynamic().equals(queryExecutorArguments.getQuery().getIdentifier())) {
			if(arguments.getOrder() == null || CollectionHelper.isEmpty(arguments.getOrder().getStrings())) {
				arguments.getOrder(Boolean.TRUE).asc("t", OperationImpl.FIELD_CODE);
			}
		}
	}
}
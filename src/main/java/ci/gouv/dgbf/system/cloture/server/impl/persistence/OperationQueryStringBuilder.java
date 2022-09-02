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
import ci.gouv.dgbf.system.cloture.server.impl.Configuration;
import lombok.Getter;

@ApplicationScoped
public class OperationQueryStringBuilder extends AbstractSpecificQueryStringBuilder<Operation> implements Serializable {

	@Getter @Inject OperationPersistence persistence;
	@Inject Configuration configuration;
	
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
		String typeIdentifier = (String) queryExecutorArguments.getFilterFieldValue(Parameters.OPERATION_TYPE_IDENTIFIER);
		if(StringHelper.isNotBlank(typeIdentifier)) {
			predicate.add(String.format("t.%s.identifier = :%s", OperationImpl.FIELD_TYPE,Parameters.OPERATION_TYPE_IDENTIFIER));
			filter.addField(Parameters.OPERATION_TYPE_IDENTIFIER, typeIdentifier);
		}
		
		String statusIdentifier = (String) queryExecutorArguments.getFilterFieldValue(Parameters.OPERATION_STATUS_IDENTIFIER);
		if(StringHelper.isNotBlank(statusIdentifier)) {
			predicate.add(String.format("t.%s.identifier = :%s", OperationImpl.FIELD_STATUS,Parameters.OPERATION_STATUS_IDENTIFIER));
			filter.addField(Parameters.OPERATION_STATUS_IDENTIFIER, statusIdentifier);
		}
		
		String statusCode = (String) queryExecutorArguments.getFilterFieldValue(Parameters.OPERATION_HAS_STATUS_CODE_AT_LEAST);
		if(StringHelper.isNotBlank(statusCode))
			predicate.add(buildPredicateStatusCodeIsAtLeast(statusCode));
		
		Boolean executed = queryExecutorArguments.getFilterFieldValueAsBoolean(null,Parameters.OPERATION_EXECUTED);
		if(executed != null)
			predicate.add(buildPredicateStatusCodeIsAtLeast(configuration.operation().status().executedCode(),!executed));
	}
	
	String buildPredicateStatusCodeIsAtLeast(String statusCode,Boolean negate) {
		return String.format("t.%1$s.%2$s %6$s (SELECT os.%2$s FROM %3$s os WHERE os.%4$s = '%5$s')", OperationImpl.FIELD_STATUS,OperationStatusImpl.FIELD_ORDER_NUMBER,OperationStatusImpl.ENTITY_NAME,OperationStatusImpl.FIELD_CODE,statusCode
				,Boolean.TRUE.equals(negate) ? "<" : ">=");
	}
	
	String buildPredicateStatusCodeIsAtLeast(String statusCode) {
		return buildPredicateStatusCodeIsAtLeast(statusCode, null);
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
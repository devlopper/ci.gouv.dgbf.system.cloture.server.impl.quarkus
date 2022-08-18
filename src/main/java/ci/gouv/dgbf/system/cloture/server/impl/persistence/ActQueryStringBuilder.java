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

import ci.gouv.dgbf.system.cloture.server.api.persistence.Act;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActOperationType;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Parameters;
import lombok.Getter;

@ApplicationScoped
public class ActQueryStringBuilder extends AbstractSpecificQueryStringBuilder<Act> implements Serializable {

	@Getter @Inject ActPersistence persistence;
	
	@Override
	protected Class<Act> getPeristenceClass() {
		return Act.class;
	}
	
	@Override
	public void populatePredicates(QueryExecutorArguments queryExecutorArguments, Arguments arguments,Predicate predicate, Filter filter) {
		super.populatePredicates(queryExecutorArguments, arguments, predicate, filter);
		String operationTypeAsString = (String) queryExecutorArguments.getFilterFieldValue(Parameters.ACT_OPERATION_TYPE);
		if(StringHelper.isNotBlank(operationTypeAsString)) {
			ActOperationType operationType = ActOperationType.valueOf(operationTypeAsString);
			if(operationType != null) {
				predicate.add(String.format("p.%s = :%s", ActLatestOperationImpl.FIELD_OPERATION_TYPE,Parameters.ACT_OPERATION_TYPE));
				filter.addField(Parameters.ACT_OPERATION_TYPE, operationType);
			}
		}
		populatePredicatesExists(queryExecutorArguments, arguments, predicate, filter, Parameters.OPERATION_IDENTIFIER
				,String.format("SELECT oa FROM %s oa WHERE oa.%s.identifier = :%s AND oa.%s = t"
				,OperationActImpl.ENTITY_NAME,OperationActImpl.FIELD_OPERATION,Parameters.OPERATION_IDENTIFIER,OperationActImpl.FIELD_ACT));
		/*
		if(queryExecutorArguments.getFilterFieldValue(Parameters.OPERATION_IDENTIFIER) != null) {
			predicate.add(String.format("t.%s IN :%s", ActImpl.FIELD_CODE,Parameters.OPERATION_IDENTIFIER));
			filter.addField(Parameters.OPERATION_IDENTIFIER, queryExecutorArguments.getFilterFieldValue(Parameters.OPERATION_IDENTIFIER));
		}*/
		if(queryExecutorArguments.getFilterFieldValue(Parameters.ACTS_CODES) != null) {
			predicate.add(String.format("t.%s IN :%s", ActImpl.FIELD_CODE,Parameters.ACTS_CODES));
			filter.addField(Parameters.ACTS_CODES, queryExecutorArguments.getFilterFieldValue(Parameters.ACTS_CODES));
		}
	}
	
	@Override
	public void setOrder(QueryExecutorArguments queryExecutorArguments, Arguments arguments) {
		if(persistence.getQueryIdentifierReadDynamic().equals(queryExecutorArguments.getQuery().getIdentifier())) {
			if(arguments.getOrder() == null || CollectionHelper.isEmpty(arguments.getOrder().getStrings())) {
				arguments.getOrder(Boolean.TRUE).asc("t", ActImpl.FIELD_CODE);
			}
		}
	}
}
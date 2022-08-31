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

import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationStatus;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationStatusPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Parameters;
import ci.gouv.dgbf.system.cloture.server.impl.Configuration;
import lombok.Getter;

@ApplicationScoped
public class OperationStatusQueryStringBuilder extends AbstractSpecificQueryStringBuilder<OperationStatus> implements Serializable {

	@Getter @Inject OperationStatusPersistence persistence;
	@Inject Configuration configuration;
	
	@Override
	protected Class<OperationStatus> getPeristenceClass() {
		return OperationStatus.class;
	}
	
	@Override
	protected String buildDefaultValuePredicate() {
		return String.format("t.%s = '%s'",OperationStatusImpl.FIELD_CODE,configuration.operation().status().defaultCode());
	}
	
	@Override
	public void populatePredicates(QueryExecutorArguments queryExecutorArguments, Arguments arguments,Predicate predicate, Filter filter) {
		super.populatePredicates(queryExecutorArguments, arguments, predicate, filter);
		String code = (String) queryExecutorArguments.getFilterFieldValue(Parameters.CODE);
		if(StringHelper.isNotBlank(code)) {
			predicate.add(String.format("t.%s = :%s", OperationImpl.FIELD_CODE,Parameters.CODE));
			filter.addField(Parameters.CODE, code);
		}
	}
	
	@Override
	public void setOrder(QueryExecutorArguments queryExecutorArguments, Arguments arguments) {
		if(persistence.getQueryIdentifierReadDynamic().equals(queryExecutorArguments.getQuery().getIdentifier())) {
			if(arguments.getOrder() == null || CollectionHelper.isEmpty(arguments.getOrder().getStrings())) {
				arguments.getOrder(Boolean.TRUE).asc("t", OperationStatusImpl.FIELD_ORDER_NUMBER);
			}
		}
	}
}
package ci.gouv.dgbf.system.cloture.server.impl.persistence;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.server.query.string.AbstractSpecificQueryStringBuilder;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder.Arguments;

import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationType;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationTypePersistence;
import ci.gouv.dgbf.system.cloture.server.impl.Configuration;
import lombok.Getter;

@ApplicationScoped
public class OperationTypeQueryStringBuilder extends AbstractSpecificQueryStringBuilder<OperationType> implements Serializable {

	@Getter @Inject OperationTypePersistence persistence;
	@Inject Configuration configuration;
	
	@Override
	protected Class<OperationType> getPeristenceClass() {
		return OperationType.class;
	}
	
	@Override
	protected String buildDefaultValuePredicate() {
		return String.format("t.%s = '%s'",OperationTypeImpl.FIELD_CODE,configuration.operation().type().defaultCode());
	}
	
	@Override
	public void setOrder(QueryExecutorArguments queryExecutorArguments, Arguments arguments) {
		if(persistence.getQueryIdentifierReadDynamic().equals(queryExecutorArguments.getQuery().getIdentifier())) {
			if(arguments.getOrder() == null || CollectionHelper.isEmpty(arguments.getOrder().getStrings())) {
				arguments.getOrder(Boolean.TRUE).asc("t", OperationTypeImpl.FIELD_CODE);
			}
		}
	}
}
package ci.gouv.dgbf.system.cloture.server.impl.persistence;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.server.query.string.AbstractSpecificQueryStringBuilder;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder.Arguments;

import ci.gouv.dgbf.system.cloture.server.api.persistence.ActType;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActTypePersistence;
import lombok.Getter;

@ApplicationScoped
public class ActTypeQueryStringBuilder extends AbstractSpecificQueryStringBuilder<ActType> implements Serializable {

	@Getter @Inject ActTypePersistence persistence;
	
	@Override
	protected Class<ActType> getPeristenceClass() {
		return ActType.class;
	}
	
	@Override
	protected String buildDefaultValuePredicate() {
		return String.format("t.%s = '%s'",ActTypeImpl.FIELD_CODE,ActType.CODE_ENGAGEMENT);
	}
	
	@Override
	public void setOrder(QueryExecutorArguments queryExecutorArguments, Arguments arguments) {
		if(persistence.getQueryIdentifierReadDynamic().equals(queryExecutorArguments.getQuery().getIdentifier())) {
			if(arguments.getOrder() == null || CollectionHelper.isEmpty(arguments.getOrder().getStrings())) {
				arguments.getOrder(Boolean.TRUE).asc("t", ActTypeImpl.FIELD_CODE);
			}
		}
	}
}
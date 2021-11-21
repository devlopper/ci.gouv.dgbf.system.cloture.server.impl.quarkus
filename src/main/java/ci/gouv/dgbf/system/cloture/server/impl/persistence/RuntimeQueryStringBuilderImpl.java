package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import static org.cyk.utility.persistence.query.Language.parenthesis;
import static org.cyk.utility.persistence.query.Language.Where.or;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.persistence.query.Filter;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.server.query.string.LikeStringBuilder;
import org.cyk.utility.persistence.server.query.string.LikeStringValueBuilder;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder.Arguments;
import org.cyk.utility.persistence.server.query.string.RuntimeQueryStringBuilder;
import org.cyk.utility.persistence.server.query.string.WhereStringBuilder.Predicate;

import ci.gouv.dgbf.system.cloture.server.api.persistence.ActOperationType;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationGroupPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Parameters;
import io.quarkus.arc.Unremovable;

@ApplicationScoped @ci.gouv.dgbf.system.cloture.server.api.System @Unremovable
public class RuntimeQueryStringBuilderImpl extends RuntimeQueryStringBuilder.AbstractImpl implements Serializable {

	@Inject OperationGroupPersistence operationGroupPersistence;
	@Inject OperationPersistence operationPersistence;
	@Inject ActPersistence actPersistence;
	
	@Override
	protected void populatePredicate(QueryExecutorArguments arguments, Arguments builderArguments, Predicate predicate,Filter filter) {
		super.populatePredicate(arguments, builderArguments, predicate, filter);
		if(Boolean.TRUE.equals(actPersistence.isProcessable(arguments)))
			populatePredicateAct(arguments, builderArguments, predicate, filter);
	}
	
	@Override
	protected void setOrder(QueryExecutorArguments arguments, Arguments builderArguments) {
		if(actPersistence.getQueryIdentifierReadDynamic().equals(arguments.getQuery().getIdentifier())) {
			builderArguments.getOrder(Boolean.TRUE).asc("t", ActImpl.FIELD_CODE);
		}
		super.setOrder(arguments, builderArguments);
	}
	
	private final String ACT_PREDICATE_SEARCH = parenthesis(or(
			LikeStringBuilder.getInstance().build("t",ActImpl.FIELD_CODE, Parameters.SEARCH)
			,LikeStringBuilder.getInstance().build("t", ActImpl.FIELD_NAME,Parameters.SEARCH)
	));
	public void populatePredicateAct(QueryExecutorArguments arguments, Arguments builderArguments, Predicate predicate,Filter filter) {
		String operationTypeAsString = (String) arguments.getFilterFieldValue(Parameters.ACT_OPERATION_TYPE);
		ActOperationType operationType = ActOperationType.valueOf(operationTypeAsString);
		if(operationType != null) {
			predicate.add(String.format("t.%s = :%s", ActImpl.FIELD_OPERATION_TYPE,Parameters.ACT_OPERATION_TYPE));
			filter.addField(Parameters.ACT_OPERATION_TYPE, operationType);
		}
		if(arguments.getFilterFieldValue(Parameters.SEARCH) != null) {
			predicate.add(ACT_PREDICATE_SEARCH);
			String search = ValueHelper.defaultToIfBlank((String) arguments.getFilterFieldValue(Parameters.SEARCH),"");
			filter.addField(Parameters.SEARCH, LikeStringValueBuilder.getInstance().build(search, null, null));
		}
	}
}
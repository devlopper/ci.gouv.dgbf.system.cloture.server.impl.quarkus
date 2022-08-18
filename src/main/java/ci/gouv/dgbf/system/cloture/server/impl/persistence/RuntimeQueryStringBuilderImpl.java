package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.cyk.utility.persistence.query.Filter;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder.Arguments;
import org.cyk.utility.persistence.server.query.string.RuntimeQueryStringBuilder;
import org.cyk.utility.persistence.server.query.string.WhereStringBuilder.Predicate;

import ci.gouv.dgbf.system.cloture.server.api.persistence.ActLockPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationGroupPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Parameters;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ScriptPersistence;
import io.quarkus.arc.Unremovable;

@ApplicationScoped @ci.gouv.dgbf.system.cloture.server.api.System @Unremovable
public class RuntimeQueryStringBuilderImpl extends RuntimeQueryStringBuilder.AbstractImpl implements Serializable {

	@Inject ActQueryStringBuilder actQueryStringBuilder;
	@Inject OperationActQueryStringBuilder operationActQueryStringBuilder;
	
	@Inject OperationGroupPersistence operationGroupPersistence;
	@Inject ScriptPersistence operationPersistence;
	@Inject ActPersistence actPersistence;
	@Inject ActLockPersistence actLockPersistence;
	
	@Override
	protected void setTuple(QueryExecutorArguments arguments, Arguments builderArguments) {
		if(actPersistence.isProcessable(arguments)) {
			if(arguments.getFilterFieldValue(Parameters.ACT_OPERATION_TYPE) != null) {
				builderArguments.getTuple(Boolean.TRUE).add(String.format("%s t",ActImpl.ENTITY_NAME));
				builderArguments.getTuple(Boolean.TRUE).addJoins(String.format("LEFT JOIN %s p ON p.%s = t.%s",ActLatestOperationImpl.ENTITY_NAME
						,ActLatestOperationImpl.FIELD_ACT_IDENTIFIER,ActImpl.FIELD_IDENTIFIER));
			}
		}
		super.setTuple(arguments, builderArguments);
	}
	
	@Override
	protected void populatePredicate(QueryExecutorArguments arguments, Arguments builderArguments, Predicate predicate,Filter filter) {
		super.populatePredicate(arguments, builderArguments, predicate, filter);
		if(Boolean.TRUE.equals(actQueryStringBuilder.isProcessable(arguments)))
			actQueryStringBuilder.populatePredicates(arguments, builderArguments, predicate, filter);
		else if(Boolean.TRUE.equals(operationActQueryStringBuilder.isProcessable(arguments)))
			operationActQueryStringBuilder.populatePredicates(arguments, builderArguments, predicate, filter);
		else if(Boolean.TRUE.equals(actLockPersistence.isProcessable(arguments)))
			populatePredicateActLock(arguments, builderArguments, predicate, filter);
	}
	
	@Override
	protected void setOrder(QueryExecutorArguments queryExecutorArguments, Arguments builderArguments) {
		if(Boolean.TRUE.equals(actQueryStringBuilder.isProcessable(queryExecutorArguments)))
			actQueryStringBuilder.setOrder(queryExecutorArguments, builderArguments);
		else if(Boolean.TRUE.equals(operationActQueryStringBuilder.isProcessable(queryExecutorArguments)))
			operationActQueryStringBuilder.setOrder(queryExecutorArguments, builderArguments);
		else 
			super.setOrder(queryExecutorArguments, builderArguments);
	}
	
	private void populatePredicateActLock(QueryExecutorArguments arguments, Arguments builderArguments, Predicate predicate,Filter filter) {
		if(arguments.getFilterFieldValue(Parameters.ACT_IDENTIFIER) != null) {
			predicate.add(String.format("t.%s = :%s", ActLockImpl.FIELD_ACT_IDENTIFIER,Parameters.ACT_IDENTIFIER));
			filter.addField(Parameters.ACT_IDENTIFIER, arguments.getFilterFieldValue(Parameters.ACT_IDENTIFIER));
		}
		if(arguments.getFilterFieldValue(Parameters.ACTS_REFERENCES) != null) {
			predicate.add(String.format("t.%s IN :%s", ActLockImpl.FIELD_ACT_REFERENCE,Parameters.ACTS_REFERENCES));
			filter.addField(Parameters.ACTS_REFERENCES, arguments.getFilterFieldValue(Parameters.ACTS_REFERENCES));
		}
		if(arguments.getFilterFieldValue(Parameters.ACT_TYPE) != null) {
			predicate.add(String.format("t.%s = :%s", ActLockImpl.FIELD_ACT_TYPE,Parameters.ACT_TYPE));
			filter.addField(Parameters.ACT_TYPE, arguments.getFilterFieldValue(Parameters.ACT_TYPE));
		}
	}
}
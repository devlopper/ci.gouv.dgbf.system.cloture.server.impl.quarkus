package ci.gouv.dgbf.system.cloture.server.impl.persistence;
import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.persistence.query.Filter;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Act;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActLock;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Operation;
import io.quarkus.arc.Unremovable;

@ApplicationScoped @ci.gouv.dgbf.system.cloture.server.api.System @Unremovable
public class TransientFieldsProcessorImpl extends org.cyk.utility.persistence.server.TransientFieldsProcessorImpl implements Serializable {

	@Override
	protected void __process__(Class<?> klass,Collection<?> objects,Filter filter, Collection<String> fieldsNames) {
		if(Operation.class.equals(klass) || OperationImpl.class.equals(klass))
			processOperations(CollectionHelper.cast(OperationImpl.class, objects),fieldsNames);
		else if(Act.class.equals(klass) || ActImpl.class.equals(klass))
			processActs(CollectionHelper.cast(ActImpl.class, objects),fieldsNames);
		else if(ActLock.class.equals(klass) || ActLockImpl.class.equals(klass))
			processActLocks(CollectionHelper.cast(ActLockImpl.class, objects),fieldsNames);
		else
			super.__process__(klass,objects,filter, fieldsNames);
	}
	
	public void processOperations(Collection<OperationImpl> operations,Collection<String> fieldsNames) {
		for(String fieldName : fieldsNames) {
			if(OperationImpl.FIELD_START_DATE_NUMBER_OF_MILLISECOND.equals(fieldName))
				new OperationImplStartDateNumberOfMillisecondReader().readThenSet(operations, null);
			else if(OperationImpl.FIELD_EXECUTION_BEGIN_DATE_NUMBER_OF_MILLISECOND.equals(fieldName))
				new OperationImplExecutionBeginDateNumberOfMillisecondReader().readThenSet(operations, null);
			else if(OperationImpl.FIELD_EXECUTION_END_DATE_NUMBER_OF_MILLISECOND.equals(fieldName))
				new OperationImplExecutionEndDateNumberOfMillisecondReader().readThenSet(operations, null);
			else if(OperationImpl.FIELD_START_DATE_STRING.equals(fieldName))
				new OperationImplStartDateStringReader().readThenSet(operations, null);
			else if(OperationImpl.FIELD_EXECUTION_BEGIN_DATE_STRING.equals(fieldName))
				new OperationImplExecutionBeginDateStringReader().readThenSet(operations, null);
			else if(OperationImpl.FIELD_EXECUTION_END_DATE_STRING.equals(fieldName))
				new OperationImplExecutionEndDateStringReader().readThenSet(operations, null);
		}
	}
	
	public void processActs(Collection<ActImpl> acts,Collection<String> fieldsNames) {
		for(String fieldName : fieldsNames) {
			if(ActImpl.FIELD_OPERATION_TYPE.equals(fieldName))
				new ActImplOperationTypeReader().readThenSet(acts, null);
			else if(ActImpl.FIELD_OPERATION_DATE_STRING.equals(fieldName))
				new ActImplOperationDateStringReader().readThenSet(acts, null);
			else if(ActImpl.FIELD_TRIGGER.equals(fieldName))
				new ActImplOperationTriggerReader().readThenSet(acts, null);
			else if(ActImpl.FIELD_NUMBER_OF_LOCKS_ENABLED.equals(fieldName))
				new ActImplNumberOfLocksEnabledReader().readThenSet(acts, null);
			else if(ActImpl.FIELD_STATUS_STRING.equals(fieldName))
				new ActImplStatusStringReader().readThenSet(acts, null);
			else if(ActImpl.FIELD_LATEST_OPERATION_STRING.equals(fieldName))
				new ActImplLatestOperationStringReader().readThenSet(acts, null);
			else if(ActImpl.FIELD_LOCKED_REASONS.equals(fieldName))
				new ActImplLockedReasonsReader().readThenSet(acts, null);
			
			else if(ActImpl.FIELDS_CODE_NAME_TYPE_STRING_NUMBER_OF_LOCKS_ENABLED_STATUS_STRING_LATEST_OPERATION.equals(fieldName))
				new ActImplCodeNameTypeStringNumberOfLocksEnabledStatusStringLatestOperationReader().readThenSet(acts, null);
		}
	}
	
	public void processActLocks(Collection<ActLockImpl> actLocks,Collection<String> fieldsNames) {
		for(String fieldName : fieldsNames) {
			if(ActLockImpl.FIELDS_REASON_ENABLED_ENABLED_AS_STRING_BEGIN_DATE_STRING_END_DATE_STRING_LATEST_OPERATION.equals(fieldName))
				new ActLockImplReasonEnabledEnabledStringBeginDateStringEndDateStringLatestOperationReader().readThenSet(actLocks, null);
		}
	}
}
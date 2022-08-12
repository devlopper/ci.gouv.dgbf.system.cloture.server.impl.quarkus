package ci.gouv.dgbf.system.cloture.server.impl.persistence;
import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.persistence.query.Filter;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Act;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActLock;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Script;
import io.quarkus.arc.Unremovable;

@ApplicationScoped @ci.gouv.dgbf.system.cloture.server.api.System @Unremovable
public class TransientFieldsProcessorImpl extends org.cyk.utility.persistence.server.TransientFieldsProcessorImpl implements Serializable {

	@Override
	protected void __process__(Class<?> klass,Collection<?> objects,Filter filter, Collection<String> fieldsNames) {
		if(Script.class.equals(klass) || ScriptImpl.class.equals(klass))
			processOperations(CollectionHelper.cast(ScriptImpl.class, objects),fieldsNames);
		else if(Act.class.equals(klass) || ActImpl.class.equals(klass))
			processActs(CollectionHelper.cast(ActImpl.class, objects),fieldsNames);
		else if(ActLock.class.equals(klass) || ActLockImpl.class.equals(klass))
			processActLocks(CollectionHelper.cast(ActLockImpl.class, objects),fieldsNames);
		else
			super.__process__(klass,objects,filter, fieldsNames);
	}
	
	public void processOperations(Collection<ScriptImpl> operations,Collection<String> fieldsNames) {
		for(String fieldName : fieldsNames) {
			if(ScriptImpl.FIELD_START_DATE_NUMBER_OF_MILLISECOND.equals(fieldName))
				new OperationImplStartDateNumberOfMillisecondReader().readThenSet(operations, null);
			else if(ScriptImpl.FIELD_EXECUTION_BEGIN_DATE_NUMBER_OF_MILLISECOND.equals(fieldName))
				new OperationImplExecutionBeginDateNumberOfMillisecondReader().readThenSet(operations, null);
			else if(ScriptImpl.FIELD_EXECUTION_END_DATE_NUMBER_OF_MILLISECOND.equals(fieldName))
				new OperationImplExecutionEndDateNumberOfMillisecondReader().readThenSet(operations, null);
			else if(ScriptImpl.FIELD_START_DATE_STRING.equals(fieldName))
				new OperationImplStartDateStringReader().readThenSet(operations, null);
			else if(ScriptImpl.FIELD_EXECUTION_BEGIN_DATE_STRING.equals(fieldName))
				new OperationImplExecutionBeginDateStringReader().readThenSet(operations, null);
			else if(ScriptImpl.FIELD_EXECUTION_END_DATE_STRING.equals(fieldName))
				new OperationImplExecutionEndDateStringReader().readThenSet(operations, null);
		}
	}
	
	public void processActs(Collection<ActImpl> acts,Collection<String> fieldsNames) {
		
	}
	
	public void processActLocks(Collection<ActLockImpl> actLocks,Collection<String> fieldsNames) {
		
	}
}
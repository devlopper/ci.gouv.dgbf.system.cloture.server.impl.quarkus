package ci.gouv.dgbf.system.cloture.server.impl.persistence;
import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.persistence.query.Filter;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Act;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Imputation;
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
		else if(Imputation.class.equals(klass) || ImputationImpl.class.equals(klass))
			processImputations(CollectionHelper.cast(ImputationImpl.class, objects),fieldsNames);
		else
			super.__process__(klass,objects,filter, fieldsNames);
	}
	
	public void processActs(Collection<ActImpl> acts,Collection<String> fieldsNames) {
		for(String fieldName : fieldsNames) {
			if(ActImpl.FIELDS_CODE_NAME_TYPE_AS_STRING.equals(fieldName))
				new ActImplCodeNameTypeAsStringReader().readThenSet(acts, null);
		}
	}
	
	public void processImputations(Collection<ImputationImpl> imputations,Collection<String> fieldsNames) {
		for(String fieldName : fieldsNames) {
			if(ImputationImpl.FIELDS_AS_STRING.equals(fieldName))
				new ImputationImplAsStringReader().readThenSet(imputations, null);
		}
	}
	
	public void processOperations(Collection<OperationImpl> operations,Collection<String> fieldsNames) {
		for(String fieldName : fieldsNames) {
			if(OperationImpl.FIELDS_STRINGS.equals(fieldName))
				new OperationImplStringsReader().readThenSet(operations, null);
			else if(OperationImpl.FIELD_NUMBER_OF_ACTS.equals(fieldName))
				new OperationImplNumberOfActsReader().readThenSet(operations, null);
			else if(OperationImpl.FIELD_NUMBER_OF_IMPUTATIONS.equals(fieldName))
				new OperationImplNumberOfImputationsReader().readThenSet(operations, null);
			else if(OperationImpl.FIELD_COLOR.equals(fieldName))
				new OperationImplColorReader().readThenSet(operations, null);
			else if(OperationImpl.FIELDS_STATUSES.equals(fieldName))
				new OperationImplStatusesReader().readThenSet(operations, null);
			else if(OperationImpl.FIELD___AUDIT__.equals(fieldName))
				new OperationImplAuditReader().readThenSet(operations, null);
		}
	}
}
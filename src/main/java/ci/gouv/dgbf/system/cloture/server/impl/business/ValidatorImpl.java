package ci.gouv.dgbf.system.cloture.server.impl.business;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.throwable.ThrowablesMessages;
import org.cyk.utility.business.Validator;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Operation;
import io.quarkus.arc.Unremovable;

@ApplicationScoped @ci.gouv.dgbf.system.cloture.server.api.System @Unremovable
public class ValidatorImpl extends Validator.AbstractImpl implements Serializable {

	@Override
	protected <T> void __validate__(Class<T> klass, T entity, Object actionIdentifier,ThrowablesMessages throwablesMessages) {
		super.__validate__(klass, entity, actionIdentifier, throwablesMessages);
		if(Boolean.TRUE.equals(ClassHelper.isInstanceOf(klass, Operation.class)))
			validate(actionIdentifier, (Operation) entity, throwablesMessages);
	}
	
	/**/
	
	private void validate(Object actionIdentifier,Operation operation,ThrowablesMessages throwablesMessages) {
		
	}
	
	
}
package ci.gouv.dgbf.system.cloture.server.impl.business;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowablesMessages;
import org.cyk.utility.__kernel__.time.TimeHelper;
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
		if(OperationBusinessImpl.EXECUTE.equals(actionIdentifier))
			validateOperationBeginExecution(operation, throwablesMessages);
	}
	
	public static void validateOperationBeginExecution(Operation operation,ThrowablesMessages throwablesMessages) {
		if(operation == null)
			throw new RuntimeException("L'opération à démarrer est obligatoire");
		validateOperationBeginExecutionEndDate(operation, throwablesMessages);
		validateOperationBeginExecutionBeginDate(operation, throwablesMessages);
		validateOperationBeginExecutionStartDate(operation, throwablesMessages);
		validateOperationBeginExecutionProcedureName(operation, throwablesMessages);
		validateOperationBeginExecutionTrigger(operation, throwablesMessages);
	}
	
	public static void validateOperationBeginExecutionBeginDate(Operation operation,ThrowablesMessages throwablesMessages) {
		if(operation.getExecutionBeginDate() != null && operation.getExecutionEndDate() == null)
			throwablesMessages.add(String.format("L'opération <<%s>> démarrée le %s par <<%s>> est en cours d'exécution",operation.getName()
					,TimeHelper.formatLocalDateTime(operation.getExecutionBeginDate()),TimeHelper.formatLocalDateTime(operation.getExecutionEndDate())
					,operation.getTrigger()));
	}
	
	public static void validateOperationBeginExecutionEndDate(Operation operation,ThrowablesMessages throwablesMessages) {
		if(operation.getExecutionEndDate() != null && operation.getExecutionBeginDate() != null)
			throwablesMessages.add(String.format("L'opération <<%s>> à déja été exécutée le %s par <<%s>> et s'est terminée le %s",operation.getName()
					,TimeHelper.formatLocalDateTime(operation.getExecutionBeginDate()),operation.getTrigger(),TimeHelper.formatLocalDateTime(operation.getExecutionEndDate())));
	}
	
	public static void validateOperationBeginExecutionStartDate(Operation operation,ThrowablesMessages throwablesMessages) {
		if(operation.getStartDate() == null)
			throwablesMessages.add(String.format("La date de début de l'opération <<%s>> n'a pas été défini",operation.getName()));
		else if(TimeHelper.toMillisecond(operation.getStartDate()) > System.currentTimeMillis())
			throwablesMessages.add(String.format("L'opération <<%s>> ne peut être démarrée qu'à partir du %s",operation.getName()
					,TimeHelper.formatLocalDateTime(operation.getStartDate())));
	}
	
	public static void validateOperationBeginExecutionProcedureName(Operation operation,ThrowablesMessages throwablesMessages) {
		if(StringHelper.isBlank(operation.getProcedureName()))
			throwablesMessages.add(String.format("Le nom de la procédure de l'opération <<%s>> est obligatoire",operation.getName()));
	}
	
	public static void validateOperationBeginExecutionTrigger(Operation operation,ThrowablesMessages throwablesMessages) {
		if(StringHelper.isBlank(operation.getTrigger()))
			throwablesMessages.add(String.format("Le nom d'utilisateur devant démarrer l'opération <<%s>> est obligatoire",operation.getName()));
	}
}
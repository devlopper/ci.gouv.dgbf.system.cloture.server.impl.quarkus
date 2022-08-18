package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import javax.persistence.Query;

import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Operation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Parameters;

public class ActImplIdentifierCodeOperationIdentifierReader extends AbstractActImplReader implements Serializable {

	@Override
	protected QueryStringBuilder.Arguments instantiateQueryStringBuilderArguments() {
		QueryStringBuilder.Arguments arguments =  super.instantiateQueryStringBuilderArguments();
		arguments.getProjection(Boolean.TRUE).addFromTuple("t",ActImpl.FIELD_IDENTIFIER,ActImpl.FIELD_CODE);
		arguments.getProjection(Boolean.TRUE).addFromTuple("p",FieldHelper.join(OperationActImpl.FIELD_OPERATION,OperationImpl.FIELD_IDENTIFIER));
		arguments.getTuple(Boolean.TRUE).addJoins(String.format("LEFT JOIN %s p ON p.%s = t AND p.operation.identifier = :%s",OperationActImpl.ENTITY_NAME
				,OperationActImpl.FIELD_ACT,Parameters.OPERATION_IDENTIFIER));
		return arguments;
	}
	
	@Override
	protected void setQueryParameters(Query query, Collection<String> identifiers, Map<String, Object> parameters) {
		super.setQueryParameters(query, identifiers, parameters);
		String operationIdentifier = (String) MapHelper.readByKey(parameters, Parameters.OPERATION_IDENTIFIER);
		if(StringHelper.isBlank(operationIdentifier))
			throw new RuntimeException(String.format("L'identifiant de %s est obligatoire",Operation.NAME));
		query.setParameter(Parameters.OPERATION_IDENTIFIER, operationIdentifier);
	}
	
	@Override
	protected void __set__(ActImpl act, Object[] array) {
		Integer index = 0;
		act.setIdentifier(getAsString(array, index++));
		act.setCode(getAsString(array,index++));
		__set__(act, array, index);
	}
	
	protected void __set__(ActImpl act, Object[] array,Integer index) {}
}
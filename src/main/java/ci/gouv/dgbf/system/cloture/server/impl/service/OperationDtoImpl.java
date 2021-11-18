package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.json.bind.annotation.JsonbProperty;

import org.cyk.utility.service.entity.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.service.server.AbstractServiceImpl;

import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationExecutionStatus;
import ci.gouv.dgbf.system.cloture.server.api.service.OperationDto;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationImpl;
import io.quarkus.arc.Unremovable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor @RequestScoped @Unremovable
public class OperationDtoImpl extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements OperationDto,Serializable {

	@JsonbProperty(value = JSON_START_DATE_NUMBER_OF_MILLISECOND)
	Long startDateNumberOfMillisecond;
	
	@JsonbProperty(value = JSON_GROUP_IDENTIFIER)
	String groupIdentifier;
	
	@JsonbProperty(value = JSON_PROCEDURE_NAME)
	String procedureName;
	
	@JsonbProperty(value = JSON_TRIGGER)
	String trigger;
	
	@JsonbProperty(value = JSON_EXECUTION_BEGIN_DATE_NUMBER_OF_MILLISECOND)
	Long executionBeginDateNumberOfMillisecond;
	
	@JsonbProperty(value = JSON_EXECUTION_END_DATE_NUMBER_OF_MILLISECOND)
	Long executionEndDateNumberOfMillisecond;
	
	@JsonbProperty(value = JSON_EXECUTION_STATUS)
	OperationExecutionStatus executionStatus;
	
	@Override @JsonbProperty(value = JSON_IDENTIFIER)
	public OperationDtoImpl setIdentifier(String identifier) {
		return (OperationDtoImpl) super.setIdentifier(identifier);
	}
	
	@Override @JsonbProperty(value = JSON_IDENTIFIER)
	public String getIdentifier() {
		return super.getIdentifier();
	}
	
	@Override @JsonbProperty(value = JSON_CODE)
	public OperationDtoImpl setCode(String code) {
		return (OperationDtoImpl) super.setCode(code);
	}
	
	@Override @JsonbProperty(value = JSON_CODE)
	public String getCode() {
		return super.getCode();
	}
	
	@Override @JsonbProperty(value = JSON_NAME)
	public OperationDtoImpl setName(String name) {
		return (OperationDtoImpl) super.setName(name);
	}
	
	@Override @JsonbProperty(value = JSON_NAME)
	public String getName() {
		return super.getName();
	}
	
	static {
		AbstractServiceImpl.setProjections(OperationDtoImpl.class, Map.of(
				JSON_IDENTIFIER,OperationImpl.FIELD_IDENTIFIER
    			,JSON_CODE,OperationImpl.FIELD_CODE
    			,JSON_NAME,OperationImpl.FIELD_NAME
    			,JSON_START_DATE_NUMBER_OF_MILLISECOND,OperationImpl.FIELD_START_DATE_NUMBER_OF_MILLISECOND
    			,JSON_GROUP_IDENTIFIER,OperationImpl.FIELD_GROUP_IDENTIFIER
    			,JSON_PROCEDURE_NAME,OperationImpl.FIELD_PROCEDURE_NAME
    			,JSON_TRIGGER,OperationImpl.FIELD_TRIGGER
    			,JSON_EXECUTION_BEGIN_DATE_NUMBER_OF_MILLISECOND,OperationImpl.FIELD_EXECUTION_BEGIN_DATE_NUMBER_OF_MILLISECOND
    			,JSON_EXECUTION_END_DATE_NUMBER_OF_MILLISECOND,OperationImpl.FIELD_EXECUTION_END_DATE_NUMBER_OF_MILLISECOND
    			,JSON_EXECUTION_STATUS,OperationImpl.FIELD_EXECUTION_STATUS
    			));
	}
}
package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.json.bind.annotation.JsonbProperty;

import org.cyk.utility.service.entity.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.service.server.AbstractServiceImpl;

import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationExecutionStatus;
import ci.gouv.dgbf.system.cloture.server.api.service.ScriptDto;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ScriptImpl;
import io.quarkus.arc.Unremovable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor @RequestScoped @Unremovable
public class ScriptDtoImpl extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements ScriptDto,Serializable {

	@JsonbProperty(value = JSON_START_DATE_NUMBER_OF_MILLISECOND)
	Long startDateNumberOfMillisecond;
	
	@JsonbProperty(value = JSON_START_DATE_STRING)
	String startDateString;
	
	@JsonbProperty(value = JSON_GROUP_IDENTIFIER)
	String groupIdentifier;
	
	@JsonbProperty(value = JSON_PROCEDURE_NAME)
	String procedureName;
	
	@JsonbProperty(value = JSON_TRIGGER)
	String trigger;
	
	@JsonbProperty(value = JSON_EXECUTION_BEGIN_DATE_NUMBER_OF_MILLISECOND)
	Long executionBeginDateNumberOfMillisecond;
	
	@JsonbProperty(value = JSON_EXECUTION_BEGIN_DATE_STRING)
	String executionBeginDateString;
	
	@JsonbProperty(value = JSON_EXECUTION_END_DATE_NUMBER_OF_MILLISECOND)
	Long executionEndDateNumberOfMillisecond;
	
	@JsonbProperty(value = JSON_EXECUTION_END_DATE_STRING)
	String executionEndDateString;
	
	@JsonbProperty(value = JSON_EXECUTION_STATUS)
	OperationExecutionStatus executionStatus;
	
	@Override @JsonbProperty(value = JSON_IDENTIFIER)
	public ScriptDtoImpl setIdentifier(String identifier) {
		return (ScriptDtoImpl) super.setIdentifier(identifier);
	}
	
	@Override @JsonbProperty(value = JSON_IDENTIFIER)
	public String getIdentifier() {
		return super.getIdentifier();
	}
	
	@Override @JsonbProperty(value = JSON_CODE)
	public ScriptDtoImpl setCode(String code) {
		return (ScriptDtoImpl) super.setCode(code);
	}
	
	@Override @JsonbProperty(value = JSON_CODE)
	public String getCode() {
		return super.getCode();
	}
	
	@Override @JsonbProperty(value = JSON_NAME)
	public ScriptDtoImpl setName(String name) {
		return (ScriptDtoImpl) super.setName(name);
	}
	
	@Override @JsonbProperty(value = JSON_NAME)
	public String getName() {
		return super.getName();
	}
	
	static {
		Map<String,String> map = new HashMap<>();
		map.putAll(Map.of(
				JSON_IDENTIFIER,ScriptImpl.FIELD_IDENTIFIER
    			,JSON_CODE,ScriptImpl.FIELD_CODE
    			,JSON_NAME,ScriptImpl.FIELD_NAME
    			,JSON_START_DATE_NUMBER_OF_MILLISECOND,ScriptImpl.FIELD_START_DATE_NUMBER_OF_MILLISECOND
    			,JSON_START_DATE_STRING,ScriptImpl.FIELD_START_DATE_STRING
    			,JSON_GROUP_IDENTIFIER,ScriptImpl.FIELD_GROUP_IDENTIFIER
    			,JSON_PROCEDURE_NAME,ScriptImpl.FIELD_PROCEDURE_NAME
    			,JSON_TRIGGER,ScriptImpl.FIELD_TRIGGER
    			,JSON_EXECUTION_BEGIN_DATE_NUMBER_OF_MILLISECOND,ScriptImpl.FIELD_EXECUTION_BEGIN_DATE_NUMBER_OF_MILLISECOND
    			,JSON_EXECUTION_BEGIN_DATE_STRING,ScriptImpl.FIELD_EXECUTION_BEGIN_DATE_STRING
    			));
		map.putAll(Map.of(
				JSON_EXECUTION_END_DATE_NUMBER_OF_MILLISECOND,ScriptImpl.FIELD_EXECUTION_END_DATE_NUMBER_OF_MILLISECOND
    			,JSON_EXECUTION_END_DATE_STRING,ScriptImpl.FIELD_EXECUTION_END_DATE_STRING
    			,JSON_EXECUTION_STATUS,ScriptImpl.FIELD_EXECUTION_STATUS
    			));
		AbstractServiceImpl.setProjections(ScriptDtoImpl.class, map);
	}
}
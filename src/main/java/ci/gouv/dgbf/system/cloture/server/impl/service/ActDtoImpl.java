package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.json.bind.annotation.JsonbProperty;

import org.cyk.utility.service.entity.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.service.server.AbstractServiceImpl;

import ci.gouv.dgbf.system.cloture.server.api.persistence.ActOperationType;
import ci.gouv.dgbf.system.cloture.server.api.service.ActDto;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ActImpl;
import io.quarkus.arc.Unremovable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor @RequestScoped @Unremovable
public class ActDtoImpl extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements ActDto,Serializable {

	@JsonbProperty(value = JSON_OPERATION_TYPE) ActOperationType operationType;
	@JsonbProperty(value = JSON_TRIGGER) String trigger;
	@JsonbProperty(value = JSON_OPERATION_DATE_STRING) String operationDateString;
	
	@Override @JsonbProperty(value = JSON_IDENTIFIER)
	public ActDtoImpl setIdentifier(String identifier) {
		return (ActDtoImpl) super.setIdentifier(identifier);
	}
	
	@Override @JsonbProperty(value = JSON_IDENTIFIER)
	public String getIdentifier() {
		return super.getIdentifier();
	}
	
	@Override @JsonbProperty(value = JSON_CODE)
	public ActDtoImpl setCode(String code) {
		return (ActDtoImpl) super.setCode(code);
	}
	
	@Override @JsonbProperty(value = JSON_CODE)
	public String getCode() {
		return super.getCode();
	}
	
	@Override @JsonbProperty(value = JSON_NAME)
	public ActDtoImpl setName(String name) {
		return (ActDtoImpl) super.setName(name);
	}
	
	@Override @JsonbProperty(value = JSON_NAME)
	public String getName() {
		return super.getName();
	}
	
	static {
		Map<String,String> map = new HashMap<>();
		map.putAll(Map.of(
				JSON_IDENTIFIER,ActImpl.FIELD_IDENTIFIER
    			,JSON_CODE,ActImpl.FIELD_CODE
    			,JSON_NAME,ActImpl.FIELD_NAME
    			,JSON_OPERATION_TYPE,ActImpl.FIELD_OPERATION_TYPE
    			,JSON_TRIGGER,ActImpl.FIELD_TRIGGER
    			,JSON_OPERATION_DATE_STRING,ActImpl.FIELD_OPERATION_DATE_STRING
    			));
		AbstractServiceImpl.setProjections(ActDtoImpl.class, map);
	}
}
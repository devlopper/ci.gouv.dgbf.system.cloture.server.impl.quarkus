package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.json.bind.annotation.JsonbProperty;

import org.cyk.utility.service.entity.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.service.server.AbstractServiceImpl;

import ci.gouv.dgbf.system.cloture.server.api.service.OperationDto;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationImpl;
import io.quarkus.arc.Unremovable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor @RequestScoped @Unremovable
public class OperationDtoImpl extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements OperationDto,Serializable {

	@JsonbProperty(value = JSON_TYPE_AS_STRING)
	String typeAsString;
	
	@JsonbProperty(value = JSON_REASON)
	String reason;
	
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
	
	public static void setProjections(){
		Map<String,String> map = new HashMap<>();
		map.putAll(Map.of(
				JSON_IDENTIFIER,OperationImpl.FIELD_IDENTIFIER
    			,JSON_CODE,OperationImpl.FIELD_CODE
    			,JSON_NAME,OperationImpl.FIELD_NAME
    			,JSON_TYPE_AS_STRING,OperationImpl.FIELD_TYPE_AS_STRING
    			,JSON_REASON,OperationImpl.FIELD_REASON
    			));
		AbstractServiceImpl.setProjections(OperationDtoImpl.class, map);
	}
}
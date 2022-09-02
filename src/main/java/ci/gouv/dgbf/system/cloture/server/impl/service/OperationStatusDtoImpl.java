package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.json.bind.annotation.JsonbProperty;

import org.cyk.utility.service.entity.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.service.server.AbstractServiceImpl;

import ci.gouv.dgbf.system.cloture.server.api.service.OperationStatusDto;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationStatusImpl;
import io.quarkus.arc.Unremovable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor @RequestScoped @Unremovable
public class OperationStatusDtoImpl extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements OperationStatusDto,Serializable {

	@JsonbProperty(value = JSON_ORDER_NUMBER) Byte orderNumber;
	
	@Override @JsonbProperty(value = JSON_IDENTIFIER)
	public OperationStatusDtoImpl setIdentifier(String identifier) {
		return (OperationStatusDtoImpl) super.setIdentifier(identifier);
	}
	
	@Override @JsonbProperty(value = JSON_IDENTIFIER)
	public String getIdentifier() {
		return super.getIdentifier();
	}
	
	@Override @JsonbProperty(value = JSON_CODE)
	public OperationStatusDtoImpl setCode(String code) {
		return (OperationStatusDtoImpl) super.setCode(code);
	}
	
	@Override @JsonbProperty(value = JSON_CODE)
	public String getCode() {
		return super.getCode();
	}
	
	@Override @JsonbProperty(value = JSON_NAME)
	public OperationStatusDtoImpl setName(String name) {
		return (OperationStatusDtoImpl) super.setName(name);
	}
	
	@Override @JsonbProperty(value = JSON_NAME)
	public String getName() {
		return super.getName();
	}
	
	public static void setProjections() {
		Map<String,String> map = new HashMap<>();
		map.putAll(Map.of(
				JSON_IDENTIFIER,OperationStatusImpl.FIELD_IDENTIFIER
    			,JSON_CODE,OperationStatusImpl.FIELD_CODE
    			,JSON_NAME,OperationStatusImpl.FIELD_NAME
    			,JSON_ORDER_NUMBER,OperationStatusImpl.FIELD_ORDER_NUMBER
    			));
		AbstractServiceImpl.setProjections(OperationStatusDtoImpl.class, map);
	}
}
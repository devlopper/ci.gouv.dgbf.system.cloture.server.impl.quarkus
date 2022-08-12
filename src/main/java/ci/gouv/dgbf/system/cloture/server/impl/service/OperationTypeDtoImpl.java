package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.json.bind.annotation.JsonbProperty;

import org.cyk.utility.service.entity.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.service.server.AbstractServiceImpl;

import ci.gouv.dgbf.system.cloture.server.api.service.OperationTypeDto;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationTypeImpl;
import io.quarkus.arc.Unremovable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor @RequestScoped @Unremovable
public class OperationTypeDtoImpl extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements OperationTypeDto,Serializable {

	@Override @JsonbProperty(value = JSON_IDENTIFIER)
	public OperationTypeDtoImpl setIdentifier(String identifier) {
		return (OperationTypeDtoImpl) super.setIdentifier(identifier);
	}
	
	@Override @JsonbProperty(value = JSON_IDENTIFIER)
	public String getIdentifier() {
		return super.getIdentifier();
	}
	
	@Override @JsonbProperty(value = JSON_CODE)
	public OperationTypeDtoImpl setCode(String code) {
		return (OperationTypeDtoImpl) super.setCode(code);
	}
	
	@Override @JsonbProperty(value = JSON_CODE)
	public String getCode() {
		return super.getCode();
	}
	
	@Override @JsonbProperty(value = JSON_NAME)
	public OperationTypeDtoImpl setName(String name) {
		return (OperationTypeDtoImpl) super.setName(name);
	}
	
	@Override @JsonbProperty(value = JSON_NAME)
	public String getName() {
		return super.getName();
	}
	
	public static void setProjections() {
		Map<String,String> map = new HashMap<>();
		map.putAll(Map.of(
				JSON_IDENTIFIER,OperationTypeImpl.FIELD_IDENTIFIER
    			,JSON_CODE,OperationTypeImpl.FIELD_CODE
    			,JSON_NAME,OperationTypeImpl.FIELD_NAME
    			));
		AbstractServiceImpl.setProjections(OperationTypeDtoImpl.class, map);
	}
}
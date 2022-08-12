package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.json.bind.annotation.JsonbProperty;

import org.cyk.utility.service.entity.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.service.server.AbstractServiceImpl;

import ci.gouv.dgbf.system.cloture.server.api.service.ActTypeDto;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ActTypeImpl;
import io.quarkus.arc.Unremovable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor @RequestScoped @Unremovable
public class ActTypeDtoImpl extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements ActTypeDto,Serializable {

	@Override @JsonbProperty(value = JSON_IDENTIFIER)
	public ActTypeDtoImpl setIdentifier(String identifier) {
		return (ActTypeDtoImpl) super.setIdentifier(identifier);
	}
	
	@Override @JsonbProperty(value = JSON_IDENTIFIER)
	public String getIdentifier() {
		return super.getIdentifier();
	}
	
	@Override @JsonbProperty(value = JSON_CODE)
	public ActTypeDtoImpl setCode(String code) {
		return (ActTypeDtoImpl) super.setCode(code);
	}
	
	@Override @JsonbProperty(value = JSON_CODE)
	public String getCode() {
		return super.getCode();
	}
	
	@Override @JsonbProperty(value = JSON_NAME)
	public ActTypeDtoImpl setName(String name) {
		return (ActTypeDtoImpl) super.setName(name);
	}
	
	@Override @JsonbProperty(value = JSON_NAME)
	public String getName() {
		return super.getName();
	}
	
	public static void setProjections() {
		Map<String,String> map = new HashMap<>();
		map.putAll(Map.of(
				JSON_IDENTIFIER,ActTypeImpl.FIELD_IDENTIFIER
    			,JSON_CODE,ActTypeImpl.FIELD_CODE
    			,JSON_NAME,ActTypeImpl.FIELD_NAME
    			));
		AbstractServiceImpl.setProjections(ActTypeDtoImpl.class, map);
	}
}
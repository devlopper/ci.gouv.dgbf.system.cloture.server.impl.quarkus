package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.json.bind.annotation.JsonbProperty;

import org.cyk.utility.service.entity.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.service.server.AbstractServiceImpl;

import ci.gouv.dgbf.system.cloture.server.api.service.EconomicNatureDto;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.EconomicNatureImpl;
import io.quarkus.arc.Unremovable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor @RequestScoped @Unremovable
public class EconomicNatureDtoImpl extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements EconomicNatureDto,Serializable {

	@Override @JsonbProperty(value = JSON_IDENTIFIER)
	public EconomicNatureDtoImpl setIdentifier(String identifier) {
		return (EconomicNatureDtoImpl) super.setIdentifier(identifier);
	}
	
	@Override @JsonbProperty(value = JSON_IDENTIFIER)
	public String getIdentifier() {
		return super.getIdentifier();
	}
	
	@Override @JsonbProperty(value = JSON_CODE)
	public EconomicNatureDtoImpl setCode(String code) {
		return (EconomicNatureDtoImpl) super.setCode(code);
	}
	
	@Override @JsonbProperty(value = JSON_CODE)
	public String getCode() {
		return super.getCode();
	}
	
	@Override @JsonbProperty(value = JSON_NAME)
	public EconomicNatureDtoImpl setName(String name) {
		return (EconomicNatureDtoImpl) super.setName(name);
	}
	
	@Override @JsonbProperty(value = JSON_NAME)
	public String getName() {
		return super.getName();
	}
	
	public static void setProjections() {
		Map<String,String> map = new HashMap<>();
		map.putAll(Map.of(
				JSON_IDENTIFIER,EconomicNatureImpl.FIELD_IDENTIFIER
    			,JSON_CODE,EconomicNatureImpl.FIELD_CODE
    			,JSON_NAME,EconomicNatureImpl.FIELD_NAME
    			));
		AbstractServiceImpl.setProjections(EconomicNatureDtoImpl.class, map);
	}
}
package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.json.bind.annotation.JsonbProperty;

import org.cyk.utility.service.entity.AbstractIdentifiableSystemScalarStringImpl;
import org.cyk.utility.service.server.AbstractServiceImpl;

import ci.gouv.dgbf.system.cloture.server.api.service.ImputationDto;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ImputationImpl;
import io.quarkus.arc.Unremovable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor @RequestScoped @Unremovable
public class ImputationDtoImpl extends AbstractIdentifiableSystemScalarStringImpl implements ImputationDto,Serializable {

	@JsonbProperty(value = JSON_EXERCISE_AS_STRING) String exerciseAsString;
	@JsonbProperty(value = JSON_ACTIVITY_AS_STRING) String activityAsString;
	@JsonbProperty(value = JSON_ECONOMIC_NATURE_AS_STRING) String economicNatureAsString;
	
	@Override @JsonbProperty(value = JSON_IDENTIFIER)
	public ImputationDtoImpl setIdentifier(String identifier) {
		return (ImputationDtoImpl) super.setIdentifier(identifier);
	}
	
	@Override @JsonbProperty(value = JSON_IDENTIFIER)
	public String getIdentifier() {
		return super.getIdentifier();
	}
	
	public static void setProjections() {
		Map<String,String> map = new HashMap<>();
		map.putAll(Map.of(
				JSON_IDENTIFIER,ImputationImpl.FIELD_IDENTIFIER
    			
    			));
		map.putAll(Map.of(
				JSONS_AS_STRING,ImputationImpl.FIELDS_AS_STRING
				
    			));
		AbstractServiceImpl.setProjections(ImputationDtoImpl.class, map);
	}
}
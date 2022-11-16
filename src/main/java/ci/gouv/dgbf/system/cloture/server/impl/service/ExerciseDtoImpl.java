package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.json.bind.annotation.JsonbProperty;

import org.cyk.utility.service.entity.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.service.server.AbstractServiceImpl;

import ci.gouv.dgbf.system.cloture.server.api.service.ExerciseDto;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ExerciseImpl;
import io.quarkus.arc.Unremovable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor @RequestScoped @Unremovable
public class ExerciseDtoImpl extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements ExerciseDto,Serializable {

	@JsonbProperty(value = JSON_YEAR) Short year;
	@JsonbProperty(value = JSON_YEAR_AS_STRING) String yearAsString;
	
	@Override @JsonbProperty(value = JSON_IDENTIFIER)
	public ExerciseDtoImpl setIdentifier(String identifier) {
		return (ExerciseDtoImpl) super.setIdentifier(identifier);
	}
	
	@Override @JsonbProperty(value = JSON_IDENTIFIER)
	public String getIdentifier() {
		return super.getIdentifier();
	}
	
	@Override @JsonbProperty(value = JSON_CODE)
	public ExerciseDtoImpl setCode(String code) {
		return (ExerciseDtoImpl) super.setCode(code);
	}
	
	@Override @JsonbProperty(value = JSON_CODE)
	public String getCode() {
		return super.getCode();
	}
	
	@Override @JsonbProperty(value = JSON_NAME)
	public ExerciseDtoImpl setName(String name) {
		return (ExerciseDtoImpl) super.setName(name);
	}
	
	@Override @JsonbProperty(value = JSON_NAME)
	public String getName() {
		return super.getName();
	}
	
	public static void setProjections() {
		Map<String,String> map = new HashMap<>();
		map.putAll(Map.of(
				JSON_IDENTIFIER,ExerciseImpl.FIELD_IDENTIFIER
    			,JSON_CODE,ExerciseImpl.FIELD_CODE
    			,JSON_NAME,ExerciseImpl.FIELD_NAME
    			,JSON_YEAR,ExerciseImpl.FIELD_YEAR
    			));
		AbstractServiceImpl.setProjections(ExerciseDtoImpl.class, map);
	}
}
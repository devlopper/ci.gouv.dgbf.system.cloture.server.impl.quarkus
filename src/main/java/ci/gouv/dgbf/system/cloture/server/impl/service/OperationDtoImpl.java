package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.json.bind.annotation.JsonbProperty;

import org.cyk.utility.service.entity.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import ci.gouv.dgbf.system.cloture.server.api.service.OperationDto;
import io.quarkus.arc.Unremovable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor @RequestScoped @Unremovable
public class OperationDtoImpl extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements OperationDto,Serializable {

	@JsonbProperty(value = OperationDto.JSON_START_DATE_NUMBER_OF_MILLISECOND)
	Long startDateNumberOfMillisecond;
	
	@JsonbProperty(value = OperationDto.JSON_GROUP_IDENTIFIER)
	String groupIdentifier;
	
	@Override @JsonbProperty(value = OperationDto.JSON_IDENTIFIER)
	public OperationDtoImpl setIdentifier(String identifier) {
		return (OperationDtoImpl) super.setIdentifier(identifier);
	}
	
	@Override @JsonbProperty(value = OperationDto.JSON_IDENTIFIER)
	public String getIdentifier() {
		return super.getIdentifier();
	}
	
	@Override @JsonbProperty(value = OperationDto.JSON_CODE)
	public OperationDtoImpl setCode(String code) {
		return (OperationDtoImpl) super.setCode(code);
	}
	
	@Override @JsonbProperty(value = OperationDto.JSON_CODE)
	public String getCode() {
		return super.getCode();
	}
	
	@Override @JsonbProperty(value = OperationDto.JSON_NAME)
	public OperationDtoImpl setName(String name) {
		return (OperationDtoImpl) super.setName(name);
	}
	
	@Override @JsonbProperty(value = OperationDto.JSON_NAME)
	public String getName() {
		return super.getName();
	}
	
}
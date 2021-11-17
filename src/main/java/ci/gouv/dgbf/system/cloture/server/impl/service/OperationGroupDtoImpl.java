package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.json.bind.annotation.JsonbProperty;

import org.cyk.utility.service.entity.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import ci.gouv.dgbf.system.cloture.server.api.service.OperationGroupDto;
import io.quarkus.arc.Unremovable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor @RequestScoped @Unremovable
public class OperationGroupDtoImpl extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements OperationGroupDto,Serializable {

	@Override @JsonbProperty(value = OperationGroupDto.JSON_IDENTIFIER)
	public OperationGroupDtoImpl setIdentifier(String identifier) {
		return (OperationGroupDtoImpl) super.setIdentifier(identifier);
	}
	
	@Override @JsonbProperty(value = OperationGroupDto.JSON_IDENTIFIER)
	public String getIdentifier() {
		return super.getIdentifier();
	}
	
	@Override @JsonbProperty(value = OperationGroupDto.JSON_CODE)
	public OperationGroupDtoImpl setCode(String code) {
		return (OperationGroupDtoImpl) super.setCode(code);
	}
	
	@Override @JsonbProperty(value = OperationGroupDto.JSON_CODE)
	public String getCode() {
		return super.getCode();
	}
	
	@Override @JsonbProperty(value = OperationGroupDto.JSON_NAME)
	public OperationGroupDtoImpl setName(String name) {
		return (OperationGroupDtoImpl) super.setName(name);
	}
	
	@Override @JsonbProperty(value = OperationGroupDto.JSON_NAME)
	public String getName() {
		return super.getName();
	}
	
}
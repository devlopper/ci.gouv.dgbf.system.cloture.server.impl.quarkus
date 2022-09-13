package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.json.bind.annotation.JsonbProperty;

import org.cyk.utility.service.entity.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableAuditedImpl;
import org.cyk.utility.service.server.AbstractServiceImpl;
import org.cyk.utility.service.server.entity.ColorDtoImpl;

import ci.gouv.dgbf.system.cloture.server.api.service.OperationDto;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationImpl;
import io.quarkus.arc.Unremovable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor @RequestScoped @Unremovable
public class OperationDtoImpl extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableAuditedImpl implements OperationDto,Serializable {

	//@JsonbProperty(value = JSON_TYPE)
	//OperationTypeDtoImpl type;
	
	@JsonbProperty(value = JSON_TYPE_AS_STRING) String typeAsString;
	@JsonbProperty(value = JSON_REASON) String reason;
	@JsonbProperty(value = JSON_STATUS_CODE) String statusCode;	
	@JsonbProperty(value = JSON_STATUS_AS_STRING) String statusAsString;
	@JsonbProperty(value = JSON_CREATED) Boolean created;
	@JsonbProperty(value = JSON_STARTED) Boolean started;
	@JsonbProperty(value = JSON_EXECUTED) Boolean executed;
	@JsonbProperty(value = JSON_NUMBER_OF_ACTS) Long numberOfActs;
	@JsonbProperty(value = JSON_COLOR) ColorDtoImpl color;
	
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
	
	@Override @JsonbProperty(value = OperationDto.JSON___AUDIT__)
	public OperationDtoImpl set__audit__(String __audit__) {
		return (OperationDtoImpl) super.set__audit__(__audit__);
	}
	
	@Override @JsonbProperty(value = OperationDto.JSON___AUDIT__)
	public String get__audit__() {
		return super.get__audit__();
	}
	
	public static void setProjections(){
		Map<String,String> map = new HashMap<>();
		map.putAll(Map.of(
				JSON_IDENTIFIER,OperationImpl.FIELD_IDENTIFIER
    			,JSON_CODE,OperationImpl.FIELD_CODE
    			,JSON_NAME,OperationImpl.FIELD_NAME
    			,JSON_TYPE,OperationImpl.FIELD_TYPE
    			,JSON_TYPE_AS_STRING,OperationImpl.FIELD_TYPE_AS_STRING
    			,JSON_REASON,OperationImpl.FIELD_REASON
    			,JSON_STATUS,OperationImpl.FIELD_STATUS
    			//,JSON_STATUS_CODE,OperationImpl.FIELD_STATUS_CODE
    			,JSON_STATUS_AS_STRING,OperationImpl.FIELD_STATUS_AS_STRING
    			
    			,JSON___AUDIT__,OperationImpl.FIELD___AUDIT__
    			
    			,JSONS_STRINGS,OperationImpl.FIELDS_STRINGS
    			));
		
		map.putAll(Map.of(
				JSON_COLOR,OperationImpl.FIELD_COLOR
    			,JSON_NUMBER_OF_ACTS,OperationImpl.FIELD_NUMBER_OF_ACTS
				,JSONS_STATUSES,OperationImpl.FIELDS_STATUSES
    			));
		
		AbstractServiceImpl.setProjections(OperationDtoImpl.class, map);
	}
}
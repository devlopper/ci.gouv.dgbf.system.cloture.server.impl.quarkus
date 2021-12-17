package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.json.bind.annotation.JsonbProperty;

import org.cyk.utility.service.entity.AbstractIdentifiableSystemScalarStringImpl;
import org.cyk.utility.service.server.AbstractServiceImpl;

import ci.gouv.dgbf.system.cloture.server.api.service.ActLockDto;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ActLockImpl;
import io.quarkus.arc.Unremovable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor @RequestScoped @Unremovable
public class ActLockDtoImpl extends AbstractIdentifiableSystemScalarStringImpl implements ActLockDto,Serializable {

	@JsonbProperty(value = JSON_ENABLED) Boolean enabled;
	@JsonbProperty(value = JSON_REASON) String reason;
	@JsonbProperty(value = JSON_ENABLED_STRING) String enabledString;
	@JsonbProperty(value = JSON_BEGIN_DATE) String beginDateString;
	@JsonbProperty(value = JSON_END_DATE) String endDateString;
	@JsonbProperty(value = JSON_LATEST_OPERATION) String latestOperation;
	
	@Override @JsonbProperty(value = JSON_IDENTIFIER)
	public ActLockDtoImpl setIdentifier(String identifier) {
		return (ActLockDtoImpl) super.setIdentifier(identifier);
	}
	
	@Override @JsonbProperty(value = JSON_IDENTIFIER)
	public String getIdentifier() {
		return super.getIdentifier();
	}
	
	static {
		Map<String,String> map = new HashMap<>();
		map.putAll(Map.of(
				JSON_IDENTIFIER,ActLockImpl.FIELD_IDENTIFIER
    			,JSON_ENABLED,ActLockImpl.FIELD_ENABLED
    			,JSON_ENABLED_STRING,ActLockImpl.FIELD_ENABLED_STRING
    			,JSON_REASON,ActLockImpl.FIELD_REASON
    			,JSON_BEGIN_DATE,ActLockImpl.FIELD_BEGIN_DATE_STRING
    			,JSON_END_DATE,ActLockImpl.FIELD_END_DATE_STRING
    			,JSON_LATEST_OPERATION,ActLockImpl.FIELD_LATEST_OPERATION
    			,JSONS_REASON_ENABLED_ENABLED_AS_STRING_BEGIN_DATE_STRING_END_DATE_STRING_LATEST_OPERATION,ActLockImpl.FIELDS_REASON_ENABLED_ENABLED_AS_STRING_BEGIN_DATE_STRING_END_DATE_STRING_LATEST_OPERATION
    			));
		AbstractServiceImpl.setProjections(ActLockDtoImpl.class, map);
	}
}
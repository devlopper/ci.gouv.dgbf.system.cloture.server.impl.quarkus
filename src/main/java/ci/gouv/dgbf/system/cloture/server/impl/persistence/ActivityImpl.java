package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.cyk.utility.persistence.entity.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Activity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) 
@Entity(name = ActivityImpl.ENTITY_NAME) @Access(AccessType.FIELD)
@Table(name=ActivityImpl.TABLE_NAME)
public class ActivityImpl extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Activity,Serializable {

	@Override
	public ActivityImpl setIdentifier(String identifier) {
		return (ActivityImpl) super.setIdentifier(identifier);
	}
	
	@Override
	public ActivityImpl setCode(String code) {
		return (ActivityImpl) super.setCode(code);
	}
	
	@Override
	public ActivityImpl setName(String name) {
		return (ActivityImpl) super.setName(name);
	}
	
	public static final String ENTITY_NAME = "ActivityImpl";
	public static final String TABLE_NAME = "VMA_ACTIVITE";
}
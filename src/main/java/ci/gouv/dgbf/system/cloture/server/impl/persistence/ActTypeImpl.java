package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.cyk.utility.persistence.entity.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import ci.gouv.dgbf.system.cloture.server.api.persistence.ActType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) 
@Entity(name = ActTypeImpl.ENTITY_NAME) @Access(AccessType.FIELD)
@Table(name=ActTypeImpl.TABLE_NAME)
public class ActTypeImpl extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements ActType,Serializable {

	@Override
	public ActTypeImpl setIdentifier(String identifier) {
		return (ActTypeImpl) super.setIdentifier(identifier);
	}
	
	@Override
	public ActTypeImpl setCode(String code) {
		return (ActTypeImpl) super.setCode(code);
	}
	
	@Override
	public ActTypeImpl setName(String name) {
		return (ActTypeImpl) super.setName(name);
	}
	
	public static final String ENTITY_NAME = "ActTypeImpl";
	public static final String TABLE_NAME = "VMA_TYPE_ACTE";
}
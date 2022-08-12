package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.cyk.utility.persistence.entity.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Act;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) 
@Entity(name = ActImpl.ENTITY_NAME) @Access(AccessType.FIELD)
@Table(name=ActImpl.TABLE_NAME)
public class ActImpl extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Act,Serializable {

 	@ManyToOne @JoinColumn(name = COLUMN_TYPE) ActTypeImpl type;
 	String typeAsString;
	/*
	@Transient Boolean locked;
	@Transient Collection<String> lockedReasons;
	@Transient Integer numberOfLocks;
	@Transient Integer numberOfLocksEnabled;*/
	
	@Override
	public ActImpl setIdentifier(String identifier) {
		return (ActImpl) super.setIdentifier(identifier);
	}
	
	@Override
	public ActImpl setCode(String code) {
		return (ActImpl) super.setCode(code);
	}
	
	@Override
	public ActImpl setName(String name) {
		return (ActImpl) super.setName(name);
	}
	
	@Override
	public Act setType(ActType type) {
		this.type = (ActTypeImpl) type;
		return this;
	}
	
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_TYPE_AS_STRING = "typeAsString";
	
	public static final String ENTITY_NAME = "ActImpl";
	public static final String TABLE_NAME = "VMA_ACTE";
	
	public static final String COLUMN_TYPE = "type";
}
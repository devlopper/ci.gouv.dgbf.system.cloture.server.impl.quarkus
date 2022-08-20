package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.cyk.utility.persistence.entity.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Act;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActType;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Parameters;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) 
@Entity(name = ActImpl.ENTITY_NAME) @Access(AccessType.FIELD)
@Table(name=ActImpl.TABLE_NAME)
@Cacheable
@org.hibernate.annotations.Immutable
@NamedQueries(value = {
		@NamedQuery(name = ActImpl.QUERY_READ_IDENTIFIERS_WHERE_NOT_EXIST_BY_OPERATION_IDENTIFIER,query = 
				"SELECT t.identifier FROM ActImpl t WHERE NOT EXISTS(SELECT p FROM OperationActImpl p WHERE p.act = t AND p.operation.identifier = :"+Parameters.OPERATION_IDENTIFIER+")")
})
public class ActImpl extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Act,Serializable {

 	@ManyToOne @JoinColumn(name = COLUMN_TYPE) ActTypeImpl type;
 	@Transient String typeAsString;
	
	@Transient Boolean locked;
	@Transient String lockedAsString;
	@Transient Collection<String> lockedReasons;
	@Transient Integer numberOfLocks;
	@Transient Integer numberOfLocksEnabled;
	
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
	public static final String FIELD_LOCKED = "locked";
	public static final String FIELD_LOCKED_AS_STRING = "lockedAsString";
	public static final String FIELD_LOCKED_REASONS = "lockedReasons";
	public static final String FIELD_NUMBER_OF_LOCKS = "numberOfLocks";
	public static final String FIELD_NUMBER_OF_LOCKS_ENABLED = "numberOfLocksEnabled";
	
	public static final String FIELDS_CODE_NAME_TYPE_AS_STRING = "codeNameTypeAsString";
	
	public static final String ENTITY_NAME = "ActImpl";
	public static final String TABLE_NAME = "VMA_ACTE";
	
	public static final String COLUMN_TYPE = "type";
	
	public static final String QUERY_READ_IDENTIFIERS_WHERE_NOT_EXIST_BY_OPERATION_IDENTIFIER = "ActImpl.readIdentifiersWhereNotExistByOperationIdentifier";
}
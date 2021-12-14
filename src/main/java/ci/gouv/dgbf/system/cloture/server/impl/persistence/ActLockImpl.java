package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.cyk.utility.persistence.entity.AbstractIdentifiableSystemScalarStringImpl;

import ci.gouv.dgbf.system.cloture.server.api.persistence.ActLock;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) 
@Entity(name = ActLockImpl.ENTITY_NAME) @Access(AccessType.FIELD)
@Table(name=ActLockImpl.TABLE_NAME)
public class ActLockImpl extends AbstractIdentifiableSystemScalarStringImpl implements ActLock,Serializable {

	@Column(name = COLUMN_LOCK_TYPE) String lockType;
	@Column(name = COLUMN_ACT_IDENTIFIER) String actIdentifier;
	@Column(name = COLUMN_ACT_REFERENCE) String actReference;
	@Column(name = COLUMN_ACT_TYPE) @Enumerated(EnumType.STRING) ActType actType;
	@Column(name = COLUMN_REASON) String reason;
	@Column(name = COLUMN_ENABLED) Boolean enabled;
	
	@Override
	public ActOperationImpl setIdentifier(String identifier) {
		return (ActOperationImpl) super.setIdentifier(identifier);
	}
	
	public static final String FIELD_LOCK_TYPE = "lockType";
	public static final String FIELD_ACT_IDENTIFIER = "actIdentifier";
	public static final String FIELD_ACT_REFERENCE = "actReference";
	public static final String FIELD_ACT_TYPE = "actType";
	public static final String FIELD_REASON = "reason";
	public static final String FIELD_ENABLED = "enabled";
	
	public static final String ENTITY_NAME = "ActLockImpl";
	public static final String TABLE_NAME = "VA_VERROU";
	
	public static final String COLUMN_ACT_IDENTIFIER = "acte_identifiant";
	public static final String COLUMN_ACT_REFERENCE = "acte_reference";
	public static final String COLUMN_ACT_TYPE = "acte_type";
	public static final String COLUMN_LOCK_TYPE = "type";
	public static final String COLUMN_REASON = "motif";
	public static final String COLUMN_ENABLED = "actif";
}
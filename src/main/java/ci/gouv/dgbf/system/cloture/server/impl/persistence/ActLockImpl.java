package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	@Column(name = COLUMN_BEGIN_DATE) LocalDateTime beginDate;
	@Column(name = COLUMN_END_DATE) LocalDateTime endDate;
	
	@Transient String enabledString;
	@Transient String beginDateString;
	@Transient String endDateString;
	@Transient String latestOperation;
	
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
	public static final String FIELD_ENABLED_STRING = "enabledString";
	public static final String FIELD_BEGIN_DATE = "beginDate";
	public static final String FIELD_BEGIN_DATE_STRING = "beginDateString";
	public static final String FIELD_END_DATE = "endDate";
	public static final String FIELD_END_DATE_STRING = "endDateString";
	public static final String FIELD_LATEST_OPERATION = "latestOperation";
	
	public static final String FIELDS_REASON_ENABLED_ENABLED_AS_STRING_BEGIN_DATE_STRING_END_DATE_STRING_LATEST_OPERATION = "reasonEnabledEnabledStringBeginDateStringEndDateStringLatestOperation";
	
	public static final String ENTITY_NAME = "ActLockImpl";
	public static final String TABLE_NAME = "VA_VERROU";
	
	public static final String COLUMN_ACT_IDENTIFIER = "acte_identifiant";
	public static final String COLUMN_ACT_REFERENCE = "acte_reference";
	public static final String COLUMN_ACT_TYPE = "acte_type";
	public static final String COLUMN_LOCK_TYPE = "type";
	public static final String COLUMN_REASON = "motif";
	public static final String COLUMN_ENABLED = "actif";
	public static final String COLUMN_BEGIN_DATE = "date_debut";
	public static final String COLUMN_END_DATE = "date_fin";
}
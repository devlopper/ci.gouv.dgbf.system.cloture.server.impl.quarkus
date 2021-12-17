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

import org.cyk.utility.persistence.entity.AbstractIdentifiableSystemScalarStringImpl;

import ci.gouv.dgbf.system.cloture.server.api.persistence.ActOperationType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) 
@Entity(name = ActLockLatestOperationImpl.ENTITY_NAME) @Access(AccessType.FIELD)
@Table(name=ActLockLatestOperationImpl.TABLE_NAME)
public class ActLockLatestOperationImpl extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {

	@Column(name = ActLockLatestOperationImpl.COLUMN_OPERATION_TYPE) @Enumerated(EnumType.STRING) ActOperationType operationType;
	@Column(name = ActLockLatestOperationImpl.COLUMN_OPERATION_DATE) LocalDateTime operationDate;
	@Column(name = ActLockLatestOperationImpl.COLUMN_TRIGGER) String trigger;	
	
	@Override
	public ActLockLatestOperationImpl setIdentifier(String identifier) {
		return (ActLockLatestOperationImpl) super.setIdentifier(identifier);
	}
	
	public static final String FIELD_OPERATION_TYPE = "operationType";
	public static final String FIELD_OPERATION_DATE = "operationDate";
	public static final String FIELD_TRIGGER = "trigger";
	
	public static final String ENTITY_NAME = "ActLockLatestOperationImpl";
	public static final String TABLE_NAME = "VA_VERROU_DERNIERE_OPERATION";
	
	public static final String COLUMN_OPERATION_TYPE = "operation";
	public static final String COLUMN_OPERATION_DATE = "operation_date";
	public static final String COLUMN_TRIGGER = "declencheur";
}
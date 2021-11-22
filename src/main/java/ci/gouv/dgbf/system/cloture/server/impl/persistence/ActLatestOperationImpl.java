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
@Entity(name = ActLatestOperationImpl.ENTITY_NAME) @Access(AccessType.FIELD)
@Table(name=ActLatestOperationImpl.TABLE_NAME)
public class ActLatestOperationImpl extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {

	@Column(name = ActOperationImpl.COLUMN_ACT_IDENTIFIER) String actIdentifier;
	@Column(name = ActOperationImpl.COLUMN_OPERATION_TYPE) @Enumerated(EnumType.STRING) ActOperationType operationType;
	@Column(name = ActOperationImpl.COLUMN_OPERATION_DATE) LocalDateTime operationDate;
	@Column(name = ActOperationImpl.COLUMN_TRIGGER) String trigger;	
	
	@Override
	public ActLatestOperationImpl setIdentifier(String identifier) {
		return (ActLatestOperationImpl) super.setIdentifier(identifier);
	}
	
	public static final String FIELD_ACT_IDENTIFIER = "actIdentifier";
	public static final String FIELD_OPERATION_TYPE = "operationType";
	public static final String FIELD_OPERATION_DATE = "operationDate";
	public static final String FIELD_TRIGGER = "trigger";
	
	public static final String ENTITY_NAME = "ActLatestOperationImpl";
	public static final String TABLE_NAME = "VA_ACTE_DERNIERE_OPERATION";
}
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
import javax.validation.constraints.NotNull;

import org.cyk.utility.persistence.entity.AbstractIdentifiableSystemScalarStringImpl;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Act;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActOperationType;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActOperation;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) 
/*@Entity(name = ActOperationImpl.ENTITY_NAME) @Access(AccessType.FIELD)
@Table(name=ActOperationImpl.TABLE_NAME)
*/
public class ActOperationImpl extends AbstractIdentifiableSystemScalarStringImpl implements ActOperation,Serializable {

	@NotNull @Column(name = COLUMN_ACT_IDENTIFIER,nullable = false) String actIdentifier;
	@Transient ActImpl act;
	@NotNull @Column(name = COLUMN_OPERATION_TYPE,nullable = false) @Enumerated(EnumType.STRING) ActOperationType operationType;
	@NotNull @Column(name = COLUMN_OPERATION_DATE,nullable = false) LocalDateTime operationDate;
	@NotNull @Column(name = COLUMN_TRIGGER,nullable = false) String trigger;	
	@Transient String operationDateString;
	
	@Override
	public ActOperationImpl setIdentifier(String identifier) {
		return (ActOperationImpl) super.setIdentifier(identifier);
	}
	
	@Override
	public ActOperation setAct(Act act) {
		this.act = (ActImpl) act;
		return this;
	}
	
	public static final String FIELD_ACT_IDENTIFIER = "actIdentifier";
	public static final String FIELD_OPERATION_TYPE = "operationType";
	public static final String FIELD_OPERATION_DATE = "operationDate";
	public static final String FIELD_OPERATION_DATE_STRING = "operationDateString";
	public static final String FIELD_TRIGGER = "trigger";
	
	public static final String ENTITY_NAME = "ActOperationImpl";
	public static final String TABLE_NAME = "TA_ACTE_OPERATION";
	
	public static final String COLUMN_ACT_IDENTIFIER = "acte";
	public static final String COLUMN_OPERATION_TYPE = "operation";
	public static final String COLUMN_OPERATION_DATE = "operation_date";
	public static final String COLUMN_TRIGGER = "declencheur";
}
package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.cyk.utility.persistence.entity.AbstractIdentifiableSystemScalarStringAuditedImpl;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditOverrides;
import org.hibernate.envers.AuditTable;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Act;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Operation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationAct;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Parameters;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) 
@Entity(name = OperationActImpl.ENTITY_NAME) @Access(AccessType.FIELD)
@Table(name=OperationActImpl.TABLE_NAME)
@AttributeOverrides(value= {
		@AttributeOverride(name = OperationActImpl.FIELD___AUDIT_IDENTIFIER__,column = @Column(name=OperationActImpl.COLUMN___AUDIT_IDENTIFIER__,nullable = false))
		,@AttributeOverride(name = OperationActImpl.FIELD___AUDIT_WHO__,column = @Column(name=OperationActImpl.COLUMN___AUDIT_WHO__,nullable = false))
		,@AttributeOverride(name = OperationActImpl.FIELD___AUDIT_WHAT__,column = @Column(name=OperationActImpl.COLUMN___AUDIT_WHAT__,nullable = false))
		,@AttributeOverride(name = OperationActImpl.FIELD___AUDIT_WHEN__,column = @Column(name=OperationActImpl.COLUMN___AUDIT_WHEN__,nullable = false))
		,@AttributeOverride(name = OperationActImpl.FIELD___AUDIT_FUNCTIONALITY__,column = @Column(name=OperationActImpl.COLUMN___AUDIT_FUNCTIONALITY__,nullable = false))
})
@AuditOverrides({
	@AuditOverride(forClass = AbstractIdentifiableSystemScalarStringAuditedImpl.class)
})
@AuditTable(value = OperationActImpl.AUDIT_TABLE_NAME)
@NamedQueries(value = {
		@NamedQuery(name = OperationActImpl.QUERY_READ_BY_OPERATION_IDENTIFIER_BY_NOT_ACTS_IDENTIFIERS,query = 
				"SELECT t FROM "+OperationActImpl.ENTITY_NAME+" t WHERE t.operation.identifier = :"+Parameters.OPERATION_IDENTIFIER+" AND t.act.identifier NOT IN :"+Parameters.ACTS_IDENTIFIERS)
		,@NamedQuery(name = OperationActImpl.QUERY_READ_BY_OPERATION_IDENTIFIER,query = 
				"SELECT t FROM "+OperationActImpl.ENTITY_NAME+" t WHERE t.operation.identifier = :"+Parameters.OPERATION_IDENTIFIER)
})
public class OperationActImpl extends AbstractIdentifiableSystemScalarStringAuditedImpl implements OperationAct,Serializable {

	@NotNull @ManyToOne @JoinColumn(name = FIELD_OPERATION,nullable = false) OperationImpl operation;
	@NotNull @ManyToOne @JoinColumn(name = COLUMN_ACT,nullable = false) ActImpl act;
	@Column(name = COLUMN_PROCESSED) Boolean processed;
	
	@Override
	public OperationActImpl setIdentifier(String identifier) {
		return (OperationActImpl) super.setIdentifier(identifier);
	}
	
	@Override
	public OperationAct setOperation(Operation operation) {
		this.operation = (OperationImpl) operation;
		return this;
	}
	
	@Override
	public OperationAct setAct(Act act) {
		this.act = (ActImpl) act;
		return this;
	}
	
	public static final String FIELD_OPERATION = "operation";
	public static final String FIELD_ACT = "act";
	public static final String FIELD_PROCESSED = "processed";
	
	public static final String FIELD_ACT_IDENTIFIER = "actIdentifier";
	public static final String FIELD_OPERATION_TYPE = "operationType";
	public static final String FIELD_OPERATION_DATE = "operationDate";
	public static final String FIELD_OPERATION_DATE_STRING = "operationDateString";
	public static final String FIELD_TRIGGER = "trigger";
	
	public static final String ENTITY_NAME = "OperationActImpl";
	public static final String TABLE_NAME = "TA_OPERATION_ACTE";
	public static final String AUDIT_TABLE_NAME = "TA_OPERATION_ACTE_AUD";
	
	public static final String COLUMN_OPERATION = "operation";
	public static final String COLUMN_ACT = "acte";
	public static final String COLUMN_PROCESSED = "traite";
	public static final String COLUMN___AUDIT_IDENTIFIER__ = "AUDIT_IDENTIFIANT";
	public static final String COLUMN___AUDIT_WHO__ = "AUDIT_ACTEUR";
	public static final String COLUMN___AUDIT_WHAT__ = "AUDIT_ACTION";
	public static final String COLUMN___AUDIT_FUNCTIONALITY__ = "AUDIT_FONCTIONNALITE";
	public static final String COLUMN___AUDIT_WHEN__ = "AUDIT_DATE";
	
	public static final String QUERY_READ_BY_OPERATION_IDENTIFIER = "OperationActImpl.readByOperationIdentifier";
	public static final String QUERY_READ_BY_OPERATION_IDENTIFIER_BY_NOT_ACTS_IDENTIFIERS = "OperationActImpl.readByOperationIdentifierByNotActsIdentifiers";
}
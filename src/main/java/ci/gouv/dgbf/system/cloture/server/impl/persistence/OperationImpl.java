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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.cyk.utility.persistence.entity.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableAuditedImpl;
import org.cyk.utility.persistence.entity.audit.AuditedAction;
import org.cyk.utility.persistence.server.audit.AuditedActionImpl;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditOverrides;
import org.hibernate.envers.AuditTable;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Operation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) 
@Entity(name = OperationImpl.ENTITY_NAME) @Access(AccessType.FIELD)
@Table(name=OperationImpl.TABLE_NAME)
@AttributeOverrides(value= {
		@AttributeOverride(name = OperationImpl.FIELD___AUDIT_IDENTIFIER__,column = @Column(name=OperationImpl.COLUMN___AUDIT_IDENTIFIER__,nullable = false))
		,@AttributeOverride(name = OperationImpl.FIELD___AUDIT_WHO__,column = @Column(name=OperationImpl.COLUMN___AUDIT_WHO__,nullable = false))
		,@AttributeOverride(name = OperationImpl.FIELD___AUDIT_WHAT__,column = @Column(name=OperationImpl.COLUMN___AUDIT_WHAT__,nullable = false))
		,@AttributeOverride(name = OperationImpl.FIELD___AUDIT_WHEN__,column = @Column(name=OperationImpl.COLUMN___AUDIT_WHEN__,nullable = false))
		,@AttributeOverride(name = OperationImpl.FIELD___AUDIT_FUNCTIONALITY__,column = @Column(name=OperationImpl.COLUMN___AUDIT_FUNCTIONALITY__,nullable = false))
})
@AuditOverrides({
	@AuditOverride(forClass = AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableAuditedImpl.class)
})
@AuditTable(value = OperationImpl.AUDIT_TABLE_NAME)
public class OperationImpl extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableAuditedImpl implements Operation,Serializable {

 	@NotNull @ManyToOne @JoinColumn(name = COLUMN_TYPE,nullable = false) OperationTypeImpl type;
  	@Transient String typeAsString;
  	@NotNull @Column(name = COLUMN_REASON,nullable = false) String reason;
	
  	@Transient AuditedActionImpl creation;
  	@Transient AuditedActionImpl execution;
  	
	@Override
	public OperationImpl setIdentifier(String identifier) {
		return (OperationImpl) super.setIdentifier(identifier);
	}
	
	@Override
	public OperationImpl setCode(String code) {
		return (OperationImpl) super.setCode(code);
	}
	
	@Override
	public OperationImpl setName(String name) {
		return (OperationImpl) super.setName(name);
	}
	
	@Override
	public Operation setType(OperationType type) {
		this.type = (OperationTypeImpl) type;
		return this;
	}
	
	@Override
	public Operation setCreation(AuditedAction creation) {
		this.creation = (AuditedActionImpl) creation;
		return this;
	}
	
	@Override
	public Operation setExecution(AuditedAction execution) {
		this.execution = (AuditedActionImpl) execution;
		return this;
	}
	
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_TYPE_AS_STRING = "typeAsString";
	public static final String FIELD_REASON = "reason";
	public static final String FIELD_CREATION = "creation";
	public static final String FIELD_EXECUTION = "execution";
	
	public static final String ENTITY_NAME = "OperationImpl";
	public static final String TABLE_NAME = "TA_OPERATION";
	public static final String AUDIT_TABLE_NAME = "TA_OPERATION_AUD";
	
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_REASON = "motif";
	public static final String COLUMN___AUDIT_IDENTIFIER__ = "AUDIT_IDENTIFIANT";
	public static final String COLUMN___AUDIT_WHO__ = "AUDIT_ACTEUR";
	public static final String COLUMN___AUDIT_WHAT__ = "AUDIT_ACTION";
	public static final String COLUMN___AUDIT_FUNCTIONALITY__ = "AUDIT_FONCTIONNALITE";
	public static final String COLUMN___AUDIT_WHEN__ = "AUDIT_DATE";
}
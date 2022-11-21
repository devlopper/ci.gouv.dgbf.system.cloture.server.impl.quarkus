package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.cyk.utility.persistence.entity.AbstractIdentifiableSystemScalarStringAuditedImpl;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditOverrides;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Operation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationData;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) 
@Access(AccessType.FIELD)
@AttributeOverrides(value= {
		@AttributeOverride(name = AbstractOperationDataImpl.FIELD___AUDIT_IDENTIFIER__,column = @Column(name=AbstractOperationDataImpl.COLUMN___AUDIT_IDENTIFIER__,nullable = false))
		,@AttributeOverride(name = AbstractOperationDataImpl.FIELD___AUDIT_WHO__,column = @Column(name=AbstractOperationDataImpl.COLUMN___AUDIT_WHO__,nullable = false))
		,@AttributeOverride(name = AbstractOperationDataImpl.FIELD___AUDIT_WHAT__,column = @Column(name=AbstractOperationDataImpl.COLUMN___AUDIT_WHAT__,nullable = false))
		,@AttributeOverride(name = AbstractOperationDataImpl.FIELD___AUDIT_WHEN__,column = @Column(name=AbstractOperationDataImpl.COLUMN___AUDIT_WHEN__,nullable = false))
		,@AttributeOverride(name = AbstractOperationDataImpl.FIELD___AUDIT_FUNCTIONALITY__,column = @Column(name=AbstractOperationDataImpl.COLUMN___AUDIT_FUNCTIONALITY__,nullable = false))
})
@AuditOverrides({
	@AuditOverride(forClass = AbstractIdentifiableSystemScalarStringAuditedImpl.class)
})
@MappedSuperclass
public abstract class AbstractOperationDataImpl extends AbstractIdentifiableSystemScalarStringAuditedImpl implements OperationData,Serializable {

	@NotNull @ManyToOne @JoinColumn(name = FIELD_OPERATION,nullable = false) @NotAudited OperationImpl operation;
	@Column(name = COLUMN_PROCESSED) @Audited Boolean processed;
	
	@Override
	public AbstractOperationDataImpl setIdentifier(String identifier) {
		return (AbstractOperationDataImpl) super.setIdentifier(identifier);
	}
	
	@Override
	public OperationData setOperation(Operation operation) {
		this.operation = (@NotNull OperationImpl) operation;
		return this;
	}
	
	public static final String FIELD_OPERATION = "operation";
	public static final String FIELD_PROCESSED = "processed";
	
	public static final String COLUMN_OPERATION = "operation";
	public static final String COLUMN_PROCESSED = "traite";
	public static final String COLUMN___AUDIT_IDENTIFIER__ = "AUDIT_IDENTIFIANT";
	public static final String COLUMN___AUDIT_WHO__ = "AUDIT_ACTEUR";
	public static final String COLUMN___AUDIT_WHAT__ = "AUDIT_ACTION";
	public static final String COLUMN___AUDIT_FUNCTIONALITY__ = "AUDIT_FONCTIONNALITE";
	public static final String COLUMN___AUDIT_WHEN__ = "AUDIT_DATE";
}
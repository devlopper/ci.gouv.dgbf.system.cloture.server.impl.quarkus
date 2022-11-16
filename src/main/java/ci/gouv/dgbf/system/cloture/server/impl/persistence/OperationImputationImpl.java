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

import ci.gouv.dgbf.system.cloture.server.api.persistence.Imputation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Operation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationImputation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Parameters;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) 
@Entity(name = OperationImputationImpl.ENTITY_NAME) @Access(AccessType.FIELD)
@Table(name=OperationImputationImpl.TABLE_NAME)
@AttributeOverrides(value= {
		@AttributeOverride(name = OperationImputationImpl.FIELD___AUDIT_IDENTIFIER__,column = @Column(name=OperationImputationImpl.COLUMN___AUDIT_IDENTIFIER__,nullable = false))
		,@AttributeOverride(name = OperationImputationImpl.FIELD___AUDIT_WHO__,column = @Column(name=OperationImputationImpl.COLUMN___AUDIT_WHO__,nullable = false))
		,@AttributeOverride(name = OperationImputationImpl.FIELD___AUDIT_WHAT__,column = @Column(name=OperationImputationImpl.COLUMN___AUDIT_WHAT__,nullable = false))
		,@AttributeOverride(name = OperationImputationImpl.FIELD___AUDIT_WHEN__,column = @Column(name=OperationImputationImpl.COLUMN___AUDIT_WHEN__,nullable = false))
		,@AttributeOverride(name = OperationImputationImpl.FIELD___AUDIT_FUNCTIONALITY__,column = @Column(name=OperationImputationImpl.COLUMN___AUDIT_FUNCTIONALITY__,nullable = false))
})
@AuditOverrides({
	@AuditOverride(forClass = AbstractIdentifiableSystemScalarStringAuditedImpl.class)
})
@AuditTable(value = OperationImputationImpl.AUDIT_TABLE_NAME)
@NamedQueries(value = {
		@NamedQuery(name = OperationImputationImpl.QUERY_READ_BY_OPERATION_IDENTIFIER_BY_IMPUTATIONS_IDENTIFIERS,query = 
				"SELECT t FROM "+OperationImputationImpl.ENTITY_NAME+" t WHERE t.operation.identifier = :"+Parameters.OPERATION_IDENTIFIER+" AND t.imputation.identifier IN :"+Parameters.IMPUTATIONS_IDENTIFIERS)
		,@NamedQuery(name = OperationImputationImpl.QUERY_READ_BY_OPERATION_IDENTIFIER_BY_NOT_IMPUTATIONS_IDENTIFIERS,query = 
				"SELECT t FROM "+OperationImputationImpl.ENTITY_NAME+" t WHERE t.operation.identifier = :"+Parameters.OPERATION_IDENTIFIER+" AND t.imputation.identifier NOT IN :"+Parameters.IMPUTATIONS_IDENTIFIERS)
		,@NamedQuery(name = OperationImputationImpl.QUERY_READ_BY_OPERATION_IDENTIFIER,query = 
				"SELECT t FROM "+OperationImputationImpl.ENTITY_NAME+" t WHERE t.operation.identifier = :"+Parameters.OPERATION_IDENTIFIER)
})
public class OperationImputationImpl extends AbstractIdentifiableSystemScalarStringAuditedImpl implements OperationImputation,Serializable {

	@NotNull @ManyToOne @JoinColumn(name = FIELD_OPERATION,nullable = false) OperationImpl operation;
	@NotNull @ManyToOne @JoinColumn(name = COLUMN_IMPUTATION,nullable = false) ImputationImpl imputation;
	@Column(name = COLUMN_PROCESSED) Boolean processed;
	
	@Override
	public OperationImputationImpl setIdentifier(String identifier) {
		return (OperationImputationImpl) super.setIdentifier(identifier);
	}
	
	@Override
	public OperationImputation setOperation(Operation operation) {
		this.operation = (OperationImpl) operation;
		return this;
	}
	
	@Override
	public OperationImputation setImputation(Imputation imputation) {
		this.imputation = (ImputationImpl) imputation;
		return this;
	}
	
	public static final String FIELD_OPERATION = "operation";
	public static final String FIELD_IMPUTATION = "imputation";
	public static final String FIELD_PROCESSED = "processed";
	
	public static final String FIELD_IMPUTATION_IDENTIFIER = "imputationIdentifier";
	public static final String FIELD_OPERATION_TYPE = "operationType";
	public static final String FIELD_OPERATION_DATE = "operationDate";
	public static final String FIELD_OPERATION_DATE_STRING = "operationDateString";
	public static final String FIELD_TRIGGER = "trigger";
	
	public static final String ENTITY_NAME = "OperationImputationImpl";
	public static final String TABLE_NAME = "TA_OPERATION_IMPUTATION";
	public static final String AUDIT_TABLE_NAME = "TA_OPERATION_IMPUTATION_AUD";
	
	public static final String COLUMN_OPERATION = "operation";
	public static final String COLUMN_IMPUTATION = "imputation";
	public static final String COLUMN_PROCESSED = "traite";
	public static final String COLUMN___AUDIT_IDENTIFIER__ = "AUDIT_IDENTIFIANT";
	public static final String COLUMN___AUDIT_WHO__ = "AUDIT_ACTEUR";
	public static final String COLUMN___AUDIT_WHAT__ = "AUDIT_ACTION";
	public static final String COLUMN___AUDIT_FUNCTIONALITY__ = "AUDIT_FONCTIONNALITE";
	public static final String COLUMN___AUDIT_WHEN__ = "AUDIT_DATE";
	
	public static final String QUERY_READ_BY_OPERATION_IDENTIFIER = "OperationImputationImpl.readByOperationIdentifier";
	public static final String QUERY_READ_BY_OPERATION_IDENTIFIER_BY_IMPUTATIONS_IDENTIFIERS = "OperationImputationImpl.readByOperationIdentifierByImputationsIdentifiers";
	public static final String QUERY_READ_BY_OPERATION_IDENTIFIER_BY_NOT_IMPUTATIONS_IDENTIFIERS = "OperationImputationImpl.readByOperationIdentifierByNotImputationsIdentifiers";
}
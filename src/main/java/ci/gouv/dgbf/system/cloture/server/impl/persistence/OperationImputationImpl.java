package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditOverrides;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Imputation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationImputation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Parameters;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) 
@Entity(name = OperationImputationImpl.ENTITY_NAME) @Access(AccessType.FIELD)
@Table(name=OperationImputationImpl.TABLE_NAME)
@AuditOverrides({
	@AuditOverride(forClass = AbstractOperationDataImpl.class)
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
public class OperationImputationImpl extends AbstractOperationDataImpl implements OperationImputation,Serializable {

	@NotNull @ManyToOne @JoinColumn(name = COLUMN_IMPUTATION,nullable = false) @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED) ImputationImpl imputation;
	
	@Override
	public OperationImputationImpl setIdentifier(String identifier) {
		return (OperationImputationImpl) super.setIdentifier(identifier);
	}
	
	@Override
	public OperationImputation setImputation(Imputation imputation) {
		this.imputation = (ImputationImpl) imputation;
		return this;
	}
	
	public static final String FIELD_IMPUTATION = "imputation";
	
	public static final String ENTITY_NAME = "OperationImputationImpl";
	public static final String TABLE_NAME = "TA_OPERATION_IMPUTATION";
	public static final String AUDIT_TABLE_NAME = "TA_OPERATION_IMPUTATION_AUD";
	
	public static final String COLUMN_IMPUTATION = "imputation";
	
	public static final String QUERY_READ_BY_OPERATION_IDENTIFIER = "OperationImputationImpl.readByOperationIdentifier";
	public static final String QUERY_READ_BY_OPERATION_IDENTIFIER_BY_IMPUTATIONS_IDENTIFIERS = "OperationImputationImpl.readByOperationIdentifierByImputationsIdentifiers";
	public static final String QUERY_READ_BY_OPERATION_IDENTIFIER_BY_NOT_IMPUTATIONS_IDENTIFIERS = "OperationImputationImpl.readByOperationIdentifierByNotImputationsIdentifiers";
}
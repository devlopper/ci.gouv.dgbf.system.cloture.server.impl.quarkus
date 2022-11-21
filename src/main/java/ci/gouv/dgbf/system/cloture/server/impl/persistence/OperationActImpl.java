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

import ci.gouv.dgbf.system.cloture.server.api.persistence.Act;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationAct;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Parameters;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) 
@Entity(name = OperationActImpl.ENTITY_NAME) @Access(AccessType.FIELD)
@Table(name=OperationActImpl.TABLE_NAME)
@AuditOverrides({
	@AuditOverride(forClass = AbstractOperationDataImpl.class)
})
@AuditTable(value = OperationActImpl.AUDIT_TABLE_NAME)
@NamedQueries(value = {
		@NamedQuery(name = OperationActImpl.QUERY_READ_BY_OPERATION_IDENTIFIER_BY_ACTS_IDENTIFIERS,query = 
				"SELECT t FROM "+OperationActImpl.ENTITY_NAME+" t WHERE t.operation.identifier = :"+Parameters.OPERATION_IDENTIFIER+" AND t.act.identifier IN :"+Parameters.ACTS_IDENTIFIERS)
		,@NamedQuery(name = OperationActImpl.QUERY_READ_BY_OPERATION_IDENTIFIER_BY_NOT_ACTS_IDENTIFIERS,query = 
				"SELECT t FROM "+OperationActImpl.ENTITY_NAME+" t WHERE t.operation.identifier = :"+Parameters.OPERATION_IDENTIFIER+" AND t.act.identifier NOT IN :"+Parameters.ACTS_IDENTIFIERS)
		,@NamedQuery(name = OperationActImpl.QUERY_READ_BY_OPERATION_IDENTIFIER,query = 
				"SELECT t FROM "+OperationActImpl.ENTITY_NAME+" t WHERE t.operation.identifier = :"+Parameters.OPERATION_IDENTIFIER)
})
public class OperationActImpl extends AbstractOperationDataImpl implements OperationAct,Serializable {

	@NotNull @ManyToOne @JoinColumn(name = COLUMN_ACT,nullable = false) @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED) ActImpl act;
	
	@Override
	public OperationActImpl setIdentifier(String identifier) {
		return (OperationActImpl) super.setIdentifier(identifier);
	}
	
	@Override
	public OperationAct setAct(Act act) {
		this.act = (ActImpl) act;
		return this;
	}
	
	public static final String FIELD_ACT = "act";
	
	public static final String ENTITY_NAME = "OperationActImpl";
	public static final String TABLE_NAME = "TA_OPERATION_ACTE";
	public static final String AUDIT_TABLE_NAME = "TA_OPERATION_ACTE_AUD";
	
	public static final String COLUMN_ACT = "acte";
	
	public static final String QUERY_READ_BY_OPERATION_IDENTIFIER = "OperationActImpl.readByOperationIdentifier";
	public static final String QUERY_READ_BY_OPERATION_IDENTIFIER_BY_ACTS_IDENTIFIERS = "OperationActImpl.readByOperationIdentifierByActsIdentifiers";
	public static final String QUERY_READ_BY_OPERATION_IDENTIFIER_BY_NOT_ACTS_IDENTIFIERS = "OperationActImpl.readByOperationIdentifierByNotActsIdentifiers";
}
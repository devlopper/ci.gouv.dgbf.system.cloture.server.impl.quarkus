package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.cyk.utility.persistence.entity.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Operation;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) 
@Entity(name = OperationImpl.ENTITY_NAME) @Access(AccessType.FIELD)
@Table(name=OperationImpl.TABLE_NAME)
@Cacheable
@NamedStoredProcedureQueries(value = {
	@NamedStoredProcedureQuery(
		name = OperationImpl.STORED_PROCEDURE_QUERY_PROCEDURE_NAME_EXECUTE_PROCEDURE
		,procedureName = OperationImpl.STORED_PROCEDURE_QUERY_PROCEDURE_NAME_EXECUTE_PROCEDURE
		,parameters = {
				@StoredProcedureParameter(name = OperationImpl.STORED_PROCEDURE_QUERY_PARAMETER_NAME_PROCEDURE_NAME , mode = ParameterMode.IN,type = String.class)
			}
	)
})
public class OperationImpl extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Operation,Serializable {

	@JoinColumn(name = COLUMN_GROUP) @ManyToOne OperationGroupImpl group;
	@Transient String groupIdentifier;
	@Column(name = COLUMN_END_DATE) LocalDateTime endDate;	
	@Column(name = COLUMN_TRIGGER) String trigger;
	@Column(name = COLUMN_EXECUTION_BEGIN_DATE) LocalDateTime executionBeginDate;
	@Column(name = COLUMN_EXECUTION_END_DATE) LocalDateTime executionEndDate;
	
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
	public Operation setGroup(Operation group) {
		this.group = (OperationGroupImpl) group;
		return this;
	}
	
	public static final String ENTITY_NAME = "OperationImpl";
	public static final String TABLE_NAME = "TA_OPERATION";
	
	public static final String COLUMN_GROUP = "GROUPE";
	public static final String COLUMN_END_DATE = "DATE_FIN";
	public static final String COLUMN_TRIGGER = "EXECUTEE_PAR";
	public static final String COLUMN_EXECUTION_BEGIN_DATE = "EXECUTION_DATE_DEBUT";
	public static final String COLUMN_EXECUTION_END_DATE = "EXECUTION_DATE_FIN";
	
	public static final String STORED_PROCEDURE_QUERY_PROCEDURE_NAME_EXECUTE_PROCEDURE = "PA_EXECUTER_PROCEDURE";
	public static final String STORED_PROCEDURE_QUERY_PARAMETER_NAME_PROCEDURE_NAME = "NOM_PROCEDURE";
}
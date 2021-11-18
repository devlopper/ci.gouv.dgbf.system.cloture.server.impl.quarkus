package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.cyk.utility.persistence.entity.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Operation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationExecutionStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) 
@Entity(name = OperationImpl.ENTITY_NAME) @Access(AccessType.FIELD)
@Table(name=OperationImpl.TABLE_NAME)
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

	@NotNull @JoinColumn(name = COLUMN_GROUP,nullable = false) @ManyToOne OperationGroupImpl group;
	@Transient String groupIdentifier;
	@NotNull @Column(name = COLUMN_START_DATE,nullable = false) LocalDateTime startDate;
	@Transient String startDateString;
	@Transient Long startDateNumberOfMillisecond;
	@NotNull @Column(name = COLUMN_PROCEDURE_NAME,nullable = false) String procedureName;
	/* Execution */
	@Column(name = COLUMN_TRIGGER) String trigger;
	@Column(name = COLUMN_EXECUTION_BEGIN_DATE) LocalDateTime executionBeginDate;
	@Transient String executionBeginDateString;
	@Transient Long executionBeginDateNumberOfMillisecond;
	@Column(name = COLUMN_EXECUTION_END_DATE) LocalDateTime executionEndDate;
	@Transient String executionEndDateString;
	@Transient Long executionEndDateNumberOfMillisecond;
	@Column(name = COLUMN_EXECUTION_STATUS) @Enumerated(EnumType.STRING) OperationExecutionStatus executionStatus;
	
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
	
	public static final String FIELD_GROUP = "group";
	public static final String FIELD_GROUP_IDENTIFIER = "groupIdentifier";
	public static final String FIELD_START_DATE = "startDate";
	public static final String FIELD_START_DATE_NUMBER_OF_MILLISECOND = "startDateNumberOfMillisecond";
	public static final String FIELD_START_DATE_STRING = "startDateString";
	public static final String FIELD_PROCEDURE_NAME = "procedureName";
	public static final String FIELD_TRIGGER = "trigger";
	public static final String FIELD_EXECUTION_BEGIN_DATE = "executionBeginDate";
	public static final String FIELD_EXECUTION_BEGIN_DATE_NUMBER_OF_MILLISECOND = "executionBeginDateNumberOfMillisecond";
	public static final String FIELD_EXECUTION_BEGIN_DATE_STRING = "executionBeginDateString";
	public static final String FIELD_EXECUTION_END_DATE = "executionEndDate";
	public static final String FIELD_EXECUTION_END_DATE_NUMBER_OF_MILLISECOND = "executionEndDateNumberOfMillisecond";
	public static final String FIELD_EXECUTION_END_DATE_STRING = "executionEndDateString";
	public static final String FIELD_EXECUTION_STATUS = "executionStatus";
	
	public static final String ENTITY_NAME = "OperationImpl";
	public static final String TABLE_NAME = "TA_OPERATION";
	
	public static final String COLUMN_GROUP = "GROUPE";
	public static final String COLUMN_START_DATE = "DATE_DEBUT";
	public static final String COLUMN_PROCEDURE_NAME = "PROCEDURE_LIBELLE";
	public static final String COLUMN_TRIGGER = "EXECUTEE_PAR";
	public static final String COLUMN_EXECUTION_BEGIN_DATE = "EXECUTION_DATE_DEBUT";
	public static final String COLUMN_EXECUTION_END_DATE = "EXECUTION_DATE_FIN";
	public static final String COLUMN_EXECUTION_STATUS = "EXECUTION_STATUS";
	
	public static final String STORED_PROCEDURE_QUERY_PROCEDURE_NAME_EXECUTE_PROCEDURE = "PA_EXECUTER_PROCEDURE";
	public static final String STORED_PROCEDURE_QUERY_PARAMETER_NAME_PROCEDURE_NAME = "NOM_PROCEDURE";
	public static final String STORED_PROCEDURE_QUERY_PARAMETER_NAME_RESULT_IDENTIFIER = "RESULTAT_IDENTIFIANT";
}
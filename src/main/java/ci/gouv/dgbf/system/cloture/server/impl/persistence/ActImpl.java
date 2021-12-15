package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.cyk.utility.persistence.entity.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Act;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActOperationType;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) 
@Entity(name = ActImpl.ENTITY_NAME) @Access(AccessType.FIELD)
@Table(name=ActImpl.TABLE_NAME)
@NamedStoredProcedureQueries(value = {
		@NamedStoredProcedureQuery(
			name = ActImpl.STORED_PROCEDURE_QUERY_PROCEDURE_NAME_LOCK
			,procedureName = ActImpl.STORED_PROCEDURE_QUERY_PROCEDURE_NAME_LOCK
			,parameters = {
				@StoredProcedureParameter(name = ActImpl.STORED_PROCEDURE_QUERY_PARAMETER_NAME_IDENTIFIER , mode = ParameterMode.IN,type = String.class)
				,@StoredProcedureParameter(name = ActImpl.STORED_PROCEDURE_QUERY_PARAMETER_NAME_LOCK_TYPE , mode = ParameterMode.IN,type = String.class)
				,@StoredProcedureParameter(name = ActImpl.STORED_PROCEDURE_QUERY_PARAMETER_NAME_TARGET_TABLE , mode = ParameterMode.IN,type = String.class)
			}
		)
		,@NamedStoredProcedureQuery(
				name = ActImpl.STORED_PROCEDURE_QUERY_PROCEDURE_NAME_UNLOCK
				,procedureName = ActImpl.STORED_PROCEDURE_QUERY_PROCEDURE_NAME_UNLOCK
				,parameters = {
					@StoredProcedureParameter(name = ActImpl.STORED_PROCEDURE_QUERY_PARAMETER_NAME_IDENTIFIER , mode = ParameterMode.IN,type = String.class)
					,@StoredProcedureParameter(name = ActImpl.STORED_PROCEDURE_QUERY_PARAMETER_NAME_LOCK_TYPE , mode = ParameterMode.IN,type = String.class)
					,@StoredProcedureParameter(name = ActImpl.STORED_PROCEDURE_QUERY_PARAMETER_NAME_TARGET_TABLE , mode = ParameterMode.IN,type = String.class)
				}
			)
	})
public class ActImpl extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Act,Serializable {

	@Column(name = COLUMN_REFERENCE) String reference;
	@Column(name = COLUMN_TYPE) @Enumerated(EnumType.STRING) ActType type;
	
	@Transient Boolean locked;
	@Transient Integer numberOfLocks;
	@Transient Integer numberOfLocksEnabled;
	@Transient ActOperationType operationType;
	@Transient String operationDateString;
	@Transient String trigger;
	@Transient String statusString;
	@Transient String latestOperationString;
	
	@Override
	public ActImpl setIdentifier(String identifier) {
		return (ActImpl) super.setIdentifier(identifier);
	}
	
	@Override
	public ActImpl setCode(String code) {
		return (ActImpl) super.setCode(code);
	}
	
	@Override
	public ActImpl setName(String name) {
		return (ActImpl) super.setName(name);
	}
	
	public static final String FIELD_OPERATION_TYPE = "operationType";
	public static final String FIELD_OPERATION_DATE = "operationDate";
	public static final String FIELD_OPERATION_DATE_STRING = "operationDateString";
	public static final String FIELD_TRIGGER = "trigger";
	public static final String FIELD_REFERENCE = "reference";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_NUMBER_OF_LOCKS = "numberOfLocks";
	public static final String FIELD_NUMBER_OF_LOCKS_ENABLED = "numberOfLocksEnabled";
	public static final String FIELD_STATUS_STRING = "statusString";
	public static final String FIELD_LATEST_OPERATION_STRING = "latestOperationString";
	public static final String FIELDS_NUMBER_OF_LOCKS = "numberOfLocks";
	
	public static final String ENTITY_NAME = "ActImpl";
	public static final String TABLE_NAME = "VMA_ACTE";
	
	public static final String COLUMN_REFERENCE = "reference";
	public static final String COLUMN_TYPE = "type";
	/*
	public static final String COLUMN_OPERATION_TYPE = "operation";
	public static final String COLUMN_TRIGGER = "declencheur";
	public static final String COLUMN_OPERATION_DATE = "operation_date";
	*/
	public static final String STORED_PROCEDURE_QUERY_PROCEDURE_NAME_LOCK = "PA_VERROUILLER_ACTE";
	public static final String STORED_PROCEDURE_QUERY_PROCEDURE_NAME_UNLOCK = "PA_DEVERROUILLER_ACTE";
	public static final String STORED_PROCEDURE_QUERY_PARAMETER_NAME_IDENTIFIER = "IDENTIFIANT";
	public static final String STORED_PROCEDURE_QUERY_PARAMETER_NAME_TARGET_TABLE = "TABLE_CIBLE";
	public static final String STORED_PROCEDURE_QUERY_PARAMETER_NAME_LOCK_TYPE = "TYPE_VERROU";
	public static final String STORED_PROCEDURE_QUERY_PARAMETER_NAME_TRIGGER = "DECLENCHEUR";
	public static final String STORED_PROCEDURE_QUERY_PARAMETER_NAME_DATE = "DATE";
}
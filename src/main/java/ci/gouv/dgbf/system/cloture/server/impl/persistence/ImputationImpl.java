package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.cyk.utility.persistence.entity.AbstractIdentifiableSystemScalarStringImpl;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Imputation;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) 
@Entity(name = ImputationImpl.ENTITY_NAME) @Access(AccessType.FIELD)
@Table(name=ImputationImpl.TABLE_NAME)
@Cacheable
@org.hibernate.annotations.Immutable
public class ImputationImpl extends AbstractIdentifiableSystemScalarStringImpl implements Imputation,Serializable {

	@Column(name = COLUMN_EXERCISE) String exerciseIdentifier;
	@Column(name = COLUMN_EXERCISE_YEAR) Short exerciseYear;
	@Transient String exerciseAsString;
	@Column(name = COLUMN_ACTIVITY_IDENTIFIER) String activityIdentifier;
	@Column(name = COLUMN_ACTIVITY_CODE) String activityCode;
	@Transient String activityAsString;
	@Column(name = COLUMN_ECONOMIC_NATURE_IDENTIFIER) String economicNatureIdentifier;
	@Column(name = COLUMN_ECONOMIC_NATURE_CODE) String economicNatureCode;
	@Transient String economicNatureAsString;
	/*@Column(name = COLUMN_REFERENCE)*/ @Transient String reference;
	
	@Override
	public ImputationImpl setIdentifier(String identifier) {
		return (ImputationImpl) super.setIdentifier(identifier);
	}
	
	public static final String FIELD_EXERCISE_IDENTIFIER = "exerciseIdentifier";
	public static final String FIELD_EXERCISE_YEAR = "exerciseYear";
	public static final String FIELD_ACTIVITY_IDENTIFIER = "activityIdentifier";
	public static final String FIELD_ACTIVITY_CODE = "activityCode";
	public static final String FIELD_ECONOMIC_NATURE_IDENTIFIER = "economicNatureIdentifier";
	public static final String FIELD_ECONOMIC_NATURE_CODE = "economicNatureCode";
	public static final String FIELD_REFERENCE = "reference";
	public static final String FIELDS_AS_STRING = "asString";
	
	public static final String ENTITY_NAME = "ImputationImpl";
	public static final String TABLE_NAME = "VMA_IMPUTATION";
	
	public static final String COLUMN_EXERCISE = "EXERCICE";
	public static final String COLUMN_EXERCISE_YEAR = "EXERCICE_ANNEE";
	public static final String COLUMN_ACTIVITY_IDENTIFIER = "ACTIVITE";
	public static final String COLUMN_ACTIVITY_CODE = "ACTIVITE_CODE";
	public static final String COLUMN_ECONOMIC_NATURE_IDENTIFIER = "NATURE_ECONOMIQUE";
	public static final String COLUMN_ECONOMIC_NATURE_CODE = "NATURE_ECONOMIQUE_CODE";
	public static final String COLUMN_REFERENCE = "REFERENCE";
}
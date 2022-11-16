package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.cyk.utility.persistence.entity.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Exercise;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) 
@Entity(name = ExerciseImpl.ENTITY_NAME) @Access(AccessType.FIELD)
@Table(name=ExerciseImpl.TABLE_NAME)
public class ExerciseImpl extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Exercise,Serializable {

	@Column(name = COUMN_YEAR) Short year;
	@Transient String yearAsString;
	
	@Override
	public ExerciseImpl setIdentifier(String identifier) {
		return (ExerciseImpl) super.setIdentifier(identifier);
	}
	
	@Override
	public ExerciseImpl setCode(String code) {
		return (ExerciseImpl) super.setCode(code);
	}
	
	@Override
	public ExerciseImpl setName(String name) {
		return (ExerciseImpl) super.setName(name);
	}
	
	public static final String FIELD_YEAR = "year";
	public static final String FIELD_YEAR_AS_STRING = "yearAsString";
	
	public static final String ENTITY_NAME = "ExerciseImpl";
	public static final String TABLE_NAME = "VMA_EXERCICE";
	
	public static final String COUMN_YEAR = "ANNEE";
}
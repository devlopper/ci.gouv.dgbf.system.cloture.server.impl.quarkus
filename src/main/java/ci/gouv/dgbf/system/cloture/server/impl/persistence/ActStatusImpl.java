package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Transient;

import org.cyk.utility.persistence.entity.AbstractIdentifiableSystemScalarStringImpl;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) 
//@Entity(name = ActStatusImpl.ENTITY_NAME) @Access(AccessType.FIELD)
//@Table(name=ActStatusImpl.TABLE_NAME)
public class ActStatusImpl extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {

	@Column(name = COLUMN_NUMBER_OF_LOCKS) Integer numberOfLocks;
	@Column(name = COLUMN_NUMBER_OF_LOCKS_ENABLED) Integer numberOfLocksEnabled;
	@Transient Boolean locked;

	@Override
	public ActStatusImpl setIdentifier(String identifier) {
		return (ActStatusImpl) super.setIdentifier(identifier);
	}
	
	public static final String FIELD_NUMBER_OF_LOCKS = "numberOfLocks";
	public static final String FIELD_NUMBER_OF_LOCKS_ENABLED = "numberOfLocksEnabled";
	public static final String FIELD_LOCKED = "locked";
	
	public static final String ENTITY_NAME = "ActStatusImpl";
	public static final String TABLE_NAME = "VA_ACTE_STATUT";
	
	public static final String COLUMN_NUMBER_OF_LOCKS = "nombre_verrou";
	public static final String COLUMN_NUMBER_OF_LOCKS_ENABLED = "nombre_verrou_actif";
}
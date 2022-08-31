package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.cyk.utility.persistence.entity.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) 
@Entity(name = OperationStatusImpl.ENTITY_NAME) @Access(AccessType.FIELD)
@Table(name=OperationStatusImpl.TABLE_NAME)
public class OperationStatusImpl extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements OperationStatus,Serializable {

	@NotNull @Column(name = COLUMN_ORDER_NUMBER,nullable = false,unique = true) Byte orderNumber;
	
	@Override
	public OperationStatusImpl setIdentifier(String identifier) {
		return (OperationStatusImpl) super.setIdentifier(identifier);
	}
	
	@Override
	public OperationStatusImpl setCode(String code) {
		return (OperationStatusImpl) super.setCode(code);
	}
	
	@Override
	public OperationStatusImpl setName(String name) {
		return (OperationStatusImpl) super.setName(name);
	}
	
	public static final String FIELD_ORDER_NUMBER = "orderNumber";
	
	public static final String ENTITY_NAME = "OperationStatusImpl";
	public static final String TABLE_NAME = "TA_STATUT_OPERATION";
	
	public static final String COLUMN_ORDER_NUMBER = "NUMERO_ORDRE";
}
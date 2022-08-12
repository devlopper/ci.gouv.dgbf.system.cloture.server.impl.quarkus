package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.cyk.utility.persistence.entity.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) 
@Entity(name = OperationTypeImpl.ENTITY_NAME) @Access(AccessType.FIELD)
@Table(name=OperationTypeImpl.TABLE_NAME)
public class OperationTypeImpl extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements OperationType,Serializable {

	@Override
	public OperationTypeImpl setIdentifier(String identifier) {
		return (OperationTypeImpl) super.setIdentifier(identifier);
	}
	
	@Override
	public OperationTypeImpl setCode(String code) {
		return (OperationTypeImpl) super.setCode(code);
	}
	
	@Override
	public OperationTypeImpl setName(String name) {
		return (OperationTypeImpl) super.setName(name);
	}
	
	public static final String ENTITY_NAME = "OperationTypeImpl";
	public static final String TABLE_NAME = "TA_TYPE_OPERATION";
}
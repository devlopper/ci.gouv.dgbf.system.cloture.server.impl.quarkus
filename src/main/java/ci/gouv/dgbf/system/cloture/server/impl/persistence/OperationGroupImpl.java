package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.cyk.utility.persistence.entity.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationGroup;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) 
@Entity(name = OperationGroupImpl.ENTITY_NAME) @Access(AccessType.FIELD)
@Table(name=OperationGroupImpl.TABLE_NAME)
public class OperationGroupImpl extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements OperationGroup,Serializable {

	@Override
	public OperationGroupImpl setIdentifier(String identifier) {
		return (OperationGroupImpl) super.setIdentifier(identifier);
	}
	
	@Override
	public OperationGroupImpl setCode(String code) {
		return (OperationGroupImpl) super.setCode(code);
	}
	
	@Override
	public OperationGroupImpl setName(String name) {
		return (OperationGroupImpl) super.setName(name);
	}
	
	public static final String ENTITY_NAME = "OperationGroupImpl";
	public static final String TABLE_NAME = "TA_GROUPE_OPERATION";
}
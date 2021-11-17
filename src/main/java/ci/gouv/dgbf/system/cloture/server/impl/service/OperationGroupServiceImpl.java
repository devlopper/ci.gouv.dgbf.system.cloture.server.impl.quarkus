package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;

import javax.inject.Inject;
import javax.ws.rs.Path;

import org.cyk.utility.service.server.AbstractSpecificServiceImpl;

import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationGroup;
import ci.gouv.dgbf.system.cloture.server.api.service.OperationGroupDto;
import ci.gouv.dgbf.system.cloture.server.api.service.OperationGroupService;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationGroupImpl;

@Path(OperationGroupService.PATH)
public class OperationGroupServiceImpl extends AbstractSpecificServiceImpl<OperationGroupDto,OperationGroupDtoImpl,OperationGroup,OperationGroupImpl> implements OperationGroupService,Serializable {

	@Inject OperationGroupDtoImplMapper mapper;
	
	public OperationGroupServiceImpl() {
		this.serviceEntityClass = OperationGroupDto.class;
		this.serviceEntityImplClass = OperationGroupDtoImpl.class;
		this.persistenceEntityClass = OperationGroup.class;
		this.persistenceEntityImplClass = OperationGroupImpl.class;
	}
}
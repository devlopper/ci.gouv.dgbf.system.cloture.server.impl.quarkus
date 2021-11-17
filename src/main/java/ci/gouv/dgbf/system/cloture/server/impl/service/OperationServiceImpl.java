package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.cyk.utility.service.server.AbstractSpecificServiceImpl;

import ci.gouv.dgbf.system.cloture.server.api.business.OperationBusiness;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Operation;
import ci.gouv.dgbf.system.cloture.server.api.service.OperationDto;
import ci.gouv.dgbf.system.cloture.server.api.service.OperationService;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationImpl;

@Path(OperationService.PATH)
public class OperationServiceImpl extends AbstractSpecificServiceImpl<OperationDto,OperationDtoImpl,Operation,OperationImpl> implements OperationService,Serializable {

	@Inject OperationGroupDtoImplMapper mapper;
	@Inject OperationBusiness operationBusiness;
	
	public OperationServiceImpl() {
		this.serviceEntityClass = OperationDto.class;
		this.serviceEntityImplClass = OperationDtoImpl.class;
		this.persistenceEntityClass = Operation.class;
		this.persistenceEntityImplClass = OperationImpl.class;
	}

	@Override
	public Response execute(String identifier) {
		operationBusiness.execute(identifier, null);
		return Response.ok().build();
	}
}
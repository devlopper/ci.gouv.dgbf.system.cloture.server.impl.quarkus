package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.cyk.utility.service.server.AbstractSpecificServiceImpl;

import ci.gouv.dgbf.system.cloture.server.api.business.OperationBusiness;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Operation;
import ci.gouv.dgbf.system.cloture.server.api.service.OperationDto;
import ci.gouv.dgbf.system.cloture.server.api.service.OperationService;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationImpl;

public class OperationServiceImpl extends AbstractSpecificServiceImpl<OperationDto,OperationDtoImpl,Operation,OperationImpl> implements OperationService,Serializable {

	@Inject OperationDtoImplMapper mapper;
	@Inject OperationBusiness business;
	
	public OperationServiceImpl() {
		this.serviceEntityClass = OperationDto.class;
		this.serviceEntityImplClass = OperationDtoImpl.class;
		this.persistenceEntityClass = Operation.class;
		this.persistenceEntityImplClass = OperationImpl.class;
	}
	
	@Override
	public Response create(String typeIdentifier, String code, String name, String reason,String auditWho) {
		return buildResponseOk(business.create(typeIdentifier, code, name, reason, auditWho));
	}
	
	@Override
	public Response execute(String identifier,String trigger,Boolean blocking) {
		if(blocking == null)
			blocking = Boolean.TRUE;
		//operationBusiness.execute(identifier, trigger,blocking);
		return Response.ok().build();
	}
}
package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.cyk.utility.service.server.AbstractSpecificServiceImpl;

import ci.gouv.dgbf.system.cloture.server.api.business.ScriptBusiness;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Script;
import ci.gouv.dgbf.system.cloture.server.api.service.OperationDto;
import ci.gouv.dgbf.system.cloture.server.api.service.OperationService;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ScriptImpl;

@Path(OperationService.PATH)
public class OperationServiceImpl extends AbstractSpecificServiceImpl<OperationDto,OperationDtoImpl,Script,ScriptImpl> implements OperationService,Serializable {

	@Inject OperationDtoImplMapper mapper;
	@Inject ScriptBusiness operationBusiness;
	
	public OperationServiceImpl() {
		this.serviceEntityClass = OperationDto.class;
		this.serviceEntityImplClass = OperationDtoImpl.class;
		this.persistenceEntityClass = Script.class;
		this.persistenceEntityImplClass = ScriptImpl.class;
	}

	@Override
	public Response execute(String identifier,String trigger,Boolean blocking) {
		if(blocking == null)
			blocking = Boolean.TRUE;
		operationBusiness.execute(identifier, trigger,blocking);
		return Response.ok().build();
	}
}
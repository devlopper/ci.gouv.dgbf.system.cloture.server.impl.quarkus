package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.cyk.utility.service.server.AbstractSpecificServiceImpl;

import ci.gouv.dgbf.system.cloture.server.api.business.ActBusiness;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Act;
import ci.gouv.dgbf.system.cloture.server.api.service.ActDto;
import ci.gouv.dgbf.system.cloture.server.api.service.ActService;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ActImpl;

@Path(ActService.PATH)
public class ActServiceImpl extends AbstractSpecificServiceImpl<ActDto,ActDtoImpl,Act,ActImpl> implements ActService,Serializable {

	@Inject ActDtoImplMapper mapper;
	@Inject ActBusiness business;
	
	public ActServiceImpl() {
		this.serviceEntityClass = ActDto.class;
		this.serviceEntityImplClass = ActDtoImpl.class;
		this.persistenceEntityClass = Act.class;
		this.persistenceEntityImplClass = ActImpl.class;
	}

	@Override
	public Response lock(List<String> identifiers, String trigger) {
		business.lock(identifiers, trigger);
		return Response.ok().build();
	}

	@Override
	public Response unlock(List<String> identifiers, String trigger) {
		business.unlock(identifiers, trigger);
		return Response.ok().build();
	}

}
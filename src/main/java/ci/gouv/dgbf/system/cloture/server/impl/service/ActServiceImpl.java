package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;

import javax.inject.Inject;
import javax.ws.rs.Path;

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
}
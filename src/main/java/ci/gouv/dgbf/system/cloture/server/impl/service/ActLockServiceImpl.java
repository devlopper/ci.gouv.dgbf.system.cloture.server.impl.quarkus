package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;

import javax.inject.Inject;
import javax.ws.rs.Path;

import org.cyk.utility.service.server.AbstractSpecificServiceImpl;

import ci.gouv.dgbf.system.cloture.server.api.business.ActLockBusiness;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActLock;
import ci.gouv.dgbf.system.cloture.server.api.service.ActLockDto;
import ci.gouv.dgbf.system.cloture.server.api.service.ActLockService;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ActLockImpl;

@Path(ActLockService.PATH)
public class ActLockServiceImpl extends AbstractSpecificServiceImpl<ActLockDto,ActLockDtoImpl,ActLock,ActLockImpl> implements ActLockService,Serializable {

	@Inject ActLockDtoImplMapper mapper;
	@Inject ActLockBusiness business;
	
	public ActLockServiceImpl() {
		this.serviceEntityClass = ActLockDto.class;
		this.serviceEntityImplClass = ActLockDtoImpl.class;
		this.persistenceEntityClass = ActLock.class;
		this.persistenceEntityImplClass = ActLockImpl.class;
	}

}